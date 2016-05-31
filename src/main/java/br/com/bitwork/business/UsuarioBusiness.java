/*
 * UsuarioBusiness.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bitwork.entities.Perfil;
import br.com.bitwork.entities.Pessoa;
import br.com.bitwork.entities.Usuario;
import br.com.bitwork.entities.enums.Status;
import br.com.bitwork.entities.enums.TipoPessoa;
import br.com.bitwork.exception.CondominioLabelCode;
import br.com.bitwork.exception.CondominioMessageCode;
import br.com.bitwork.exception.UsuarioException;
import br.com.bitwork.persistence.UsuarioDAO;
import br.com.bitwork.persistence.exception.DAOException;
import br.com.bitwork.service.EmailService;
import br.com.bitwork.util.DateUtil;
import br.com.bitwork.util.Util;
import br.com.bitwork.util.email.ConstantesEmail;
import br.com.bitwork.util.email.exception.EmailException;
import br.com.bitwork.util.exception.UtilException;
import br.com.bitwork.ws.EnderecoWebService;
import br.com.bitwork.ws.exception.EnderecoException;
import br.com.bitwork.ws.model.Endereco;

/**
 * Classe responsável por implementar as regras de negócio relativas à classe {@link Usuario}.
 *
 * @author BitWork.
 */
@Named
@Dependent
@Transactional(rollbackOn = UsuarioException.class)
public class UsuarioBusiness {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private UsuarioDAO usuarioDAO;

	@Inject
	private EmailService emailService;
	
	/**
	 * Persiste o {@link Usuario} informado.
	 *
	 * @param usuario
	 * @throws DAOException
	 * @author BitWork.
	 */
	public void persistir(Usuario usuario) throws DAOException {
		usuarioDAO.persistir(usuario);
	}
	
	/**
	 * Salva o {@link Usuario} informado.
	 *
	 * @param usuario
	 * @param perfil
	 * @param isInclusao
	 * @throws UsuarioException
	 * @author BitWork.
	 */
	public void salvar(Usuario usuario, final Perfil.Descricao perfil, final boolean isInclusao) throws UsuarioException {
		try {
			montarDadosUsuario(usuario, perfil, isInclusao);
			validarCamposObrigatorios(usuario);
			validarEmail(usuario.getEmail());
			validarCPF(usuario.getPessoa());
			validarRG(usuario);
			validarNumeroEndereco(usuario);

			removerCaracteresEspeciais(usuario);

			validarDuplicidade(usuario);
			
			persistir(usuario);

			enviarEmailCadastroUsuario(usuario, isInclusao);
		} catch (DAOException | UtilException e) {
			String msg = "Falha ao salvar o usuário informado.";
			logger.error(msg, e);
			throw new UsuarioException(msg, e);
		}
	}

	/**
	 * Altera a senha do {@link Usuario} informado.
	 *
	 * @param usuario
	 * @param novaSenha
	 * @param confirmacaoSenha
	 * @throws UsuarioException
	 * @author BitWork.
	 */
	public void alterarSenha(Usuario usuario, final String novaSenha, final String confirmacaoSenha) throws UsuarioException {
		try {
			validarCamposObrigatorios(novaSenha, confirmacaoSenha);

			if (!novaSenha.trim().equals(confirmacaoSenha.trim())) {
				throw new UsuarioException(CondominioMessageCode.SENHAS_NAO_CORRESPONDEM);
			}
			
			usuario.setSenha(Util.generateMD5(novaSenha));
			usuario.setPrimeiroAcesso(Boolean.FALSE);
			persistir(usuario);
		} catch (UtilException | DAOException e) {
			String msg = "Falha ao alterar a senha do Usuário informado.";
			logger.error(msg, e);
			throw new UsuarioException(msg, e);
		}
	}
	
	/**
	 * Ativa ou Inativa o {@link Usuario} informado.
	 *
	 * @param usuario
	 * @throws UsuarioException
	 * @author BitWork.
	 */
	public void alterarSituacao(Usuario usuario) throws UsuarioException {
		try {
			if (usuario.isAtivo()) {
				usuario.setAtivo(Status.INATIVO.getId());
			} else {
				usuario.setAtivo(Status.ATIVO.getId());
			}

			persistir(usuario);
		} catch (DAOException e) {
			String msg = "Falha ao alterar a situação do usuário informado.";
			logger.error(msg, e);
			throw new UsuarioException(msg, e);
		}
	}
	
