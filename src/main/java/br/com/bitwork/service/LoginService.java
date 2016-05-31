/*
 * LoginService.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.bitwork.business.UsuarioBusiness;
import br.com.bitwork.entities.Grupo;
import br.com.bitwork.entities.Usuario;
import br.com.bitwork.exception.CondominioLabelCode;
import br.com.bitwork.exception.CondominioMessageCode;
import br.com.bitwork.exception.LoginException;
import br.com.bitwork.exception.UsuarioException;
import br.com.bitwork.persistence.exception.DAOException;
import br.com.bitwork.security.NivelAcessoFactory;
import br.com.bitwork.util.DateUtil;
import br.com.bitwork.util.Util;
import br.com.bitwork.util.email.ConstantesEmail;
import br.com.bitwork.util.email.exception.EmailException;
import br.com.bitwork.util.exception.UtilException;

/**
 * Serviço responsável por autenticar o usuário e atribuir os privilégios segundo os {@link Grupo}s cadastrados no sistema.
 *
 * @author BitWork.
 */
@Dependent
@Named
public class LoginService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private UsuarioBusiness usuarioBusiness;
	
	@Inject
	private EmailService emailService;
	
	/**
	 * Efetua o login do {@link Usuario} ao sistema segundo o e-mail e senha informados.
	 *
	 * @param email
	 * @param senha
	 * @param authenticationManager
	 * @throws LoginException
	 * @author BitWork.
	 */
	public void login(final String email, final String senha, AuthenticationManager authenticationManager) throws LoginException {
		try {
			Authentication authentication = new UsernamePasswordAuthenticationToken(email, senha);
			Authentication result = authenticationManager.authenticate(authentication);
			SecurityContextHolder.getContext().setAuthentication(result);
		} catch (Exception e) {
			throw new LoginException(CondominioMessageCode.EMAIL_OU_SENHA_INCORRETOS);
		}
	}
	
	/**
	 * Obtém o {@link Usuario} segundo o e-mail informado.
	 * 
	 * @param email
	 * @return
	 * @throws LoginException
	 * @author BitWork.
	 */
	public Usuario getUsuarioByEmail(final String email) throws LoginException {
		try {
			Usuario usuario = usuarioBusiness.getByEmail(email);
			if (usuario == null || !usuario.isAtivo()) {
				throw new LoginException(CondominioMessageCode.USUARIO_INEXISTENTE);
			}
			return usuario;
		} catch (LoginException | UsuarioException e) {
			String msg = "Falha ao obter o Usuário segundo o e-mail informado.";
			logger.error(msg, e);
			throw new LoginException(msg, e);
		}
	}
	
	/**
	 * Verifica se os campos obrigatórios do {@link Usuario} foram informados.
	 *
	 * @param email
	 * @param senha
	 * @throws LoginException
	 * @author BitWork.
	 */
	public void validarCamposObrigatorios(final String email, final String senha, boolean isLogin) throws LoginException {
		List<CondominioLabelCode> labels = new ArrayList<CondominioLabelCode>();
		
		if (Util.isBlank(email)) {
			labels.add(CondominioLabelCode.LABEL_EMAIL);
		} if (Util.isBlank(senha) && isLogin) {
			labels.add(CondominioLabelCode.LABEL_SENHA);
		} if (!Util.isEmpty(labels)) {
			throw new LoginException(CondominioMessageCode.CAMPOS_OBRIGATORIOS_NAO_INFORMADOS, labels);
		}
	}

	/**
	 * Valida as senhas informadas.
	 *
	 * @param email
	 * @param senha
	 * @throws LoginException
	 * @author BitWork.
	 */
	public void validarSenha(final Usuario usuario, final String senha) throws LoginException {
		try {
			if (!Util.decrypt(usuario.getSenha()).equals(senha.trim())) {
				throw new UsuarioException(CondominioMessageCode.EMAIL_OU_SENHA_INCORRETOS);
			}
		} catch (UsuarioException | UtilException e) {
			String msg = "Falha ao validar a senha informada.";
			logger.error(msg, e);
			throw new LoginException(msg, e);
		}
	}

	/**
	 * Envia para o usuário o e-mail de Recuperação de Senha.
	 * 
	 * @param email
	 * @throws LoginException
	 * @author BitWork.
	 */
	public void enviarEmailRecuperacaoSenha(final String email) throws LoginException {
		try {
			String assunto = "Recuperação de Senha";
			String destinatario = email;
			String template = emailService.lerArquivo(emailService.getURLTemplateEmail(ConstantesEmail.TEMPLATE_AVISO_RECUPERAR_SENHA));
			Date data = new Date();
			Usuario usuario = getUsuarioByEmail(email);
			String senha = usuarioBusiness.gerarSenhaUsuario(email);
			usuario.setSenha(Util.generateMD5(senha));
			usuarioBusiness.persistir(usuario);

			template = emailService.montarRodapeEmail(template);
			template = template.replace(ConstantesEmail.NOME, usuario.getPessoa().getNome());
			template = template.replace(ConstantesEmail.DATA, DateUtil.getDataFormatada(data));
			template = template.replace(ConstantesEmail.HORA, DateUtil.getDataFormatada(data, "HH:mm"));
			template = template.replace(ConstantesEmail.SENHA, senha);
			
			emailService.enviarEmail(template, assunto, destinatario);
		} catch (EmailException | IOException | UtilException | DAOException e) {
			String msg = "Falha ao enviar o para o usuário o e-mail de Recuperação de Senha.";
			logger.error(msg, e);
			throw new LoginException(msg, e);
		}
	}
	
	/**
	 * TODO
	 * @return
	 * @author BitWork.
	 */
	public boolean isManterAdmnistrador() {
		return isPermitido(NivelAcessoFactory.getInstance().getManterAdministrador());
	}

	/**
	 * Verifica se o usuário logado possui a permissão informada.
	 * 
	 * TODO
	 * @param permissao
	 * @return
	 */
	private boolean isPermitido(String permissao) {
		boolean isPermitido = Boolean.FALSE;
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		//Verificar as permissões do usuário logado.
		for (GrantedAuthority autorizacao : authentication.getAuthorities()) {
			String auth = autorizacao.getAuthority().toString();
			if (auth.equals(permissao)) {
				isPermitido = Boolean.TRUE;
				break;
			}
		}
		return isPermitido;
	}
}