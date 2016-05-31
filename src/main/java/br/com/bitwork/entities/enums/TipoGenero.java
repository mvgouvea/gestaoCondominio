/*
 * TipoGenero.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.entities.enums;

/**
 * Enum para definição dos possíveis {@link TipoGenero}.
 *
 * @author BitWork.
 */
public enum TipoGenero {
	
	MASCULINO('M', "Masculino"),
	FEMININO('F', "Feminino");

	private final Character id;
	private final String descricao;

	/**
	 * Construtor da classe.
	 *
	 * @param id
	 * @param descricao
	 * @author BitWork.
	 */
	private TipoGenero(final Character id, final String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
	/**
	 * Obtém o {@link TipoGenero} segundo o id informado.
	 * 
	 * @param id
	 * @return
	 * @author BitWork.
	 */
	public static TipoGenero getTipoGenero(final Character id) {
		TipoGenero tipoGenero = null;

		for (TipoGenero tipo : values()) {
			if (tipo.getId().equals(id)) {
				tipoGenero = tipo;
				break;
			}
		}

		return tipoGenero;
	}

	/**
	 * @return the id
	 */
	public Character getId() {
		return id;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
}