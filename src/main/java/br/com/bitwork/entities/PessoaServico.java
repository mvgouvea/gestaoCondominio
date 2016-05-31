/*
 * PessoaServico.java
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
 * Entidade que representa a tabela <b>PESSOA_SERVICO</b>.
 *
 * @author BitWork.
 */
@Entity
@Table(name = "PESSOA_SERVICO")
public class PessoaServico implements Entidade {
	
	private static final long serialVersionUID = 7220958280974920819L;

	@Id
	@Column(name = "ID_PESSOA_SERVICO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_PESSOA")
	private Pessoa pessoa;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_SERVICO")
	private Servico servico;

	/**
	 * Construtor da classe.
	 *
	 * @author BitWork.
	 */
	public PessoaServico() { }
	
	/**
	 * Construtor da classe.
	 *
	 * @param pessoa
	 * @param servico
	 * @author BitWork.
	 */
	public PessoaServico(Pessoa pessoa, Servico servico) {
		this.pessoa = pessoa;
		this.servico = servico;
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
	 * @return the pessoa
	 */
	public Pessoa getPessoa() {
		return pessoa;
	}

	/**
	 * @param pessoa the pessoa to set
	 */
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pessoa == null) ? 0 : pessoa.hashCode());
		result = prime * result + ((servico == null) ? 0 : servico.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PessoaServico other = (PessoaServico) obj;
		if (pessoa == null) {
			if (other.pessoa != null)
				return false;
		} else if (!pessoa.equals(other.pessoa))
			return false;
		if (servico == null) {
			if (other.servico != null)
				return false;
		} else if (!servico.equals(other.servico))
			return false;
		return true;
	}
}