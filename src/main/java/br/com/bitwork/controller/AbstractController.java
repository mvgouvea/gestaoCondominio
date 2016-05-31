/*
 * AbstractController.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.controller;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bitwork.business.UsuarioBusiness;
import br.com.bitwork.constantes.Constantes;
import br.com.bitwork.controller.enums.AcaoSistema;
import br.com.bitwork.controller.util.FacesContextUtil;
import br.com.bitwork.controller.util.FacesMessageUtil;
import br.com.bitwork.entities.Usuario;
import br.com.bitwork.exception.BusinessException;
import br.com.bitwork.exception.CondominioMessageCode;
import br.com.bitwork.exception.UsuarioException;
import br.com.bitwork.service.LoginService;
import br.com.bitwork.util.Util;

/**
 * Classe padrão de Controlador.
 *
 * @author BitWork.
 */
public class AbstractController implements Serializable {

	private static final long serialVersionUID = -4944515187864896137L;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	protected UsuarioBusiness usuarioBusiness;

	@Inject
	private LoginService loginBusiness;
	
	@ManagedProperty("#{msg}")
    private ResourceBundle resourceMessages;

	private Usuario usuario;

	private AcaoSistema acaoSistema;

    /**
     * Inicialização do formulário.
     * 
     * TODO refatorar para que exiba mensagens de exceção.
     *  
     * @author BitWork.
     */
    public void init() { }
    
    /**
     * Obtém a propriedade segundo a mensagem informada.
     * 
     * @param property
     * @param msg
     * @return
     * @author BitWork.
     */
    public String getProperty(String property, String msg){
		return ResourceBundle.getBundle(property).getString(msg);
	}
    
    /**
     * Retorna a mensagem segundo o código informado.
     *
     * @param code
     * @return
     * @author BitWork.
     */
    protected String getMensagem(final String code) {
    	return resourceMessages.getString(code.toString());
    }
	
    /**
     * Retorna a mensagem segundo o {@link CondominioMessageCode} informado.
     * 
     * @param code
     * @return
     * @author BitWork.
     */
    protected String getMensagem(final CondominioMessageCode code) {
    	return resourceMessages.getString(code.toString());
    }

    /**
     * Retorna a mensagem formatada segundo o {@link CondominioMessageCode} e o parâmetro informado.
     * 
     * @param code
     * @param parametro
     * @return
     * @author BitWork.
     */
    protected String getMensagemFormatada(final CondominioMessageCode code, String parametro) {
    	String mensagem = getMensagem(code);
    	return MessageFormat.format(mensagem, parametro);
    }

	/**
	 * Adiciona mensagem a ser exibida na página.
	 * 
	 * @param message
	 * @param componenetId
	 * @author BitWork.
	 */
	protected void adicionarMensagem(final FacesMessage message, final String componenetId) {
		FacesContextUtil.adicionarMensagem(message, componenetId);
	}
    
	/**
	 * Adiciona mensagem para ser exibida na página.
	 * 
	 * @param message
	 * @author BitWork.
	 */
	protected void adicionarMensagem(final FacesMessage message) {
		FacesContextUtil.adicionarMensagem(message, null);
	}

	/**
	 * Retorna instância de {@link FacesContext}.
	 *
	 * @return
	 * @author BitWork.
	 */
	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	/**
	 * Adiciona mensagem de sucesso para ser exibida na página.
	 * 
	 * @param code
	 * @author BitWork.
	 */
	protected void adicionarMensagemSucesso(final String code) {
		String mensagem = getMensagem(code);
		FacesMessage message = FacesMessageUtil.getMensagemInformacao(mensagem);
		adicionarMensagem(message);
	}

	/**
	 * Adiciona mensagem de sucesso para ser exibida na página.
	 * 
	 * @param code
	 * @author BitWork.
	 */
	protected void adicionarMensagemSucesso(final CondominioMessageCode code) {
		String mensagem = getMensagem(code.toString());
		FacesMessage message = FacesMessageUtil.getMensagemInformacao(mensagem);
		adicionarMensagem(message);
	}

	/**
	 * Adiciona mensagem de sucesso para ser exibida na página.
	 * 
	 * @param code
	 * @param parametro
	 * @author BitWork.
	 */
	protected void adicionarMensagemSucesso(final CondominioMessageCode code, String parametro) {
		String mensagem = getMensagemFormatada(code, parametro);
		FacesMessage message = FacesMessageUtil.getMensagemInformacao(mensagem);
		adicionarMensagem(message);
	}

	/**
	 * Adiciona mensagem de erro a ser exibida na página.
	 * 
	 * @param mensagem
	 * @author BitWork.
	 */
	protected void adicionarMensagemErro(final String mensagem) {
		adicionarMensagemErro(mensagem, null);
	}
	
	/**
	 * Adiciona mensagem de erro para ser exibida na página.
	 * 
	 * @param mensagem
	 * @param componentId
	 * @author BitWork.
	 */
	protected void adicionarMensagemErro(final String mensagem, String componentId){
		FacesMessage message = FacesMessageUtil.getMensagemErro(mensagem);

		if (Util.isBlank(componentId)) {
			adicionarMensagem(message);
		} else {
			adicionarMensagem(message,componentId);
		}
	}

