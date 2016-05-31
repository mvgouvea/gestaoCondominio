/*
 * AutenticarUsuarioController.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.springframework.security.authentication.AuthenticationManager;

import br.com.bitwork.entities.Usuario;
import br.com.bitwork.exception.CondominioMessageCode;
import br.com.bitwork.exception.LoginException;
import br.com.bitwork.exception.UsuarioException;
import br.com.bitwork.service.LoginService;

/**
 * Controlador para o Caso de Uso - DECU-DFR-UC.04-AUTENTICAR USUÁRIO.
 *
 * @author BitWork.
 */
@ManagedBean
@ViewScoped
public class AutenticarUsuarioController extends AbstractController {

	private static final long serialVersionUID = -2348133914536828976L;

	@ManagedProperty(value = "#{authenticationManager}")
	private AuthenticationManager authenticationManager;
	
	@Inject
	private LoginService loginService;

	private Usuario usuario;
	
	private AcaoLogin acaoLogin;
	
	private String email;
	private String senha;
	private String novaSenha;
	private String confirmacaoSenha;

	/**
	 * @see br.com.bitwork.controller.AbstractController#init()
	 */
	@Override
	@PostConstruct
	public void init() {
		super.init();
		setAcaoLogin(AcaoLogin.LOGIN);
	}

	/**
	 * Valida o e-mail e senha informados.
	 * 
	 * @author BitWork.
	 */
	public void validarLogin() {
		try {
			loginService.validarCamposObrigatorios(getEmail(), getSenha(), isLogin());
			setUsuario(loginService.getUsuarioByEmail(getEmail()));

			if (!getUsuario().isPrimeiroAcesso()) {
				login();
			} else {
				loginService.validarSenha(getUsuario(), getSenha());
				setAcaoLogin(AcaoLogin.ALTERAR_SENHA);
			}
		} catch (LoginException e) {
			adicionarMensagemErro(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Salva a senha do {@link Usuario} informado.
	 *
	 * @author BitWork.
	 */
	public void salvarSenha() {
		try {
			usuarioBusiness.alterarSenha(getUsuario(), getNovaSenha(), getConfirmacaoSenha());
			setAcaoLogin(AcaoLogin.LOGIN);
			adicionarMensagemSucesso(CondominioMessageCode.SENHA_ALTERADA);
		} catch (UsuarioException e) {
			adicionarMensagemErro(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}

	/**
	 * Envia o usuário para a tela de Recuperar Senha, definindo a ação do sistema para <b>{@link AcaoLogin#RECUPERAR_SENHA}</b>.
	 *
	 * @author BitWork.
	 */
	public void recuperarSenha() {
		setAcaoLogin(AcaoLogin.RECUPERAR_SENHA);
	}

	/**
	 * Envia para o usuário o e-mail de Recuperação de Senha segundo o e-mail informado.
	 *
	 * @author BitWork.
	 */
	public void enviarEmailRecuperacaoSenha() {
		try {
			loginService.validarCamposObrigatorios(getEmail(), getSenha(), isLogin());
			loginService.enviarEmailRecuperacaoSenha(getEmail());
			setAcaoLogin(AcaoLogin.LOGIN);
			adicionarMensagemSucesso(CondominioMessageCode.SENHA_ENVIADA);
		} catch (LoginException e) {
			adicionarMensagemErro(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Cancela as ações do usuário e envia para a tela de Login, definindo a ação do sistema para <b>{@link AcaoLogin#LOGIN}</b>.
	 *
	 * @author BitWork.
	 */
	public void cancelar() {
		setAcaoLogin(AcaoLogin.LOGIN);
	}
	
	/**
	 * Enum para definição de Ações da página de Login.
	 *
	 * @author BitWork.
	 */
	public enum AcaoLogin {
		LOGIN, ALTERAR_SENHA, RECUPERAR_SENHA;
	}

	/**
	 * Verifica se a ação do usuário é igual a <b>{@link AcaoLogin#LOGIN}</b>.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public boolean isLogin() {
		return AcaoLogin.LOGIN.equals(getAcaoLogin());
	}

	/**
	 * Verifica se a ação do usuário é igual a <b>{@link AcaoLogin#ALTERAR_SENHA}</b>.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public boolean isAlterarSenha() {
		return AcaoLogin.ALTERAR_SENHA.equals(getAcaoLogin());
	}

	/**
	 * Verifica se a ação do usuário é igual a <b>{@link AcaoLogin#RECUPERAR_SENHA}</b>.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public boolean isRecuperarSenha() {
		return AcaoLogin.RECUPERAR_SENHA.equals(getAcaoLogin());
	}
	
	/**
	 * Efetua o login do {@link Usuario} ao sistema segundo o e-mail e senha informados.
	 * 
	 * @author BitWork.
	 * @throws LoginException 
	 */
	private void login() throws LoginException {
		loginService.login(getEmail(), getSenha(), getAuthenticationManager());
		redirecionarPaginaPrincipal();
	}
	/************************************************ Getters and Setters ************************************************/
	/**
	 * @return the authenticationManager
	 */
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	/**
	 * @param authenticationManager the authenticationManager to set
	 */
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/**
	 * @return the acaoLogin
	 */
	public AcaoLogin getAcaoLogin() {
		return acaoLogin;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @param acaoLogin the acaoLogin to set
	 */
	public void setAcaoLogin(AcaoLogin acaoLogin) {
		this.acaoLogin = acaoLogin;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * @return the novaSenha
	 */
	public String getNovaSenha() {
		return novaSenha;
	}

	/**
	 * @param novaSenha the novaSenha to set
	 */
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	/**
	 * @return the confirmacaoSenha
	 */
	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	/**
	 * @param confirmacaoSenha the confirmacaoSenha to set
	 */
	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}
}