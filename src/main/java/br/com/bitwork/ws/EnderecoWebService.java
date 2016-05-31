/*
 * EnderecoWebService.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.bitwork.util.Util;
import br.com.bitwork.ws.exception.EnderecoException;
import br.com.bitwork.ws.model.Endereco;

import com.google.gson.Gson;

/**
 * WebService de busca de endereço segundo o CEP informado.
 *
 * @author BitWork.
 */
public class EnderecoWebService {
	
	private static final String CONSTANTE_CEP = "codigoCep";
	private static final String URL_SERVICO = "http://viacep.com.br/ws/" + CONSTANTE_CEP + "/json/";

	/**
	 * Obtém o endereço segundo o cep informado.
	 *
	 * @param cep
	 * @return
	 * @throws EnderecoException
	 * @author BitWork.
	 */
	public static Endereco getEndereco(final String cep) throws EnderecoException {
		HttpURLConnection httpURLConnection = null;
		try {
			URL url = new URL(URL_SERVICO.replace(CONSTANTE_CEP, cep));
		
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("Accept", "application/json");
			
			InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			
			Endereco endereco = new Gson().fromJson(bufferedReader, Endereco.class);
			
			return !Util.isBlank(endereco.getUf()) ? endereco : null;
		} catch (IOException e) {
			throw new EnderecoException(e);
		} finally {
			httpURLConnection.disconnect();
		}
	}
}
