/*
 * Status.java
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
 * Enum para definição dos possíveis {@link Status}: ATIVO, INATIVO.
 *
 * @author BitWork.
 */
public enum Status {
	
	ATIVO(Boolean.TRUE),
	INATIVO(Boolean.FALSE);
	
	private final Boolean id;
	
	/**
	 * Construtor da classe.
	 *
	 * @param id
	 * @author BitWork.
	 */
	private Status(final Boolean id) {
		this.id = id;
	}

	/**
	 * Obtém o {@link Status} segundo o id informado.
	 * 
	 * @param id
	 * @return
	 * @author BitWork.
	 */
	public static Status getStatus(final Boolean id) {
		Status status = null;

		for (Status valor : values()) {
			if (valor.getId().equals(id)) {
				status = valor;
				break;
			}
		}

		return status;
	}

	/**
	 * @return the id
	 */
	public Boolean getId() {
		return id;
	}
}