	/**
	 * Obtém os {@link Usuario}s segundo o {@link Perfil} informado.
	 *
	 * @param perfil
	 * @return
	 * @throws UsuarioException
	 * @author BitWork.
	 */
	public List<Usuario> getAll(final Perfil.Descricao perfil) throws UsuarioException {
		try {
			return usuarioDAO.getAll(new Perfil(perfil));
		} catch (DAOException e) {
			String msg = "Falha ao obter os Usuários segundo o Tipo de Usuário informado.";
			logger.error(msg, e);
			throw new UsuarioException(msg, e);
		}
	}

	/**
	 * Obtém os {@link Usuario}s segundo o {@link Perfil} e nome informados.
	 *
	 * @param perfil
	 * @param nome
	 * @return
	 * @throws UsuarioException
	 * @author BitWork.
	 */
	public List<Usuario> getAll(final Perfil.Descricao perfil, final String nome) throws UsuarioException {
		try {
			return usuarioDAO.getAll(new Perfil(perfil), nome);
		} catch (DAOException e) {
			String msg = "Falha ao obter os Usuários segundo o Tipo de Usuário e nome informados.";
			logger.error(msg, e);
			throw new UsuarioException(msg, e);
		}
	}
	
	/**
	 * Obtém o {@link Usuario} segundo o id informado.
	 *
	 * @param id
	 * @return
	 * @throws UsuarioException
	 * @author BitWork.
	 */
	public Usuario getById(final Long id) throws UsuarioException {
		try {
			return usuarioDAO.getById(id);
		} catch (DAOException e) {
			String msg = "Falha ao obter o Usuário segundo o id informado.";
			logger.error(msg, e);
			throw new UsuarioException(msg, e);
		}
	}

	/**
	 * Obtém o {@link Usuario} segundo o e-mail informado.
	 *
	 * @param email
	 * @return
	 * @throws UsuarioException
	 * @author BitWork.
	 */
	public Usuario getByEmail(final String email) throws UsuarioException {
		try {
			return usuarioDAO.getByEmail(email.trim());
		} catch (DAOException e) {
			String msg = "Falha ao obter o Usuário segundo o e-mail informado.";
			logger.error(msg, e);
			throw new UsuarioException(msg, e);
		}
	}

	/**
	 * Valida se foram encontrados registros na lista de {@link Usuario} informada.
	 *
	 * @param usuarios
	 * @throws UsuarioException
	 * @author BitWork.
	 */
	public void validarResultadoEncontrado(final List<Usuario> usuarios) throws UsuarioException {
		if (Util.isEmpty(usuarios)) {
			throw new UsuarioException(CondominioMessageCode.NENHUM_RESULTADO_ENCONTRADO);
		}
	}

	/**
	 * Obtém o endereço do {@link Usuario} informado segundo o CEP.
	 *
	 * @param usuario
	 * @return
	 * @throws UsuarioException
	 * @author BitWork.
	 */
	public Usuario buscarEndereco(Usuario usuario) throws UsuarioException {
		try {
			Endereco endereco = EnderecoWebService.getEndereco(usuario.getPessoa().getCep());
			
			if (endereco == null) {
				throw new UsuarioException(CondominioMessageCode.CEP_INVALIDO);
			}
			
			usuario.getPessoa().setEstado(endereco.getUf());
			usuario.getPessoa().setCidade(endereco.getLocalidade());
			usuario.getPessoa().setBairro(endereco.getBairro());
			usuario.getPessoa().setEndereco(endereco.getLogradouro());

			return usuario;
		} catch (EnderecoException e) {
			String msg = "Falha ao obter o endereço do usuário segundo o CEP informado.";
			logger.error(msg, e);
			throw new UsuarioException(msg, e);
		}
	}

	/**
	 * Gera a senha do usuário: email + data atual codificando essa string em MD5 e retirando os 8 primeiro caracteres.
	 * 
	 * @param email
	 * @return
	 * @throws UtilException
	 * @author BitWork.
	 */
	public String gerarSenhaUsuario(final String email) throws UtilException {
		String senha = email + DateUtil.getDataAtualZeroHora().toString();
		senha = Util.generateMD5(senha);
		return senha.substring(0, 8);
	}

	/**
	 * Monta os dados de inclusão do {@link Usuario} informado.
	 *
	 * @param usuario
	 * @param perfil
	 * @param isInclusao
	 * @throws UtilException
	 * @author BitWork.
	 */
	private void montarDadosUsuario(Usuario usuario, final Perfil.Descricao perfil, final boolean isInclusao) throws UtilException {
		if (isInclusao) {
			usuario.setPerfil(new Perfil(perfil));
			usuario.setAtivo(Boolean.TRUE);
			usuario.setPrimeiroAcesso(Boolean.TRUE);
			usuario.setSenha(Util.encrypt(gerarSenhaUsuario(usuario.getEmail())));
			usuario.getPessoa().setTipoPessoa(TipoPessoa.FISICA);
		}
	}

