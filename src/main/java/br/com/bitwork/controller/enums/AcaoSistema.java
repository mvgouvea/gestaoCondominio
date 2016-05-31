/*
 * AcaoSistema.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.controller.enums;

import br.com.bitwork.util.Util;

/**
 * Enum com todas as representações das possíveis ações adotadas na aplicação.
 *
 * @author BitWork.
 */
public enum AcaoSistema {

	LISTAR, INCLUIR, ALTERAR, VISUALIZAR;
	
	/**
	 * Retorna a instância de {@link AcaoSistema} segundo a descrição informada.
	 * 
	 * @param acao
	 * @return
	 * @author BitWork.
	 */
	public static AcaoSistema getAcaoSistema(final String acao) {
		AcaoSistema acaoSelecionada = null;
		if (!Util.isEmpty(acao)) {
			for (AcaoSistema acaoSistema : AcaoSistema.values()) {
				if (acaoSistema.toString().equals(acao)) {
					acaoSelecionada = acaoSistema;
					break;
				}
			}
		}

		return acaoSelecionada;
	}
}
