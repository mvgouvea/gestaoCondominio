/*
 * TipoGeneroConverter.java
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
import javax.persistence.Converter;

import br.com.bitwork.entities.enums.TipoGenero;

/**
 * Implementação de Converter para {@link TipoGenero}.
 *
 * @author BitWork.
 */
@Converter
public class TipoGeneroConverter implements AttributeConverter<TipoGenero, Character> {

	/**
	 * @see javax.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.Object)
	 */
	@Override
	public Character convertToDatabaseColumn(final TipoGenero tipoGenero) {
		return tipoGenero != null ? tipoGenero.getId() : null;
	}

	/**
	 * @see javax.persistence.AttributeConverter#convertToEntityAttribute(java.lang.Object)
	 */
	@Override
	public TipoGenero convertToEntityAttribute(final Character id) {
		return id != null ? TipoGenero.getTipoGenero(id) : null;
	}
}