	/**
	 * Verifica se os campos obrigatórios foram informados.
	 *
	 * @param novaSenha
	 * @param confirmacaoSenha
	 * @throws UsuarioException
	 * @author BitWork.
	 */
	private void validarCamposObrigatorios(final String novaSenha, final String confirmacaoSenha) throws UsuarioException {
		List<CondominioLabelCode> labels = new ArrayList<CondominioLabelCode>();
		
		if (Util.isBlank(novaSenha)) {
			labels.add(CondominioLabelCode.LABEL_NOVA_SENHA);
		} if (Util.isBlank(novaSenha)) {
			labels.add(CondominioLabelCode.LABEL_CONFIRMAR_SENHA);
		} if (!Util.isEmpty(labels)) {
			throw new UsuarioException(CondominioMessageCode.CAMPOS_OBRIGATORIOS_NAO_INFORMADOS, labels);
		}
	}
	
	/**
	 * Verifica se os campos obrigatórios do {@link Usuario} foram informados.
	 *
	 * @param usuario
	 * @throws UsuarioException
	 * @author BitWork.
	 */
	private void validarCamposObrigatorios(final Usuario usuario) throws UsuarioException {
		List<CondominioLabelCode> labels = new ArrayList<CondominioLabelCode>();
		
		if (Util.isBlank(usuario.getPessoa().getNome())) {
			labels.add(CondominioLabelCode.LABEL_NOME);
		} if (Util.isBlank(usuario.getPessoa().getSobrenome()) && TipoPessoa.FISICA.equals(usuario.getPessoa().getTipoPessoa())) {
			labels.add(CondominioLabelCode.LABEL_SOBRENOME);
		} if (Util.isBlank(usuario.getEmail())) {
			labels.add(CondominioLabelCode.LABEL_EMAIL);
		} if (Util.isBlank(usuario.getPessoa().getTelefone())) {
			labels.add(CondominioLabelCode.LABEL_TELEFONE);
		} if (usuario.getPessoa().getTipoGenero() == null && TipoPessoa.FISICA.equals(usuario.getPessoa().getTipoPessoa())) {
			labels.add(CondominioLabelCode.LABEL_GENERO);
		} if (usuario.getPessoa().getTipoEstadoCivil() == null && TipoPessoa.FISICA.equals(usuario.getPessoa().getTipoPessoa())) {
			labels.add(CondominioLabelCode.LABEL_ESTADO_CIVIL);
		} if (usuario.getPessoa().getRg() == null && TipoPessoa.FISICA.equals(usuario.getPessoa().getTipoPessoa())) {
			labels.add(CondominioLabelCode.LABEL_RG);
		} if (Util.isBlank(usuario.getPessoa().getCpf()) && TipoPessoa.FISICA.equals(usuario.getPessoa().getTipoPessoa())) {
			labels.add(CondominioLabelCode.LABEL_CPF);
		} if (usuario.getPessoa().getDataNascimento() == null && TipoPessoa.FISICA.equals(usuario.getPessoa().getTipoPessoa())) {
			labels.add(CondominioLabelCode.LABEL_DATA_NASCIMENTO);
		} if (Util.isBlank(usuario.getPessoa().getCep())) {
			labels.add(CondominioLabelCode.LABEL_CEP);
		} if (Util.isBlank(usuario.getPessoa().getEstado())) {
			labels.add(CondominioLabelCode.LABEL_ESTADO);
		} if (Util.isBlank(usuario.getPessoa().getCidade())) {
			labels.add(CondominioLabelCode.LABEL_CIDADE);
		}if (Util.isBlank(usuario.getPessoa().getBairro())) {
			labels.add(CondominioLabelCode.LABEL_BAIRRO);
		}if (Util.isBlank(usuario.getPessoa().getEndereco())) {
			labels.add(CondominioLabelCode.LABEL_ENDERECO);
		} if (!Util.isEmpty(labels)) {
			throw new UsuarioException(CondominioMessageCode.CAMPOS_OBRIGATORIOS_NAO_INFORMADOS, labels);
		}
	}

	/**
	 * Verifica se o e-mail informado é válido: possui pelo menos um “<b>.</b>” e um “<b>@</b>”.
	 *
	 * @param usuario
	 * @author BitWork.
	 * @throws UsuarioException 
	 */
	private void validarEmail(final String email) throws UsuarioException {
		if (!Util.isEmailValido(email)) {
			throw new UsuarioException(CondominioMessageCode.EMAIL_INVALIDO);
		}
	}

