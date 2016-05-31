/*
 * UtilException.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.util.exception;

import br.com.bitwork.util.Util;

/**
 * Exceção a ser lançada na ocorrência de falhas e validações de negócio referentes a classe {@link Util}.
 *
 * @author BitWork.
 */
public class UtilException extends Exception {

	private static final long serialVersionUID = -7441108995284022898L;

	/**
	 * Construtor da classe.
	 *
	 * @param msg
	 * @author BitWork.
	 */
	public UtilException(final String msg) {
		super(msg);
	}

	/**
	 * Construtor da classe.
	 * 
	 * @param e
	 * @author BitWork.
	 */
	public UtilException(final Throwable e) {
		super(e);
	}
	
	/**
	 * Construtor da classe.
	 *
	 * @param msg
	 * @param e
	 * @author BitWork.
	 */
	public UtilException(final String msg, final Throwable e) {
		super(msg, e);
	}
}