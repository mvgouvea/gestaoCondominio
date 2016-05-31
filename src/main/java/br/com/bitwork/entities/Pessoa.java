/*
 * Pessoa.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.bitwork.entities.converter.TipoEstadoCivilConverter;
import br.com.bitwork.entities.converter.TipoGeneroConverter;
import br.com.bitwork.entities.converter.TipoPessoaConverter;
import br.com.bitwork.entities.enums.TipoEstadoCivil;
import br.com.bitwork.entities.enums.TipoGenero;
import br.com.bitwork.entities.enums.TipoPessoa;

/**
 * Entidade que representa a tabela <b>PESSOA</b>.
 *
 * @author BitWork.
 */
@Entity
@Table(name = "PESSOA")
public class Pessoa implements Entidade {
	
	private static final long serialVersionUID = 3979101752192844807L;

	@Id
	@Column(name = "ID_PESSOA")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NOME")
	private String nome;

	@Column(name = "SOBRENOME")
	private String sobrenome;

	@Column(name = "TELEFONE")
	private String telefone;
	
	@Column(name = "RG")
	private Long rg;
	
	@Column(name = "CPF")
	private String cpf;
	
	@Column(name = "CNPJ")
	private String cnpj;
	
	@Column(name = "DATA_NASCIMENTO")
	private Date dataNascimento;
	
	@Column(name = "CEP")
	private String cep;
	
	@Column(name = "ESTADO")
	private String estado;
	
	@Column(name = "CIDADE")
	private String cidade;
	
	@Column(name = "BAIRRO")
	private String bairro;
	
	@Column(name = "numero")
	private String numero;
	
	@Column(name = "ENDERECO")
	private String endereco;
	
	@Column(name = "COMPLEMENTO")
	private String complemento;
	
	@Column(name = "ID_TIPO_GENERO")
	@Convert(converter = TipoGeneroConverter.class)
	private TipoGenero tipoGenero;
	
	@Column(name = "ID_TIPO_ESTADO_CIVIL")
	@Convert(converter = TipoEstadoCivilConverter.class)
	private TipoEstadoCivil tipoEstadoCivil;
	
	@Column(name = "ID_TIPO_PESSOA")
	@Convert(converter = TipoPessoaConverter.class)
	private TipoPessoa tipoPessoa;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa", cascade = CascadeType.ALL)
	private Set<PessoaServico> servicos;
	
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
	 * @return the sobrenome
	 */
	public String getSobrenome() {
		return sobrenome;
	}

	/**
	 * @param sobrenome the sobrenome to set
	 */
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	/**
	 * @return the telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone the telefone to set
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return the rg
	 */
	public Long getRg() {
		return rg;
	}

	/**
	 * @param rg the rg to set
	 */
	public void setRg(Long rg) {
		this.rg = rg;
	}

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the cnpj
	 */
	public String getCnpj() {
		return cnpj;
	}

	/**
	 * @param cnpj the cnpj to set
	 */
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	/**
	 * @return the dataNascimento
	 */
	public Date getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * @param dataNascimento the dataNascimento to set
	 */
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/**
	 * @return the cep
	 */
	public String getCep() {
		return cep;
	}

	/**
	 * @param cep the cep to set
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * @param cidade the cidade to set
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * @return the bairro
	 */
	public String getBairro() {
		return bairro;
	}

	/**
	 * @param bairro the bairro to set
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return the endereco
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the complemento
	 */
	public String getComplemento() {
		return complemento;
	}

	/**
	 * @param complemento the complemento to set
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	/**
	 * @return the tipoGenero
	 */
	public TipoGenero getTipoGenero() {
		return tipoGenero;
	}

	/**
	 * @param tipoGenero the tipoGenero to set
	 */
	public void setTipoGenero(TipoGenero tipoGenero) {
		this.tipoGenero = tipoGenero;
	}

	/**
	 * @return the tipoEstadoCivil
	 */
	public TipoEstadoCivil getTipoEstadoCivil() {
		return tipoEstadoCivil;
	}

	/**
	 * @param tipoEstadoCivil the tipoEstadoCivil to set
	 */
	public void setTipoEstadoCivil(TipoEstadoCivil tipoEstadoCivil) {
		this.tipoEstadoCivil = tipoEstadoCivil;
	}

	/**
	 * @return the tipoPessoa
	 */
	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	/**
	 * @param tipoPessoa the tipoPessoa to set
	 */
	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	/**
	 * @return the servicos
	 */
	public Set<PessoaServico> getServicos() {
		return servicos;
	}

	/**
	 * @param servicos the servicos to set
	 */
	public void setServicos(Set<PessoaServico> servicos) {
		this.servicos = servicos;
	}
}