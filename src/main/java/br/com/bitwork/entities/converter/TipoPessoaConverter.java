/*
 * TipoPessoaConverter.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.entities.converter;

import javax.persistence.AttributeConverter;

import br.com.bitwork.entities.enums.TipoPessoa;

/**
 * Implementação de Converter para {@link TipoPessoa}.
 *
 * @author BitWork.
 */
public class TipoPessoaConverter implements AttributeConverter<TipoPessoa, Long> {

	/**
	 * @see javax.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.Object)
	 */
	@Override
	public Long convertToDatabaseColumn(final TipoPessoa tipoPessoa) {
		return tipoPessoa != null ? tipoPessoa.getId() : null;
	}

	/**
	 * @see javax.persistence.AttributeConverter#convertToEntityAttribute(java.lang.Object)
	 */
	@Override
	public TipoPessoa convertToEntityAttribute(final Long id) {
		return id != null ? TipoPessoa.getTipoPessoa(id) : null;
	}

}