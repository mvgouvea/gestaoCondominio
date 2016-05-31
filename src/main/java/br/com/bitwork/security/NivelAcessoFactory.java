/*
 * NivelAcessoFactory.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.security;

import java.util.HashMap;
import java.util.Map;

import br.com.bitwork.entities.Usuario;

/**
 *
 *
 * @author BitWork.
 */
public class NivelAcessoFactory {

	private static NivelAcessoFactory instance;
	private final Map<Integer, String> niveisAcessoSistema;

	/**
	 * Construtor privado para garantir o Singleton.
	 * 
	 * Inicia o mapeamento dos níveis de acesso existentes, 
	 * a chave deve ser igual ao valor do campo identificador no banco de dados.
	 */
	private NivelAcessoFactory() {
		niveisAcessoSistema = new HashMap<Integer, String>();
	
		niveisAcessoSistema.put(1, "MANTER_ADMINISTRADOR");
	}
	
	 /* Retorna a instância Singleton de {@link NivelAcessoFactory}.
	 * 
	 * @return
	 */
	public static NivelAcessoFactory getInstance() {
		if (instance == null) {
			instance = new NivelAcessoFactory();
		}
		return instance;
	}

	/**
	 * Recupera o Role para o controle de acesso dos {@link Usuario}s, 
	 * de acordo com id do {@link NivelAcesso} informado.
	 * 
	 * @param id
	 * @return
	 */
	public String getRoleNivelAcessoPorId(Long id) {
		String roleNivelAcesso = null;
		if (niveisAcessoSistema.containsKey(id.intValue())) {
			roleNivelAcesso = niveisAcessoSistema.get(id.intValue());
		}
		return roleNivelAcesso;
	}
	
	/**
	 * Recupera a Role de acesso garantindo o privilégio de <b>Publicar PTI Com Ressalvas – Administração</b>.
	 * @return
	 */
	public String getManterAdministrador() {
		return "MANTER_ADMINISTRADOR";
	}	
}
