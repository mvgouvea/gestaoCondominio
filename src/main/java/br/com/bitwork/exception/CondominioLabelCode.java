/*
 * CondominioLabelCode.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.exception;

/**
 * Enum com os códigos de descrição dos campos necessários para geração da mensagem de campos obrigatórios.
 *
 * @author BitWork.
 */
public enum CondominioLabelCode {
	LABEL_NOME, LABEL_SOBRENOME, LABEL_EMAIL, LABEL_TELEFONE, LABEL_GENERO, LABEL_ESTADO_CIVIL, LABEL_RG, LABEL_CPF, LABEL_DATA_NASCIMENTO, LABEL_CEP,
	LABEL_ESTADO, LABEL_CIDADE, LABEL_BAIRRO, LABEL_ENDERECO,
	
	LABEL_SERVICO, LABEL_DESCRICAO,

	LABEL_SENHA, LABEL_NOVA_SENHA, LABEL_CONFIRMAR_SENHA;
}