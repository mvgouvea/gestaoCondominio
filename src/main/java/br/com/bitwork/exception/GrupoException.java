/*
 * GrupoException.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.exception;

import br.com.bitwork.constantes.Constantes;
import br.com.bitwork.entities.Grupo;

/**
 * Exceção a ser lançada na ocorrência de falhas e validações de negócio referentes a entidade {@link Grupo}.
 *
 * @author BitWork.
 */
public class GrupoException extends BusinessException {

	private static final long serialVersionUID = -7454314290167677575L;

	/**
	 * Construtor da classe.
	 *
	 * @param message
	 * @param e
	 * @author BitWork.
	 */
	public GrupoException(final String message, final Throwable e) {
		super(message, e);
	}

	/**
	 * @see br.com.bitwork.exception.BusinessException#getMessageProperties()
	 */
	@Override
	protected String getMessageProperties() {
		return Constantes.MESSAGE_PROPERTIES;
	}
}