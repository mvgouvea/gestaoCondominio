/*
 * FacesContextUtil.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.controller.util;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Classe utilitária para manipulação do {@link FacesContext}.
 *
 * @author BitWork.
 */
public final class FacesContextUtil {
	
	/**
	 * Construtor da classe.
	 *
	 * @author BitWork.
	 */
	private FacesContextUtil() { }

	/**
	 * Retorna {@link FacesContext}.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	/**
	 * Retorna {@link Application}.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public static Application getApplication() {
		return getFacesContext().getApplication();
	}

	/**
	 * Retorna {@link ELContext}.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public static ELContext getElContext() {
		return getFacesContext().getELContext();
	}

	/**
	 * Retorna {@link ExternalContext}.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public static ExternalContext getExternalContext() {
		return getFacesContext().getExternalContext();
	}

	/**
	 * Retorna {@link HttpServletRequest}.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) getExternalContext().getRequest();
	}

	/**
	 * Retorna {@link HttpServletResponse}.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) getExternalContext().getResponse();
	}

	/**
	 * Retorna mapa de parâmetro da requisição.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public static Map<String, String> getRequestParamMap() {
		return getExternalContext().getRequestParameterMap();
	}

	/**
	 * Retorna o parâmetro da requisição segundo o parâmetro informado.
	 * 
	 * @param parametro
	 * @return
	 * @author BitWork.
	 */
	public static String getRequestParam(String parametro) {
		return getRequestParamMap().get(parametro);
	}

	/**
	 * Retorna a sessao do {@link ExternalContext}.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public HttpSession getSession() {
		return (HttpSession) getExternalContext().getSession(false);
	}

	/**
	 * Retorna o mapa da sessão corrente.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public static Map<String, Object> getSessionMap() {
		return getExternalContext().getSessionMap();
	}

	/**
	 * Recupera atributo na sessão.
	 * 
	 * @param chave
	 * @return
	 * @author BitWork.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSessionAtributo(String chave) {
		return (T) getSessionMap().get(chave);
	}

	/**
	 * Adiciona o atributo na sessão.
	 * 
	 * @param chave
	 * @param valor
	 * @author BitWork.
	 */
	public static void setSessionAtributo(String chave, Object valor) {
		getSessionMap().put(chave, valor);
	}

	/**
	 * Retorna {@link MethodExpression}.
	 * 
	 * @param expressao
	 * @return
	 * @author BitWork.
	 */
	@SuppressWarnings("unchecked")
	public static MethodExpression getMethodExpression(String expressao) {
		return getMethodExpression(expressao, new Class[] {});
	}

	/**
	 * Retorna {@link MethodExpression}.
	 * 
	 * @param expressao
	 * @param classesEvent
	 * @return
	 * @author BitWork.
	 */
	public static <T> MethodExpression getMethodExpression(String expressao, Class<T>[] classesEvent) {
		return getExpressionFactory().createMethodExpression(getElContext(), expressao, String.class, classesEvent);
	}

	/**
	 * Retorna {@link ExpressionFactory}.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public static ExpressionFactory getExpressionFactory() {
		return getApplication().getExpressionFactory();
	}

	/**
	 * Retorna a url atual.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public static String getUrlAtual() {
		return getRequest().getRequestURI();
	}

	/**
	 * Navega de pagina de acordo com o outcome informado.
	 * 
	 * @param outcome
	 * @author BitWork.
	 */
	public static void navegarPara(final String outcome) {
		getApplication().getNavigationHandler().handleNavigation(getFacesContext(), null, outcome);
	}
	
	/**
	 * Adiciona mensagem ao contexto.
	 *
	 * @param message
	 * @param componenteId
	 * @author BitWork.
	 */
	public static void adicionarMensagem(final FacesMessage message, final String componenteId) {
		getFacesContext().addMessage(componenteId, message);
	}
}