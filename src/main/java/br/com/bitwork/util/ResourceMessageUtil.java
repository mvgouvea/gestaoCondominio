/*
 * ResourceMessageUtil.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import br.com.bitwork.constantes.Constantes;

/**
 * Classe utilitária que provê os métodos para manipulação do arquivo *.properties  com as mensagens e descrições apresentadas no sistema. 
 *
 * @author BitWork.
 */
public class ResourceMessageUtil {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(Constantes.MESSAGE_PROPERTIES);
	private static final String CHAVE_INVALIDA = "A chave do atributo informado é inválida.";
	
	/**
	 * Retorna a mensagem associada a chave informada.
	 * 
	 * @param chave
	 * @return
	 * @author BitWork.
	 */
	public static String getDescricao(final String chave) {
		if (chave == null || chave.length() == 0) {
			throw new IllegalArgumentException(CHAVE_INVALIDA);
		}

		return RESOURCE_BUNDLE.getString(chave);
	}
	
	/**
	 * Retorna a mensagem associada a chave informada.
	 * 
	 * @param code
	 * @return
	 * @author BitWork.
	 */
	public static String getDescricao(final Object code) {
		return getDescricao(code.toString());
	}
	
	/**
	 * Retorna a mensagem de erro formatada com a descrição do {@link Throwable} informado.
	 * 
	 * @param chave
	 * @param e
	 * @return
	 * @author BitWork.
	 */
	public static String getDescricaoErro(final String chave, final Throwable e) {
		String descricao = getDescricao(chave);		
		return MessageFormat.format(descricao, e);
	}
	
	/**
	 * Retorna a mensagem de erro formatada com a descrição do {@link Throwable} informado.
	 * 
	 * @param chave
	 * @param e
	 * @return
	 * @author BitWork.
	 */
	public static String getDescricaoErro(final Object chave, final Throwable e) {	
		return getDescricaoErro(chave.toString(), e);
	}
}