/*
 * GrupoDAO.java
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

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.com.bitwork.entities.Grupo;
import br.com.bitwork.persistence.exception.DAOException;

/**
 * Classe responsável por realizar a persistência de objetos da classe {@link Grupo}.
 *
 * @author BitWork.
 */
public class GrupoDAO extends CondominioGenericDAO<Grupo> {

	private static final String GET_ALL = " SELECT grupo FROM br.com.bitwork.entities.Grupo grupo"
			+ " ORDER BY grupo.descricao ";
	
	/**
	 * Obtém os todos {@link Grupo}s.
	 * 
	 * @return
	 * @throws DAOException
	 * @author BitWork.
	 */
	public List<Grupo> getAll() throws DAOException {
		try {
			TypedQuery<Grupo> query = getEntityManager().createQuery(GET_ALL, Grupo.class);
			
			return query.getResultList();
		} catch (PersistenceException e) {
			String msg = "Falha ao obter todos os Grupo.";
			logger.error(msg, e);
			throw new DAOException(msg, e);
		}
	}
}