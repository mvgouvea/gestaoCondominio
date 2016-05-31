/*
 * ServicoException.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.exception;

import java.util.List;

import br.com.bitwork.constantes.Constantes;
import br.com.bitwork.entities.Servico;

/**
 * Exceção a ser lançada na ocorrência de falhas e validações de negócio referentes a entidade {@link Servico}.
 *
 * @author BitWork.
 */
public class ServicoException extends BusinessException {

	private static final long serialVersionUID = 8576065360378508271L;
	
	/**
	 * Construtor da classe.
	 *
	 * @param message
	 * @param e
	 * @author BitWork.
	 */
	public ServicoException(final String message, final Throwable e) {
		super(message, e);
	}

	/**
	 * Construtor da classe.
	 *
	 * @param code
	 * @param parametros
	 * @author BitWork.
	 */
	public ServicoException(final CondominioMessageCode code, final Object... parametros ) {
		super(code, parametros);
	}

	/**
	 * Construtor da classe.
	 *
	 * @param code
	 * @param codeLabels
	 * @author BitWork.
	 */
	public ServicoException(final CondominioMessageCode code, final List<CondominioLabelCode> codeLabels) {
		super(code, codeLabels);
	}
	
	/**
	 * @see br.com.bitwork.exception.BusinessException#getMessageProperties()
	 */
	@Override
	protected String getMessageProperties() {
		return Constantes.MESSAGE_PROPERTIES;
	}
}