/*
 * EnderecoException.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.ws.exception;

import br.com.bitwork.ws.model.Endereco;

/**
 * Exceção a ser lançada na ocorrência de falhas e validações de negócio referentes a classe {@link Endereco}.
 *
 * @author BitWork.
 */
public class EnderecoException extends Exception {

	private static final long serialVersionUID = -2628296975599508547L;

	/**
	 * Construtor da classe.
	 *
	 * @param msg
	 * @author BitWork.
	 */
	public EnderecoException(final String msg) {
		super(msg);
	}

	/**
	 * Construtor da classe.
	 *
	 * @param e
	 * @author BitWork.
	 */
	public EnderecoException(Throwable e) {
		super(e);
	}

	/**
	 * Construtor da classe.
	 *
	 * @param msg
	 * @param e
	 * @author BitWork.
	 */
	public EnderecoException(final String msg, final Throwable e) {
		super(msg, e);
	}
}