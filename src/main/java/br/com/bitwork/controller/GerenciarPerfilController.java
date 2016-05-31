/*
 * GerenciarPerfilController.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.bitwork.entities.Perfil;
import br.com.bitwork.entities.Usuario;
import br.com.bitwork.exception.CondominioMessageCode;
import br.com.bitwork.exception.UsuarioException;

/**
 * Controlador para o Caso de Uso - DECU-DFR-UC.05-GERENCIAR PERFIL.
 *
 * @author BitWork.
 */
@ManagedBean
@ViewScoped
public class GerenciarPerfilController extends ManterUsuarioController {

	private static final long serialVersionUID = -3742875414975362253L;

	private String novaSenha;
	private String confirmacaoSenha;

	/**
	 * @see br.com.bitwork.controller.AbstractController#init()
	 */
	@Override
	@PostConstruct
	public void init() {
		super.init();
	}

	/**
	 * Obtém o {@link Usuario} segundo o id informado.
	 *
	 * @author BitWork.
	 */
	public void carregarUsuario() {
		try {
			setUsuario(usuarioBusiness.getById(getUsuarioLogado().getId()));
			setPerfil(Perfil.Descricao.getPerfil(getUsuario().getPerfil().getDescricao()));
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
			adicionarMensagemSucesso(CondominioMessageCode.USUARIO_SALVO_SUCESSO);
		} catch (UsuarioException e) {
			adicionarMensagemErro(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Salva a senha do {@link Usuario} informado.
	 *
	 * @author BitWork.
	 */
	public void salvarSenha() {
		try {
			usuarioBusiness.alterarSenha(getUsuarioLogado(), getNovaSenha(), getConfirmacaoSenha());
			adicionarMensagemSucesso(CondominioMessageCode.SENHA_ALTERADA);
		} catch (UsuarioException e) {
			adicionarMensagemErro(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Obtém o nome do usuário logado.
	 *
	 * @return
	 * @author BitWork.
	 */
	public String getNomeUsuario() {
		String nome = getUsuarioLogado().getPessoa().getNome();
		return getUsuarioLogado().getPessoa().getSobrenome() != null ? nome.concat(" ").concat(getUsuarioLogado().getPessoa().getSobrenome()) : nome;
	}

	/************************************************ Getters and Setters ************************************************/
	/**
	 * @return the novaSenha
	 */
	public String getNovaSenha() {
		return novaSenha;
	}

	/**
	 * @param novaSenha the novaSenha to set
	 */
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	/**
	 * @return the confirmacaoSenha
	 */
	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	/**
	 * @param confirmacaoSenha the confirmacaoSenha to set
	 */
	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}
}