/*
 * GrupoController.java
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
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.bitwork.business.PerfilBusiness;
import br.com.bitwork.entities.GrupoPerfil;
import br.com.bitwork.entities.Perfil;
import br.com.bitwork.exception.CondominioMessageCode;
import br.com.bitwork.exception.PerfilException;
import br.com.bitwork.util.CollectionUtil;
import br.com.bitwork.util.Util;

/**
 * Controlador para o Caso de Uso - DECU-DFR-UC.02-DEFINIR GRUPOS.
 *
 * @author BitWork.
 */
@ManagedBean
@ViewScoped
public class GrupoController extends AbstractController {

	private static final long serialVersionUID = -7425616196701359901L;

	@Inject
	private PerfilBusiness perfilBusiness;

	private List<Perfil> perfis;

	/**
	 * @see br.com.bitwork.controller.AbstractController#init()
	 */
	@PostConstruct
	@Override
	public void init() {
		try {
			super.init();
			setPerfis(perfilBusiness.getAll());	
		} catch (PerfilException e) {
			adicionarMensagemErro(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}

	/**
	 * Salva as permissões dos {@link GrupoPerfil}s.
	 *
	 * @author BitWork.
	 */
	public void salvar() {
		try {
			perfilBusiness.salvar(getPerfis());
			adicionarMensagemSucesso(CondominioMessageCode.PERMISSOES_DEFINIDAS_SUCESSO);
		} catch (PerfilException e) {
			adicionarMensagemErro(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}

	/**
	 * Transforma o {@link Set} informado em uma {@link List}.
	 * 
	 * @param set
	 * @return
	 * @author BitWork.
	 */
	public List<GrupoPerfil> setToLista(final Set<GrupoPerfil> set) {
		List<GrupoPerfil> lista = super.toLista(set);
		CollectionUtil.ordenarListaEntidadePorAtributo("grupo.descricao", lista);
	
		return lista;
	}

	/************************************************ Getters and Setters ************************************************/
	/**
	 * @return the perfis
	 */
	public List<Perfil> getPerfis() {
		if (Util.isEmpty(perfis)) {
			setPerfis(new ArrayList<Perfil>());
		}

		return perfis;
	}

	/**
	 * @param perfis the perfis to set
	 */
	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}
}