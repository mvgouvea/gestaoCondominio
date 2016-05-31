/*
 * PerfilDAO.java
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

import br.com.bitwork.entities.Perfil;
import br.com.bitwork.persistence.exception.DAOException;

/**
 * Classe responsável por realizar a persistência de objetos da classe {@link Perfil}.
 *
 * @author BitWork.
 */
public class PerfilDAO extends CondominioGenericDAO<Perfil> {

	private static final String GET_ALL = " SELECT DISTINCT perfil FROM br.com.bitwork.entities.Perfil perfil "
			+ " LEFT JOIN FETCH perfil.grupos grupoPerfil"
			+ " LEFT JOIN FETCH grupoPerfil.perfil  "
			+ " LEFT JOIN FETCH grupoPerfil.grupo  "
			+ " ORDER BY perfil.descricao ";
	
	/**
	 * Obtém os todos {@link Perfil}s.
	 * 
	 * @return
	 * @throws DAOException
	 * @author BitWork.
	 */
	public List<Perfil> getAll() throws DAOException {
		try {
			TypedQuery<Perfil> query = getEntityManager().createQuery(GET_ALL, Perfil.class);
			
			return query.getResultList();
		} catch (PersistenceException e) {
			String msg = "Falha ao obter todos os Perfis.";
			logger.error(msg, e);
			throw new DAOException(msg, e);
		}
	}
}