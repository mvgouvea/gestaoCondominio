/*
 * EmailService.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.bitwork.config.CondominioConfig;
import br.com.bitwork.util.Util;
import br.com.bitwork.util.email.ConstantesEmail;
import br.com.bitwork.util.email.Email;
import br.com.bitwork.util.email.exception.EmailException;

/**
 * Classe de serviço de envio de e-mail do sistema.
 *
 * @author BitWork.
 */
public class EmailService {
	
	/**
	 * Envia o email para o destinatário informado.
	 * 
	 * @param corpo
	 * @param assunto
	 * @param destinatario
	 * 
	 * @throws EmailException
	 */
	public void enviarEmail(final String corpo, final String assunto, final String destinatario) throws EmailException {
		List<String> destinatarios = new ArrayList<String>();
		destinatarios.add(destinatario);
		enviarEmail(corpo, assunto, destinatarios);
	}

	/**
	 * Envia o email para os destinatários informados.
	 * 
	 * @param corpo
	 * @param assunto
	 * @param destinatarios
	 * @throws EmailException
	 * @author BitWork.
	 */
	public void enviarEmail(final String corpo, final String assunto, final List<String> destinatarios) throws EmailException {
		Email email = null;
		if (!Util.isEmpty(destinatarios)) {
			email = new Email();
			email.setAssunto(assunto);
			email.setCorpoEmail(corpo.toString());
			email.setEnderecoRemetente(CondominioConfig.getConfiguracao(CondominioConfig.CHAVE_EMAIL_REMETENTE));
			Integer i = 0;
			for (String destinatario : destinatarios) {
				if (i == 0) {
					email.adicionarDestinatario(destinatario);
				} else {
					email.adicionarDestinatarioEmCopia(destinatario);
				}

				i++;
			}
			
			email.enviar();
		}
	}
	
	/** 
	 * Monta rodapé do email segundo os padrões da BitWork.
	 * 
	 * @param template
	 * @return
	 * @throws IOException
	 * @author BitWork.
	 */
	public String montarRodapeEmail(String template) throws IOException {
		String rodape = lerArquivo(getURLTemplateEmail(ConstantesEmail.TEMPLATE_RODAPE_EMAIL));
		return template.replace(ConstantesEmail.RODAPE_EMAIL, rodape.toString());
	}

	/** 
	 * Recupera o conteudo do arquivo segundo a url informada.
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 * @author BitWork.
	 */
	public String lerArquivo(String url) throws IOException {
		StringBuilder conteudoArquivo = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(url));
		String linha = reader.readLine();

		while (linha != null) {
			conteudoArquivo.append(linha);
			linha = reader.readLine();
		}

		reader.close();
		return conteudoArquivo.toString();
	}

	/**
	 * Recupera o caminho onde o arquivo de template informado está localizado.
	 * 
	 * @param template
	 * @return
	 * @author BitWork.
	 */
	public String getURLTemplateEmail(String template){
		String caminhoTemplates = getClass().getResource(CondominioConfig.getConfiguracao(ConstantesEmail.CHAVE_URL_TEMPLATES_EMAIL)).getFile();
		return caminhoTemplates.concat(template);
	}
}
