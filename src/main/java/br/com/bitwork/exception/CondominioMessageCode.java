/*
 * CondominioMessageCode.java
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
 * Enum para definição de códigos de exceções/mensagens de negócio.
 *
 * @author BitWork.
 */
public enum CondominioMessageCode {

	ERRO_INESPERADO("ME001"),
	CAMPOS_OBRIGATORIOS_NAO_INFORMADOS("MSG001"),
	NENHUM_RESULTADO_ENCONTRADO("MSG002"),
	
	USUARIO_SALVO_SUCESSO("MSG003"),
	EMAIL_INVALIDO("MSG004"),
	CPF_INVALIDO("MSG005"),
	CEP_INVALIDO("MSG006"),
	CNPJ_INVALIDO("MSG007"),
	USUARIO_EXISTENTE("MSG008"),
	
	SENHAS_NAO_CORRESPONDEM("MSG009"),

	PERMISSOES_DEFINIDAS_SUCESSO("MSG011"),
	
	SERVICO_CADASTRADO_SUCESSO("MSG012"),
	SERVICO_ALTERADO_SUCESSO("MSG013"),
	SERVICO_EXCLUIDO_SUCESSO("MSG015"),
	SERVICO_EXISTENTE("MSG016"),
	SENHA_ENVIADA("MSG017"),
	USUARIO_INEXISTENTE("MSG018"),
	EMAIL_OU_SENHA_INCORRETOS("MSG019"),
	
	SENHA_ALTERADA("MSG020");
	
	private String codigo;

	/**
	 * Construtor da classe.
	 *
	 * @param codigo
	 * @author BitWork.
	 */
	private CondominioMessageCode(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the codigo
	 */
	public String toString() {
		return codigo;
	}
}