	/**
	 * Verifica se o CPF informado é válido.
	 *
	 * @param pessoa
	 * @throws UsuarioException
	 * @author BitWork.
	 */
	private void validarCPF(final Pessoa pessoa) throws UsuarioException {
		if (TipoPessoa.FISICA.equals(pessoa.getTipoPessoa()) && !Util.isCpfValido(pessoa.getCpf())) {
			throw new UsuarioException(CondominioMessageCode.CPF_INVALIDO);
		}
	}

	/**
	 * Verifica se o RG informado é menor que 10 e deverá preenche com “0” a esquerda.
	 * 
	 * TODO revisar
	 * @param usuario
	 * @author BitWork.
	 */
	private void validarRG(Usuario usuario) {
		String rg = usuario.getPessoa().getRg().toString();
		
		if (rg.length() < 10) {
			usuario.getPessoa().setRg(Long.parseLong(Util.completarZerosEsquerda(rg, 1)));
		}
	}

	/**
	 * Verifica se o usuário não inseriu nenhum dado neste campo e atribui o valor “<b>S/N</b>”.
	 * 
	 * @param usuario
	 * @author BitWork.
	 */
	private void validarNumeroEndereco(Usuario usuario) {
		if (Util.isBlank(usuario.getPessoa().getNumero())) {
			usuario.getPessoa().setNumero("S/N");
		}
	}
	
	/**
	 * Envia e-mail e-mail informando o cadastro de novo usuário no sistema.
	 * 
	 * @param usuario
	 * @param isInclusao
	 * @throws UsuarioException
	 * @author BitWork.
	 */
	private void enviarEmailCadastroUsuario(final Usuario usuario, final boolean isInclusao) throws UsuarioException {
		try {
			if (isInclusao) {
				String assunto = "Cadastro Usuário " + usuario.getPerfil().getDescricao();
				String destinatario = usuario.getEmail();
				String template = emailService.lerArquivo(emailService.getURLTemplateEmail(ConstantesEmail.TEMPLATE_AVISO_CADASTRO_USUARIO));
				
				template = emailService.montarRodapeEmail(template);
				template = template.replace(ConstantesEmail.NOME, usuario.getPessoa().getNome());
				template = template.replace(ConstantesEmail.LOGIN, usuario.getEmail());
				template = template.replace(ConstantesEmail.SENHA, Util.decrypt(usuario.getSenha()));
				
				emailService.enviarEmail(template, assunto, destinatario);
			}
		} catch (EmailException | IOException | UtilException e) {
			String msg = "Falha ao enviar o e-mail informando o cadastro de novo usuário no sistema.";
			logger.error(msg, e);
			throw new UsuarioException(msg, e);
		}
	}

	/**
	 * Remove os caracteres especiais do Telefone, CPF e CNPJ do {@link Usuario} informado.
	 *
	 * @param usuario
	 * @author BitWork.
	 */
	private void removerCaracteresEspeciais(Usuario usuario) {
		usuario.getPessoa().setTelefone(Util.removerEspacoEntrePalavras(Util.removerCaracteresEspeciais(usuario.getPessoa().getTelefone())));
		usuario.getPessoa().setCpf(Util.removerEspacoEntrePalavras(Util.removerCaracteresEspeciais(usuario.getPessoa().getCpf())));
		usuario.getPessoa().setCep(Util.removerEspacoEntrePalavras(Util.removerCaracteresEspeciais(usuario.getPessoa().getCep())));
	}

	/**
	 * Verifica se possui algum usuário com o mesmo e-mail, CPF ou CNPJ que o {@link Usuario} informado.
	 *
	 * @param usuario
	 * @throws UsuarioException
	 * @author BitWork.
	 */
	private void validarDuplicidade(final Usuario usuario) throws UsuarioException {
		try {
			Usuario usuarioEncontrado = usuarioDAO.getUsuario(usuario.getEmail(), usuario.getPessoa().getCpf(), usuario.getPessoa().getCnpj());
			
			if (usuarioEncontrado != null && usuarioEncontrado.getId() != usuario.getId()) {
				throw new UsuarioException(CondominioMessageCode.USUARIO_EXISTENTE);
			}
		} catch (DAOException e) {
			String msg = "Falha ao obter o Usuário segundo o e-mail, CPF ou CNPJ informados.";
			logger.error(msg, e);
			throw new UsuarioException(msg, e);
		}
	}
}