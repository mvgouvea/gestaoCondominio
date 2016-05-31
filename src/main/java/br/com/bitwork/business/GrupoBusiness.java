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

import br.com.bitwork.entities.Grupo;
import br.com.bitwork.exception.GrupoException;
import br.com.bitwork.persistence.GrupoDAO;
import br.com.bitwork.persistence.exception.DAOException;

/**
 * Classe responsável por implementar as regras de negócio relativas à classe {@link Grupo}.
 *
 * @author BitWork.
 */
@Named
@Dependent
@Transactional(rollbackOn = GrupoException.class)
public class GrupoBusiness {

private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Inject
	private GrupoDAO grupoDAO;

	/**
	 * Obtém os todos {@link Grupo}s.
	 *
	 * @return
	 * @throws GrupoException
	 * @author BitWork.
	 */
	public List<Grupo> getAll() throws GrupoException {
		try {
			return grupoDAO.getAll();
		} catch (DAOException e) {
			String msg = "Falha ao obter todos os Grupo.";
			logger.error(msg, e);
			throw new GrupoException(msg, e);
		}
	}
}