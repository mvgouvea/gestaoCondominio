/*
 * ManterUsuarioController.java
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
import javax.inject.Inject;

import br.com.bitwork.business.ServicoBusiness;
import br.com.bitwork.controller.enums.AcaoSistema;
import br.com.bitwork.entities.Perfil;
import br.com.bitwork.entities.PessoaServico;
import br.com.bitwork.entities.Servico;
import br.com.bitwork.entities.Usuario;
import br.com.bitwork.entities.enums.TipoEstadoCivil;
import br.com.bitwork.entities.enums.TipoGenero;
import br.com.bitwork.entities.enums.TipoPessoa;
import br.com.bitwork.exception.ServicoException;
import br.com.bitwork.exception.UsuarioException;
import br.com.bitwork.util.Util;

/**
 * Controlador para a entidade {@link Usuario}.
 *
 * @author BitWork.
 */
public class ManterUsuarioController extends AbstractController {
	private static final long serialVersionUID = -8986720410299046200L;
	
	@Inject
	private ServicoBusiness servicoBusiness;
	
	protected Usuario usuario;
	private Perfil.Descricao perfil = Perfil.Descricao.getPerfil(Perfil.Descricao.ADMINISTRADOR.getDescricao());
	private Servico servico;

	private List<Servico> servicos;

	/**
	 * @see br.com.bitwork.controller.AbstractController#init()
	 */
	@Override
	@PostConstruct
	public void init() {
		try {
			super.init();
			setAcaoSistema(AcaoSistema.LISTAR);
			setServicos(servicoBusiness.getAll());
		} catch (ServicoException e) {
			adicionarMensagemErro(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}

	/**
	 * Envia o usuário para a tela de cadastro definindo a ação do sistema para <b>{@link AcaoSistema#ALTERAR}</b>.
	 *
	 * @param usuario
	 * @author BitWork.
	 */
	public void alterar(Usuario usuario) {
		this.usuario = usuario;
		setAcaoSistema(AcaoSistema.ALTERAR);
	}
	
	/**
	 * Adiciona o {@link Servico} informado a lista de {@link PessoaServico}.
	 *
	 * @author BitWork.
	 */
	public void adicionarServico() {
		if (getServico() != null) {
			servicoBusiness.adicionarServico(getServico(), getUsuario().getPessoa());
			setServico(new Servico());
		}
	}

	/**
	 * Remove o {@link Servico} informado a lista de {@link PessoaServico}.
	 *
	 * @param servico
	 * @author BitWork.
	 */
	public void removerServico(PessoaServico servico) {
		getUsuario().getPessoa().getServicos().remove(servico);
		setServico(new Servico());
	}
	
	/**
	 * Verifica se o {@link TipoPessoa} selecionado é <b>{@link TipoPessoa#FISICA}</b>.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public boolean isPessoaFisica() {
		return getUsuario().getPessoa() != null && TipoPessoa.FISICA.equals(getUsuario().getPessoa().getTipoPessoa());
	}
	
	/**
	 * Verifica se o {@link Perfil} selecionado é <b>{@link Perfil.Descricao#ADMINISTRADOR}</b>.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public boolean isAdministrador() {
		return getPerfil() != null && Perfil.Descricao.ADMINISTRADOR.equals(getPerfil());
	}

	/**
	 * Verifica se o {@link Perfil} selecionado é <b>{@link Perfil.Descricao#FUNCIONARIO}</b>.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public boolean isFuncionario() {
		return getPerfil() != null && Perfil.Descricao.FUNCIONARIO.equals(getPerfil());
	}

	/**
	 * Verifica se o {@link Perfil} selecionado é <b>{@link Perfil.Descricao#FORNECEDOR}</b>.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public boolean isFornecedor() {
		return getPerfil() != null && Perfil.Descricao.FORNECEDOR.equals(getPerfil());
	}

	/**
	 * Verifica se o {@link Perfil} selecionado é <b>{@link Perfil.Descricao#LOCATARIO}</b>.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public boolean isLocatario() {
		return getPerfil() != null && Perfil.Descricao.LOCATARIO.equals(getPerfil());
	}
	
	/**
	 * Obtém o endereço do {@link Usuario} informado segundo o CEP.
	 *
	 * @author BitWork.
	 */
	public void buscarEndereco() {
		try {
			usuarioBusiness.buscarEndereco(getUsuario());
		} catch (UsuarioException e) {
			adicionarMensagemErro(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}

	/************************************************ Getters and Setters ************************************************/
	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	/**
	 * @return the perfil
	 */
	public Perfil.Descricao getPerfil() {
		return perfil;
	}

	/**
	 * @param perfil the perfil to set
	 */
	public void setPerfil(Perfil.Descricao perfil) {
		this.perfil = perfil;
	}

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
	 * @return the perfis
	 */
	public Perfil.Descricao[] getPerfis() {
		return Perfil.Descricao.values();
	}
	
	/**
	 * @return the tiposGeneros
	 */
	public TipoGenero[] getTiposGeneros() {
		return TipoGenero.values();
	}
	
	/**
	 * @return the tiposEstadoCivil
	 */
	public TipoEstadoCivil[] getTiposEstadoCivil() {
		return TipoEstadoCivil.values();
	}
	
	/**
	 * @return the tiposPessoa
	 */
	public TipoPessoa[] getTiposPessoa() {
		return TipoPessoa.values();
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
}