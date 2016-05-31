/*
 * ServicoController.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.bitwork.business.ServicoBusiness;
import br.com.bitwork.controller.enums.AcaoSistema;
import br.com.bitwork.entities.Servico;
import br.com.bitwork.entities.Perfil;
import br.com.bitwork.entities.Usuario;
import br.com.bitwork.exception.CondominioMessageCode;
import br.com.bitwork.exception.ServicoException;
import br.com.bitwork.util.Util;

/**
 * Controlador para o Caso de Uso - DECU-DFR-UC.03-MANTER SERVIÇO.
 *
 * @author BitWork.
 */
@ManagedBean
@ViewScoped
public class ServicoController extends AbstractController {

	private static final long serialVersionUID = 5781881648104907271L;

	@Inject
	private ServicoBusiness servicoBusiness;

	private Servico servico;

	private List<Servico> servicos;

	private String nome;

	private boolean exibirModalExclusao;
	
	/**
	 * @see br.com.bitwork.controller.AbstractController#init()
	 */
	@PostConstruct
	@Override
	public void init() {
		try {
			super.init();
			limpar();
		} catch (ServicoException e) {
			adicionarMensagemErro(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}

	/**
	 * Busca o {@link Servico} segundo o nome informado.
	 * 
	 * @author BitWork.
	 */
	public void buscar() {
		try {
			setServicos(servicoBusiness.getAll(getNome()));
			servicoBusiness.validarResultadoEncontrado(getServicos());
		} catch (ServicoException e) {
			adicionarMensagemErro(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Descarta as alterações definindo a ação do sistema para <b>{@link AcaoSistema#LISTAR}</b>.
	 * 
	 * @author BitWork.
	 */
	public void cancelar() {
		try {
			limpar();
		} catch (ServicoException e) {
			adicionarMensagemErro(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}

	/**
	 * Salva o {@link Servico} informado.
	 *
	 * @author BitWork.
	 */
	public void salvar() {
		try {
			servicoBusiness.salvar(getServico());
			adicionarMensagemSucesso(isListar() ? CondominioMessageCode.SERVICO_CADASTRADO_SUCESSO : CondominioMessageCode.SERVICO_ALTERADO_SUCESSO);
			limpar();
		} catch (ServicoException e) {
			adicionarMensagemErro(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Envia o usuário para a tela de cadastro para alteração do {@link Servico} informado.
	 * 
	 * @param servico
	 * @author BitWork.
	 */
	public void alterar(final Servico servico) {
		this.servico = servico;
		setAcaoSistema(AcaoSistema.ALTERAR);
	}

	/**
	 * Abre o modal de confirmação de exclusão de serviço.
	 * 
	 * @author BitWork.
	 */
	public void abrirModalExclusao() {
		setExibirModalExclusao(Boolean.TRUE);
	}
	
	/**
	 * Fecha o modal de confirmação de exclusão de serviço.
	 *
	 * @author BitWork.
	 */
	public void fecharModalExclusao() {
		setExibirModalExclusao(Boolean.FALSE);
	}
	
	/**
	 * Exclui o {@link Servico} informado.
	 *
	 * @param servico
	 * @author BitWork.
	 */
	public void excluir() {
		try {
			servicoBusiness.excluir(servico);
			fecharModalExclusao();
			adicionarMensagemSucesso(CondominioMessageCode.SERVICO_EXCLUIDO_SUCESSO);
			limpar();
		} catch (ServicoException e) {
			adicionarMensagemErro(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Obtém os {@link Usuario}s segundo o {@link Perfil} informado.
	 * 
	 * @throws ServicoException
	 * @author BitWork.
	 */
	private void pesquisar() throws ServicoException {
		setServicos(servicoBusiness.getAll());
		servicoBusiness.validarResultadoEncontrado(getServicos());
	}
	
	/**
	 * Limpa os dados definindo a ação do sistema para <b>{@link AcaoSistema#LISTAR}</b>.
	 * 
	 * @throws ServicoException
	 * @author BitWork.
	 */
	private void limpar() throws ServicoException {
		setServico(new Servico());
		setNome("");
		setAcaoSistema(AcaoSistema.LISTAR);
		pesquisar();
	}
	/************************************************ Getters and Setters ************************************************/
	/**
	 * @return the servico
	 */
	public Servico getServico() {
		return servico;
	}

	/**
	 * @param servico the servico to set
	 */
	public void setServico(Servico servico) {
		this.servico = servico;
	}

	/**
	 * @return the servicos
	 */
	public List<Servico> getServicos() {
		if (Util.isEmpty(servicos)) {
			setServicos(new ArrayList<Servico>());
		}

		return servicos;
	}

	/**
	 * @param servicos the servicos to set
	 */
	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the exibirModalExclusao
	 */
	public boolean isExibirModalExclusao() {
		return exibirModalExclusao;
	}

	/**
	 * @param exibirModalExclusao the exibirModalExclusao to set
	 */
	public void setExibirModalExclusao(boolean exibirModalExclusao) {
		this.exibirModalExclusao = exibirModalExclusao;
	}
}