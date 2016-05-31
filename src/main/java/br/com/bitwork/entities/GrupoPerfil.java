/*
 * GrupoPerfil.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entidade que representa a tabela <b>GRUPO_PERFIL</b>.
 *
 * @author BitWork.
 */
@Entity
@Table(name = "GRUPO_PERFIL")
public class GrupoPerfil implements Entidade {

	private static final long serialVersionUID = -2784613839309411870L;

	@Id
	@Column(name = "ID_GRUPO_PERFIL")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "PERMITIDO")
	private boolean permitido;
	
	@Column(name = "DESABILITADO")
	private boolean desabilitado;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_GRUPO")
	private Grupo grupo;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_PERFIL")
	private Perfil perfil;

	/**
	 * Construtor da classe.
	 *
	 * @author BitWork.
	 */
	public GrupoPerfil() { }

	/**
	 * Construtor da classe.
	 *
	 * @param grupo
	 * @param perfil
	 * @author BitWork.
	 */
	public GrupoPerfil(Grupo grupo, Perfil perfil) {
		this.grupo = grupo;
		this.perfil = perfil;
	}

	/**
	 * @see br.com.bitwork.entities.Entidade#getId()
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the permitido
	 */
	public boolean isPermitido() {
		return permitido;
	}

	/**
	 * @param permitido the permitido to set
	 */
	public void setPermitido(boolean permitido) {
		this.permitido = permitido;
	}

	/**
	 * @return the desabilitado
	 */
	public boolean isDesabilitado() {
		return desabilitado;
	}

	/**
	 * @param desabilitado the desabilitado to set
	 */
	public void setDesabilitado(boolean desabilitado) {
		this.desabilitado = desabilitado;
	}

	/**
	 * @return the grupo
	 */
	public Grupo getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo the grupo to set
	 */
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	/**
	 * @return the perfil
	 */
	public Perfil getPerfil() {
		return perfil;
	}

	/**
	 * @param perfil the perfil to set
	 */
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
}