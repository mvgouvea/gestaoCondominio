/*
 * EmailException.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.util.email.exception;

import br.com.bitwork.util.email.Email;

/**
 * Exceção a ser lançada na ocorrência de falhas e validações de negócio referentes a classe {@link Email}.
 *
 * @author BitWork.
 */
public class EmailException extends Exception {

	private static final long serialVersionUID = -178560253838794331L;

	/**
	 * Construtor da classe.
	 *
	 * @param msg
	 * @author BitWork.
	 */
	public EmailException(final String msg) {
		super(msg);
	}

	/**
	 * Construtor da classe.
	 *
	 * @param msg
	 * @param e
	 * @author BitWork.
	 */
	public EmailException(final String msg, final Throwable e) {
		super(msg, e);
	}
}