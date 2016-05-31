/*
 * Perfil.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidade que representa a tabela <b>PERFIL</b>.
 *
 * @author BitWork.
 */
@Entity
@Table(name = "PERFIL")
public class Perfil implements Entidade {

	private static final long serialVersionUID = 5053778842824801129L;

	@Id
	@Column(name = "ID_PERFIL")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "DESCRICAO")
	private String descricao;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "perfil", cascade = CascadeType.ALL)
	private Set<GrupoPerfil> grupos;

	/**
	 * Construtor da classe.
	 *
	 * @author BitWork.
	 */
	public Perfil() { }
	
	/**
	 * Construtor da classe.
	 *
	 * @param pefil
	 * @author BitWork.
	 */
	public Perfil(Perfil.Descricao pefil) {
		this.id = pefil.getId();
		this.descricao = pefil.getDescricao();
	}

	/**
	 * @see br.com.bitwork.entities.Entidade#getId()
	 */
	@Override
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
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the grupos
	 */
	public Set<GrupoPerfil> getGrupos() {
		return grupos;
	}

	/**
	 * @param grupos the grupos to set
	 */
	public void setGrupos(Set<GrupoPerfil> grupos) {
		this.grupos = grupos;
	}

	/**
	 * Enum para definição dos possíveis {@link Perfil}.
	 * 
	 * @author BitWork.
	 */
	public enum Descricao {

		ADMINISTRADOR(1L, "Administrador"),
		CONDOMINO(2L, "Condômino"),
		FORNECEDOR(3L, "Fornecedor"),
		FUNCIONARIO(4L, "Funcionário"),
		LOCATARIO(5L, "Locatário");

		private final Long id;
		private final String descricao;

		/**
		 * Construtor da classe.
		 *
		 * @param id
		 * @param descricao
		 * @author BitWork.
		 */
		private Descricao(Long id, String descricao) {
			this.id = id;
			this.descricao = descricao;
		}

		/**
		 * Obtém a {@link Descricao} do {@link Perfil} segundo a descricao informada.
		 * 
		 * @param valor
		 * @return
		 * @author BitWork.
		 */
		public static Descricao getPerfil(final String valor) {
			Descricao perfil = null;

			for (Descricao descricao : Descricao.values()) {
				if (descricao.descricao.equals(valor)) {
					perfil = descricao;
					break;
				}
			}

			return perfil;
		}

		/**
		 * @return the id
		 */
		public Long getId() {
			return id;
		}

		/**
		 * @return the descricao
		 */
		public String getDescricao() {
			return descricao;
		}
	}
}