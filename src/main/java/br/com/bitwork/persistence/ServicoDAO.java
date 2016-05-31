/*
 * ServicoDAO.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.persistence;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.com.bitwork.entities.Servico;
import br.com.bitwork.persistence.exception.DAOException;

/**
 * Classe responsável por realizar a persistência de objetos da classe {@link Servico}.
 *
 * @author BitWork.
 */
@Named
@Dependent
public class ServicoDAO extends CondominioGenericDAO<Servico> {

	private static final String GET_ALL = " SELECT servico FROM br.com.bitwork.entities.Servico servico ";
	
	private static final String GET_BY_NOME = " SELECT servico FROM br.com.bitwork.entities.Servico servico "
			+ " WHERE servico.nome = :nome ";
	
	/**
	 * Obtém os todos {@link Servico}s.
	 * 
	 * @return
	 * @throws DAOException
	 * @author BitWork.
	 */
	public List<Servico> getAll() throws DAOException {
		try {
			StringBuilder jpql = new StringBuilder(GET_ALL);
			jpql.append("ORDER BY servico.nome");
			
			TypedQuery<Servico> query = getEntityManager().createQuery(jpql.toString(), Servico.class);
			
			return query.getResultList();
		} catch (PersistenceException e) {
			String msg = "Falha ao obter todos os Serviços.";
			logger.error(msg, e);
			throw new DAOException(msg, e);
		}
	}
	
	/**
	 * Obtém os todos {@link Servico}s segundo o nome informado.
	 * 
	 * @param nome
	 * @return
	 * @throws DAOException
	 * @author BitWork.
	 */
	public List<Servico> getAll(final String nome) throws DAOException {
		try {
			StringBuilder jpql = new StringBuilder(GET_ALL);
			jpql.append("WHERE servico.nome LIKE '%" + nome + "%'");
			jpql.append("ORDER BY servico.nome");
			
			TypedQuery<Servico> query = getEntityManager().createQuery(jpql.toString(), Servico.class);
			
			return query.getResultList();
		} catch (PersistenceException e) {
			String msg = "Falha ao obter todos os Serviços segundo o nome informado.";
			logger.error(msg, e);
			throw new DAOException(msg, e);
		}
	}
	
	/**
	 * Obtém o {@link Servico} segundo o nome informado.
	 * 
	 * @return
	 * @throws DAOException
	 * @author BitWork.
	 */
	public Servico getServico(final String nome) throws DAOException {
		try {
			TypedQuery<Servico> query = getEntityManager().createQuery(GET_BY_NOME, Servico.class);
			query.setParameter("nome", nome);
			
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException e) {
			String msg = "Falha ao obter o Serviço segundo o nome informado.";
			logger.error(msg, e);
			throw new DAOException(msg, e);
		}
	}
}