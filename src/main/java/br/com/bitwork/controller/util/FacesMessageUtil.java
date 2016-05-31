/*
 * FacesMessageUtil.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.controller.util;

import javax.faces.application.FacesMessage;

/**
 *  Classe utilitária para manipulação do {@link FacesMessage}.
 *
 * @author BitWork.
 */
public final class FacesMessageUtil {

	/**
	 * Construtor da classe.
	 *
	 * @author BitWork.
	 */
	private FacesMessageUtil() { }

	/**
	 * Retorna {@link FacesMessage} com o severity {@link FacesMessage#SEVERITY_INFO}.
	 * 
	 * @param mensagem
	 * @return
	 * @author BitWork.
	 */
	public static FacesMessage getMensagemInformacao(String mensagem) {
		FacesMessage message = getFacesMessage(mensagem);
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		return message;
	}

	/**
	 * Retorna {@link FacesMessage} com o severity {@link FacesMessage#SEVERITY_WARN}.
	 * 
	 * @param mensagem
	 * @return
	 * @author BitWork.
	 */
	public static FacesMessage getMensagemAlerta(String mensagem) {
		FacesMessage message = getFacesMessage(mensagem);
		message.setSeverity(FacesMessage.SEVERITY_WARN);
		return message;
	}

	/**
	 * Retorna {@link FacesMessage} com o severity {@link FacesMessage#SEVERITY_ERROR}.
	 *
	 * @param mensagem
	 * @return
	 * @author BitWork.
	 */
	public static FacesMessage getMensagemErro(String mensagem) {
		FacesMessage message = getFacesMessage(mensagem);
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		return message;
	}

	/**
	 * Retorna {@link FacesMessage}.
	 *
	 * @param mensagem
	 * @return
	 * @author BitWork.
	 */
	private static FacesMessage getFacesMessage(String mensagem) {
		return new FacesMessage(mensagem, mensagem);
	}
}