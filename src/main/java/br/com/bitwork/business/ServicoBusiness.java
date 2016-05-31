/*
 * ServicoBusiness.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bitwork.entities.Pessoa;
import br.com.bitwork.entities.PessoaServico;
import br.com.bitwork.entities.Servico;
import br.com.bitwork.exception.CondominioLabelCode;
import br.com.bitwork.exception.CondominioMessageCode;
import br.com.bitwork.exception.ServicoException;
import br.com.bitwork.persistence.ServicoDAO;
import br.com.bitwork.persistence.exception.DAOException;
import br.com.bitwork.util.Util;

/**
 * Classe responsável por implementar as regras de negócio relativas à classe {@link Servico}.
 *
 * @author BitWork.
 */
@Named
@Dependent
@Transactional(rollbackOn = ServicoException.class)
public class ServicoBusiness {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Inject
	private ServicoDAO servicoDAO;
	
	/**
	 * Obtém os todos {@link Servico}s.
	 *
	 * @return
	 * @throws ServicoException
	 * @author BitWork.
	 */
	public List<Servico> getAll() throws ServicoException {
		try {
			return servicoDAO.getAll();
		} catch (DAOException e) {
			String msg = "Falha ao obter todos os Serviços.";
			logger.error(msg, e);
			throw new ServicoException(msg, e);
		}
	}
	
	/**
	 * Obtém os todos {@link Servico}s segundo o nome informado.
	 *
	 * @param nome
	 * @return
	 * @throws ServicoException
	 * @author BitWork.
	 */
	public List<Servico> getAll(final String nome) throws ServicoException {
		try {
			return servicoDAO.getAll(nome);
		} catch (DAOException e) {
			String msg = "Falha ao obter todos os Serviços segundo o nome informado.";
			logger.error(msg, e);
			throw new ServicoException(msg, e);
		}
	}
	
	/**
	 * Salva o {@link Servico} informado.
	 *
	 * @param servico
	 * @return
	 * @throws ServicoException
	 * @author BitWork.
	 */
	public void salvar(final Servico servico) throws ServicoException {
		try {
			validarCamposObrigatorios(servico);
			validarDuplicidade(servico);
			
			servicoDAO.persistir(servico);
		} catch (DAOException e) {
			String msg = "Falha ao salvar o serviço informado.";
			logger.error(msg, e);
			throw new ServicoException(msg, e);
		}
	}
	
	/**
	 * Exclui o {@link Servico} informado.
	 *
	 * @param servico
	 * @throws ServicoException
	 * @author BitWork.
	 */
	public void excluir(final Servico servico) throws ServicoException {
		try {
			servicoDAO.excluir(servico);
		} catch (DAOException e) {
			String msg = "Falha ao excluir o serviço informado.";
			logger.error(msg, e);
			throw new ServicoException(msg, e);
		}
	}

	/**
	 * Valida se foram encontrados registros na lista de {@link Servico} informada.
	 * 
	 * @param servicos
	 * @throws ServicoException
	 * @author BitWork.
	 */
	public void validarResultadoEncontrado(final List<Servico> servicos) throws ServicoException {
		if (Util.isEmpty(servicos)) {
			throw new ServicoException(CondominioMessageCode.NENHUM_RESULTADO_ENCONTRADO);
		}
	}

	/**
	 * Adiciona o {@link Servico} informado a lista de {@link PessoaServico}.
	 *
	 * @param servico
	 * @param pessoa
	 * @return
	 * @author BitWork.
	 */
	public void adicionarServico(final Servico servico, final Pessoa pessoa) {
		if (Util.isEmpty(pessoa.getServicos())) {
			pessoa.setServicos(new HashSet<PessoaServico>());
		}

		pessoa.getServicos().add(new PessoaServico(pessoa, servico));
	}
	
	
	/**
	 * Verifica se os campos obrigatórios do {@link Servico} foram informados.
	 * 
	 * @param servico
	 * @throws ServicoException
	 * @author BitWork.
	 */
	private void validarCamposObrigatorios(final Servico servico) throws ServicoException {
		List<CondominioLabelCode> labels = new ArrayList<CondominioLabelCode>();
		
		if (Util.isBlank(servico.getNome())) {
			labels.add(CondominioLabelCode.LABEL_SERVICO);
		} if (Util.isBlank(servico.getDescricao())) {
			labels.add(CondominioLabelCode.LABEL_DESCRICAO);
		} if (!Util.isEmpty(labels)) {
			throw new ServicoException(CondominioMessageCode.CAMPOS_OBRIGATORIOS_NAO_INFORMADOS, labels);
		}
	}
	
	/**
	 * Verifica se possui algum serviço com o mesmo nome do {@link Servico} informado.
	 *
	 * @param servico
	 * @author BitWork.
	 * @throws ServicoException 
	 */
	private void validarDuplicidade(final Servico servico) throws ServicoException {
		try {
			Servico servicoEncontrado = servicoDAO.getServico(servico.getNome());
			
			if (servicoEncontrado != null && servicoEncontrado.getId() != servico.getId()) {
				throw new ServicoException(CondominioMessageCode.SERVICO_EXISTENTE);
			}
		} catch (DAOException e) {
			String msg = "Falha ao obter o Serviço segundo o nome informado.";
			logger.error(msg, e);
			throw new ServicoException(msg, e);
		}
	}
}