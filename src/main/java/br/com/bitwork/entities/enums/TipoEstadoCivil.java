/*
 * TipoEstadoCivil.java
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
 * Enum para definição dos possíveis {@link TipoEstadoCivil}.
 *
 * @author BitWork.
 */
public enum TipoEstadoCivil {
	
	SOLTEIRO(1L, "Solteiro(a)"),
	CASADO(2L, "Casado(a)"),
	DIVORCIADO(3L, "Divorciado(a)"),
	VIUVO(4L, "Viúvo(a)");
	
	private final Long id;
	private final String descricao;
	
	/**
	 * Construtor da classe.
	 *
	 * @param id
	 * @param descricao
	 * @author BitWork.
	 */
	private TipoEstadoCivil(final Long id, final String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	/**
	 * Obtém o {@link TipoEstadoCivil} segundo o id informado.
	 * 
	 * @param id
	 * @return
	 * @author BitWork.
	 */
	public static TipoEstadoCivil getTipoEstadoCivil(final Long id) {
		TipoEstadoCivil tipoEstadoCivil = null;

		for (TipoEstadoCivil tipo : values()) {
			if (tipo.getId().equals(id)) {
				tipoEstadoCivil = tipo;
				break;
			}
		}

		return tipoEstadoCivil;
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