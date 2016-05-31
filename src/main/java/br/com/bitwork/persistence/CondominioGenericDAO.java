/*
 * CondominioGenericDAO.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.bitwork.entities.Entidade;

/**
 * Classe mãe de todas as classes DAO.
 *
 * @author BitWork.
 */
public class CondominioGenericDAO<T extends Entidade> extends GenericDAO<T> {

	@PersistenceContext(unitName = "condominio")
	private EntityManager entityManager;

	/**
	 * @see br.com.labtrans.persistence.GenericDAO#getEntityManager()
	 */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
}