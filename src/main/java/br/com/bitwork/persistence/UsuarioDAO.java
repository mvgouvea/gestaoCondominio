/*
 * UsuarioDAO.java
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

import br.com.bitwork.entities.Perfil;
import br.com.bitwork.entities.Usuario;
import br.com.bitwork.persistence.exception.DAOException;
import br.com.bitwork.util.Util;

/**
 * Classe responsável por realizar a persistência de objetos da classe {@link Usuario}.
 *
 * @author BitWork.
 */
@Named
@Dependent
public class UsuarioDAO extends CondominioGenericDAO<Usuario> {

	private static final String GET_ALL_BY_TIPO_USUARIO = " SELECT usuario FROM br.com.bitwork.entities.Usuario usuario "
			+ " INNER JOIN FETCH usuario.pessoa pessoa "
			+ " WHERE usuario.perfil.id = :perfil ";

	private static final String GET_BY_ID = " SELECT usuario FROM br.com.bitwork.entities.Usuario usuario "
			+ " INNER JOIN FETCH usuario.pessoa pessoa "
			+ " INNER JOIN FETCH usuario.perfil "
			+ " WHERE usuario.id = :usuario";
	
	private static final String GET_BY_EMAIL = " SELECT usuario FROM br.com.bitwork.entities.Usuario usuario "
			+ " INNER JOIN FETCH usuario.pessoa pessoa "
			+ " INNER JOIN FETCH usuario.perfil "
			+ " WHERE usuario.email = :email";
	
	/**
	 * Obtém os {@link Usuario}s segundo o {@link Perfil} informado.
	 * 
	 * @param perfil
	 * @return
	 * @throws DAOException
	 * @author BitWork.
	 */
	public List<Usuario> getAll(final Perfil perfil) throws DAOException {
		try {
			TypedQuery<Usuario> query = getEntityManager().createQuery(GET_ALL_BY_TIPO_USUARIO, Usuario.class);
			query.setParameter("perfil", perfil.getId());
			
			return query.getResultList();
		} catch (PersistenceException e) {
			String msg = "Falha ao obter os Usuários segundo o Perfil informado.";
			logger.error(msg, e);
			throw new DAOException(msg, e);
		}
	}
	
	/**
	 * Obtém os {@link Usuario}s segundo o {@link Perfil} e nome informados.
	 * 
	 * @param perfil
	 * @param nome
	 * @return
	 * @throws DAOException
	 * @author BitWork.
	 */
	public List<Usuario> getAll(final Perfil perfil, final String nome) throws DAOException {
		try {
			StringBuilder jpql = new StringBuilder(GET_ALL_BY_TIPO_USUARIO);
			jpql.append("AND ((pessoa.nome LIKE '%" + nome + "%') OR (usuario.email LIKE '%" + nome + "%')) ");
			
			TypedQuery<Usuario> query = getEntityManager().createQuery(jpql.toString(), Usuario.class);
			query.setParameter("perfil", perfil.getId());
			
			return query.getResultList();
		} catch (PersistenceException e) {
			String msg = "Falha ao obter os Usuários segundo o Perfil e nome informados.";
			logger.error(msg, e);
			throw new DAOException(msg, e);
		}
	}

	/**
	 * Obtém o {@link Usuario} segundo o id informado.
	 * 
	 * @param id
	 * @return
	 * @throws DAOException
	 * @author BitWork.
	 */
	public Usuario getById(final Long id) throws DAOException {
		try {
			TypedQuery<Usuario> query = getEntityManager().createQuery(GET_BY_ID, Usuario.class);
			query.setParameter("usuario", id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException e) {
			String msg = "Falha ao obter o Usuário segundo o id informado.";
			logger.error(msg, e);
			throw new DAOException(msg, e);
		}
	}

	/**
	 * Obtém o {@link Usuario} segundo o e-mail informado.
	 * 
	 * @param email
	 * @return
	 * @throws DAOException
	 * @author BitWork.
	 */
	public Usuario getByEmail(final String email) throws DAOException {
		try {
			TypedQuery<Usuario> query = getEntityManager().createQuery(GET_BY_EMAIL, Usuario.class);
			query.setParameter("email", email);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException e) {
			String msg = "Falha ao obter o Usuário segundo o e-mail informado.";
			logger.error(msg, e);
			throw new DAOException(msg, e);
		}
	}

	/**
	 * Obtém o {@link Usuario} segundo o e-mail, CPF ou CNPJ informados.
	 * 
	 * @param email
	 * @param cpf
	 * @param cnpj
	 * @return
	 * @throws DAOException
	 * @author BitWork.
	 */
	public Usuario getUsuario(final String email, final String cpf, String cnpj) throws DAOException {
		try {
			StringBuilder jpql = new StringBuilder(GET_BY_EMAIL);
			
			if (!Util.isBlank(cpf)) {
				jpql.append(" AND pessoa.cpf LIKE '%" + cpf + "%' ");
			} else {
				jpql.append(" AND pessoa.cnpj LIKE '%" + cnpj + "%' ");
			}

			TypedQuery<Usuario> query = getEntityManager().createQuery(jpql.toString(), Usuario.class);
			query.setParameter("email", email);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException e) {
			String msg = "Falha ao obter o Usuário segundo o e-mail, CPF ou CNPJ informados.";
			logger.error(msg, e);
			throw new DAOException(msg, e);
		}
	}
}