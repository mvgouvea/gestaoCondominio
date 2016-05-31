/*
 * Entidade.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.entities;

import java.io.Serializable;

/**
 * Interface padrão para as entidades do sistema.
 *
 * @author BitWork.
 */
public interface Entidade extends Serializable {

	/**
	 * @return the id.
	 * @author BitWork.
	 */
	public Object getId();
}