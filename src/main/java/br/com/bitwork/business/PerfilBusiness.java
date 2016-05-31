/*
 * GrupoBusiness.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.business;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bitwork.entities.Perfil;
import br.com.bitwork.exception.PerfilException;
import br.com.bitwork.persistence.PerfilDAO;
import br.com.bitwork.persistence.exception.DAOException;

/**
 * Classe responsável por implementar as regras de negócio relativas à classe {@link Perfil}.
 *
 * @author BitWork.
 */
@Named
@Dependent
@Transactional(rollbackOn = PerfilException.class)
public class PerfilBusiness {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Inject
	private PerfilDAO perfilDAO;

	/**
	 * Obtém os todos {@link Perfil}s.
	 *
	 * @return
	 * @throws PerfilException
	 * @author BitWork.
	 */
	public List<Perfil> getAll() throws PerfilException {
		try {
			return perfilDAO.getAll();
		} catch (DAOException e) {
			String msg = "Falha ao obter todos os Perfis.";
			logger.error(msg, e);
			throw new PerfilException(msg, e);
		}
	}

	/**
	 * Salva os {@link Perfil}s informados.
	 *
	 * @param perfis
	 * @throws PerfilException
	 * @author BitWork.
	 */
	public void salvar(final List<Perfil> perfis) throws PerfilException {
		try {
			perfilDAO.persistirEmLote(perfis);
		} catch (DAOException e) {
			String msg = "Falha ao salvar os Perfis informados.";
			logger.error(msg, e);
			throw new PerfilException(msg, e);
		}
	}
}