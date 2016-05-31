/*
 * TipoPessoa.java
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
 * Enum para definição dos possíveis {@link TipoPessoa}.
 *
 * @author BitWork.
 */
public enum TipoPessoa {
	
	FISICA(1L, "Física"),
	JURIDICA(2L, "Jurídica");
	
	private final Long id;
	private final String descricao;
	
	/**
	 * Construtor da classe.
	 *
	 * @param id
	 * @param descricao
	 * @author BitWork.
	 */
	private TipoPessoa(final Long id, final String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	/**
	 * Obtém o {@link TipoPessoa} segundo o id informado.
	 * 
	 * @param id
	 * @return
	 * @author BitWork.
	 */
	public static TipoPessoa getTipoPessoa(final Long id) {
		TipoPessoa tipoPessoa = null;

		for (TipoPessoa tipo : values()) {
			if (tipo.getId().equals(id)) {
				tipoPessoa = tipo;
				break;
			}
		}

		return tipoPessoa;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
}