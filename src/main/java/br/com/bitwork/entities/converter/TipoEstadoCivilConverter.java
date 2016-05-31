/*
 * TipoEstadoCivilConverter.java
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

import br.com.bitwork.entities.enums.TipoEstadoCivil;

/**
 * Implementação de Converter para {@link TipoEstadoCivil}.
 *
 * @author BitWork.
 */
public class TipoEstadoCivilConverter implements AttributeConverter<TipoEstadoCivil, Long> {

	/**
	 * @see javax.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.Object)
	 */
	@Override
	public Long convertToDatabaseColumn(final TipoEstadoCivil tipoEstadoCivil) {
		return tipoEstadoCivil != null ? tipoEstadoCivil.getId() : null;
	}

	/**
	 * @see javax.persistence.AttributeConverter#convertToEntityAttribute(java.lang.Object)
	 */
	@Override
	public TipoEstadoCivil convertToEntityAttribute(final Long id) {
		return id != null ? TipoEstadoCivil.getTipoEstadoCivil(id) : null;
	}

}
