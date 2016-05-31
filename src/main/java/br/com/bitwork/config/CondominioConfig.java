/*
 * CondominioConfig.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.config;

import java.util.ResourceBundle;

/**
 * Classe de acesso às configurações do sistema.
 *
 * @author BitWork.
 */
public class CondominioConfig {
	
	public static final String CONFIGURACAO_PROPERTIES = "configuracao";
	public static final String EMAIL_HOST = "bitwork.email.servidor";
	public static final String EMAIL_PORTA = "bitwork.email.porta";
	public static final String CHARSET_EMAIL_PADRAO = "bitwork.email.charset.padrao";
    public static final String CHAVE_EMAIL_REMETENTE_PADRAO = "bitwork.email.remetente.padrao";
	public static final String CHAVE_EMAIL_REMETENTE = "bitwork.email.remetente";
	public static final String CHAVE_EMAIL_AUTORIZACAO_LOGIN = "bitwork.email.autorizacao.login";
	public static final String CHAVE_EMAIL_AUTORIZACAO_SENHA = "bitwork.email.autorizacao.senha";
	
	/**
	 * Retorna a configuração segundo a chave informada.
	 * 
	 * @param chave
	 * @return
	 * @author BitWork.
	 */
	public static String getConfiguracao(final String chave) {
		return ResourceBundle.getBundle(CONFIGURACAO_PROPERTIES).getString(chave);
	}
}