	/**
	 * Adiciona mensagem de erro para ser exibida na página.
	 * 
	 * @param code
	 * @author BitWork.
	 */
	protected void adicionarMensagemErro(final CondominioMessageCode code){
		String mensagem = getMensagem(code.toString());
		FacesMessage message = FacesMessageUtil.getMensagemErro(mensagem);
		adicionarMensagem(message);
	}
	
	/**
	 * Adiciona mensagem de erro para ser exibida na página.
	 *
	 * @param e
	 * @author BitWork.
	 */
	protected void adicionarMensagemErroInesperado(Throwable e) {
		String mensagem = getMensagem(CondominioMessageCode.ERRO_INESPERADO);
		mensagem =  MessageFormat.format(mensagem, e.getMessage());
		FacesMessage message = FacesMessageUtil.getMensagemErro(mensagem);
		adicionarMensagem(message);
		logger.error(mensagem, e);
	}

	
	/**
	 * Adiciona mensagem de erro para ser exibida na página.
	 * 
	 * @param e
	 * @param componentId
	 * @author BitWork.
	 */
	protected void adicionarMensagemErro(final Throwable e, final String componentId){		
		String msg = null;
		
		/** Caso a instância de Throwable seja de BusinessException. */
		if(e instanceof BusinessException) {
			BusinessException exception = (BusinessException) e;

			/** Caso a exceção esteja associada a validação de negócio. */
			if(exception.isValidacaoCamposObrigatorios() || !Util.isBlank(exception.getChave())) {
				msg = exception.getMessage();
			}
			
			/** Caso a causa da exceção esteja associada a validação de negócio. */
			if(exception.getCause() != null && exception.getCause() instanceof BusinessException) {
				BusinessException cause = (BusinessException) exception.getCause();
				
				if(cause.isValidacaoCamposObrigatorios() || !Util.isBlank(cause.getChave())) {
					msg = cause.getMessage();
				}
			}
		}

		if(!Util.isBlank(msg)) {
			adicionarMensagemErro(msg, componentId);
		} else {
			adicionarMensagemErroInesperado(e);
		}

	}
	
	/**
	 * Adiciona mensagem de erro a ser exibida na página.
	 *
	 * @param e
	 * @author BitWork.
	 */
	protected void adicionarMensagemErro(final Throwable e) {
		adicionarMensagemErro(e, null);
	}

	/**
	 * Recupera as informações do {@link Usuario} logado no sistema através do login informado.
	 * 
	 * @return the usuario
	 */
	protected Usuario getUsuarioLogado() {
		usuario = (Usuario) FacesContextUtil.getSessionAtributo(Constantes.USUARIO_SESSAO);
		if (usuario == null) {
			try {
				String email = FacesContextUtil.getExternalContext().getUserPrincipal().getName();
				usuario = usuarioBusiness.getByEmail(email);
				FacesContextUtil.setSessionAtributo(Constantes.USUARIO_SESSAO, usuario);
			} catch (UsuarioException e) {
				String msg = "Falha ao recuperar o Usuário logado no sistema.";
				adicionarMensagemErro(msg);
			}
		}
		return usuario;
	}
	
	/**
	 * Transforma o {@link Set} informado em uma {@link List}.
	 * 
	 * @param set
	 * @return
	 * @author BitWork.
	 */
	public <E> List<E> toLista(Set<E> set) {
		List<E> lista = new ArrayList<E>();

		if (!Util.isEmpty(set)) {
			lista = new ArrayList<E>(set);
		}

		return lista;
	}
	
	/**
	 * Verifica se a ação do usuário é igual a <b>{@link AcaoSistema#VISUALIZAR}</b>.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public boolean isVisualizar() {
		return AcaoSistema.VISUALIZAR.equals(getAcaoSistema());
	}

	/**
	 * Verifica se a ação do usuário é igual a <b>{@link AcaoSistema#LISTAR}</b>.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public boolean isListar() {
		return AcaoSistema.LISTAR.equals(getAcaoSistema());
	}

	/**
	 * Verifica se a ação do usuário é igual a <b>{@link AcaoSistema#INCLUIR}</b>.
	 *
	 * @return
	 * @author BitWork.
	 */
	public boolean isIncluir() {
		return AcaoSistema.INCLUIR.equals(getAcaoSistema());
	}

	/**
	 * Verifica se a ação do usuário é igual a <b>{@link AcaoSistema#ALTERAR}</b>.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public boolean isAlterar() {
		return AcaoSistema.ALTERAR.equals(getAcaoSistema());
	}

	/**
	 * Redireciona para a página principal do sistema.
	 *
	 * @author BitWork.
	 */
	public void redirecionarPaginaPrincipal() {
		FacesContextUtil.navegarPara(Constantes.PAGINA_PRINCIPAL);
	}
	
	/**
	 * @param resourceMessages the resourceMessages to set
	 */
	public void setResourceMessages(ResourceBundle resourceMessages) {
		this.resourceMessages = resourceMessages;
	}

	/**
	 * @return the acaoSistema
	 */
	public AcaoSistema getAcaoSistema() {
		return acaoSistema;
	}

	/**
	 * @param acaoSistema the acaoSistema to set
	 */
	public void setAcaoSistema(AcaoSistema acaoSistema) {
		this.acaoSistema = acaoSistema;
	}
	
	/**
	 * Verifica se o usuário logado possui permissão para <b>Publicar o PTI com Ressalvas</b> quando este for titular de algum componente.
	 * 
	 * @return
	 */
	public boolean isManterAdministrador() {
		return loginBusiness.isManterAdmnistrador();
	}	
}