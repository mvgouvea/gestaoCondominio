/*
 * UsuarioController.java
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

import org.primefaces.event.TabChangeEvent;

import br.com.bitwork.controller.enums.AcaoSistema;
import br.com.bitwork.entities.Perfil;
import br.com.bitwork.entities.Pessoa;
import br.com.bitwork.entities.Usuario;
import br.com.bitwork.entities.enums.TipoPessoa;
import br.com.bitwork.exception.CondominioMessageCode;
import br.com.bitwork.exception.UsuarioException;
import br.com.bitwork.util.Util;

/**
 * Controlador para o Caso de Uso - DECU-DFR-UC.01-MANTER USUÁRIO.
 *
 * @author BitWork.
 */
@ManagedBean
@ViewScoped
public class UsuarioController extends ManterUsuarioController {
	
	private static final long serialVersionUID = -5381860992739804411L;

	private String nome;

	private List<Usuario> usuarios;

	/**
	 * @see br.com.bitwork.controller.AbstractController#init()
	 */
	@Override
	@PostConstruct
	public void init() {
		try {
			super.init();
			pesquisar();
		} catch (UsuarioException e) {
			adicionarMensagemErro(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}

	/**
	 * Define a ação do sistema para <b>{@link AcaoSistema#LISTAR}</b> e o {@link Perfil} segundo a aba selecionada.
	 *
	 * @author BitWork.
	 */
	public void listar(TabChangeEvent event) {
		try {
			setPerfil(Perfil.Descricao.getPerfil(event.getTab().getTitle()));
			pesquisar();
		} catch (UsuarioException e) {
			adicionarMensagemErro(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}

	/**
	 * Salva o {@link Usuario} informado.
	 * 
	 * @author BitWork.
	 */
	public void salvar() {
		try {
			usuarioBusiness.salvar(getUsuario(), getPerfil(), isIncluir());
			pesquisar();
			adicionarMensagemSucesso(CondominioMessageCode.USUARIO_SALVO_SUCESSO);
		} catch (UsuarioException e) {
			adicionarMensagemErro(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Busca o usuário segundo o nome e o {@link Perfil} informados.
	 * 
	 * @author BitWork.
	 */
	public void buscar() {
		try {
			setUsuarios(usuarioBusiness.getAll(getPerfil(), getNome()));
			usuarioBusiness.validarResultadoEncontrado(getUsuarios());
		} catch (UsuarioException e) {
			adicionarMensagemErro(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Envia o usuário para a tela de cadastro definindo a ação do sistema para <b>{@link AcaoSistema#INCLUIR}</b>.
	 *
	 * @author BitWork.
	 */
	public void novo() {
		setAcaoSistema(AcaoSistema.INCLUIR);
		setUsuario(new Usuario());
		getUsuario().setPessoa(new Pessoa());
		getUsuario().getPessoa().setTipoPessoa(TipoPessoa.FISICA);
	}

	/**
	 * Descarta as informações e volta para a tela principal definindo a ação do sistema para <b>{@link AcaoSistema#LISTAR}</b>.
	 * 
	 * @author BitWork.
	 */
	public void cancelar() {
		try {
			setNome("");
			pesquisar();
		} catch (UsuarioException e) {
			adicionarMensagemErro(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}

	/**
	 * Retorna o número de telefone infomado formatado nos padrões:
	 * Caso o telefone possua o nono dígito: (00) 00000-0000. 
     * Caso o telefone possua oito dígitos: (00) 0000-0000.
	 *
	 * @param telefone
	 * @return
	 * @author BitWork.
	 */
	public String getTelefoneFormatado(String telefone) {
		return Util.getTelefoneFormatado(telefone);
	}

	/**
	 * Obtém os {@link Usuario}s segundo o {@link Perfil} informado.
	 * 
	 * @author BitWork.
	 * @throws UsuarioException 
	 */
	private void pesquisar() throws UsuarioException {
		setAcaoSistema(AcaoSistema.LISTAR);
		setUsuarios(usuarioBusiness.getAll(getPerfil()));
		usuarioBusiness.validarResultadoEncontrado(getUsuarios());
	}
	/************************************************ Getters and Setters ************************************************/
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
	 * @return the usuarios
	 */
	public List<Usuario> getUsuarios() {
		if (Util.isEmpty(usuarios)) {
			setUsuarios(new ArrayList<Usuario>());
		}

		return usuarios;
	}

	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}