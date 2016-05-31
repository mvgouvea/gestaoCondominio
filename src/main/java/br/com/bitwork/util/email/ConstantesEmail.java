/*
 * ConstantesEmail.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.util.email;

/**
 * Classe onde serão mantidas as constantes necessárias para o envio de e-mails.
 *
 * @author BitWork.
 */
public class ConstantesEmail {

	public static final String CHAVE_URL_TEMPLATES_EMAIL = "bitwork.email.url.template.externo";

	public static final String TEMPLATE_RODAPE_EMAIL = "rodape_email.html";
	public static final String TEMPLATE_AVISO_CADASTRO_USUARIO = "aviso_cadastro_usuario.html";
	public static final String TEMPLATE_AVISO_RECUPERAR_SENHA = "aviso_recuperar_senha.html";
	
	public static final String RODAPE_EMAIL = "[RODAPE_EMAIL]";
	public static final String NOME = "[NOME]";
	public static final String LOGIN = "[LOGIN]";
	public static final String SENHA = "[SENHA]";
	public static final String DATA = "[DATA]";
	public static final String HORA = "[HORA]";
}