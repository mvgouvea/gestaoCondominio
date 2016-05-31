/*
 * Email.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.util.email;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.mail.HtmlEmail;

import br.com.bitwork.config.CondominioConfig;
import br.com.bitwork.util.Util;
import br.com.bitwork.util.email.exception.EmailException;

/**
 * Classe de envio de e-mail do sistema.
 *
 * @author BitWork.
 */
public class Email {

	private static final String ERRO_DESTINATARIO = "É necessário definir pelo menos um destinatário.";

	private String enderecoRemetente;
	private Set<String> destinatarios;
	private Set<String> destinatariosEmCopia;
	private Set<String> destinatariosEmCopiaOculta;
	private String assunto;
	private String corpoEmail;
	private String charset;

	/**
	 * @return the enderecoRemetente
	 */
	public String getEnderecoRemetente() {
		return enderecoRemetente;
	}

	/**
	 * @param enderecoRemetente the enderecoRemetente to set
	 */
	public void setEnderecoRemetente(String enderecoRemetente) {
		this.enderecoRemetente = enderecoRemetente;
	}

	/**
	 * @return the destinatarios
	 */
	public Set<String> getDestinatarios() {
		if(Util.isEmpty(destinatarios)) {
			setDestinatarios(new LinkedHashSet<>());
		}

		return destinatarios;
	}

	/**
	 * @param destinatarios the destinatarios to set
	 */
	public void setDestinatarios(Set<String> destinatarios) {		
		this.destinatarios = destinatarios;
	}

	/**
	 * @return the destinatariosEmCopia
	 */
	public Set<String> getDestinatariosEmCopia() {
		if(Util.isEmpty(destinatariosEmCopia)) {
			setDestinatariosEmCopia(new LinkedHashSet<>());
		}
		
		return destinatariosEmCopia;
	}

	/**
	 * @param destinatariosEmCopia the destinatariosEmCopia to set
	 */
	public void setDestinatariosEmCopia(Set<String> destinatariosEmCopia) {
		this.destinatariosEmCopia = destinatariosEmCopia;
	}

	/**
	 * @return the destinatariosEmCopiaOculta
	 */
	public Set<String> getDestinatariosEmCopiaOculta() {
		if(Util.isEmpty(destinatariosEmCopiaOculta)) {
			setDestinatariosEmCopiaOculta(new LinkedHashSet<>());
		}
		
		return destinatariosEmCopiaOculta;
	}

	/**
	 * @param destinatariosEmCopiaOculta the destinatariosEmCopiaOculta to set
	 */
	public void setDestinatariosEmCopiaOculta(Set<String> destinatariosEmCopiaOculta) {
		this.destinatariosEmCopiaOculta = destinatariosEmCopiaOculta;
	}

	/**
	 * @return the assunto
	 */
	public String getAssunto() {
		return assunto;
	}

	/**
	 * @param assunto the assunto to set
	 */
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	/**
	 * @return the corpoEmail
	 */
	public String getCorpoEmail() {
		return corpoEmail;
	}

	/**
	 * @param corpoEmail the corpoEmail to set
	 */
	public void setCorpoEmail(String corpoEmail) {
		this.corpoEmail = corpoEmail;
	}

	/**
	 * @return the charset
	 */
	public String getCharset() {
		return this.charset == null ? CondominioConfig.getConfiguracao(CondominioConfig.CHARSET_EMAIL_PADRAO) : this.charset;
	}

	/**
	 * @param charset the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * Adiciona o destinatário informado.
	 *
	 * @param email
	 * @author BitWork.
	 */
	public void adicionarDestinatario(final String email) {
		if (!Util.isEmpty(email)) {
			getDestinatarios().add(email.toLowerCase().trim());
		}
	}
	
	/**
	 * Adiciona em cópia o destinatário informado.
	 * 
	 * @param email
	 * @author BitWork.
	 */
	public void adicionarDestinatarioEmCopia(final String email) {
		if (!Util.isEmpty(email)) {
			getDestinatariosEmCopia().add(email.toLowerCase().trim());
		}
	}
	
	/**
	 * Envia o e-mail segundo as definições informadas.
	 *
	 * @throws EmailException
	 * @author BitWork.
	 */
	public void enviar() throws EmailException {
		validar();
		try {
			HtmlEmail htmlEmail = getHtmlEmail();
			htmlEmail.send();
		} catch (Exception e) {
			throw new EmailException("Erro ao montar e-mail.", e);
		}

	}
	
	/**
	 * Retorna o {@link HtmlEmail} segundo as definições informadas.
	 * 
	 * @return
	 * @author BitWork.
	 * @throws Exception 
	 */
	private HtmlEmail getHtmlEmail() throws Exception {
		HtmlEmail htmlEmail = new HtmlEmail();
		htmlEmail.setCharset(getCharset());
		htmlEmail.setSSLOnConnect(true);
		htmlEmail.setHostName(CondominioConfig.getConfiguracao(CondominioConfig.EMAIL_HOST));
		htmlEmail.setSslSmtpPort(CondominioConfig.getConfiguracao(CondominioConfig.EMAIL_PORTA));
		htmlEmail.setAuthentication(CondominioConfig.getConfiguracao(CondominioConfig.CHAVE_EMAIL_AUTORIZACAO_LOGIN), Util.decrypt(CondominioConfig.getConfiguracao(CondominioConfig.CHAVE_EMAIL_AUTORIZACAO_SENHA)));
		htmlEmail.setFrom(Util.isEmpty(this.enderecoRemetente) ? CondominioConfig.getConfiguracao(CondominioConfig.CHAVE_EMAIL_REMETENTE_PADRAO) : this.enderecoRemetente);

		if (!Util.isEmpty(this.destinatarios)) {
			for (String endereco : this.destinatarios) {
				htmlEmail.addTo(endereco);
			}
		}

		if (!Util.isEmpty(this.destinatariosEmCopia)) {
			for (String endereco : this.destinatariosEmCopia) {
				htmlEmail.addCc(endereco);
			}
		}

		if (!Util.isEmpty(this.destinatariosEmCopiaOculta)) {
			for (String endereco : this.destinatariosEmCopiaOculta) {
				htmlEmail.addBcc(endereco);
			}
		}

		if (!Util.isEmpty(this.assunto)) {
			htmlEmail.setSubject(this.assunto);
		}

		htmlEmail.setHtmlMsg(Util.isEmpty(this.corpoEmail) ? " " : this.corpoEmail);

		return htmlEmail;
	}

	/**
	 * Valida as informações do e-mail.
	 * 
	 * @throws EmailException
	 * @author BitWork.
	 */
	private void validar() throws EmailException {
		if (Util.isEmpty(this.destinatarios) && Util.isEmpty(this.destinatariosEmCopia) && Util.isEmpty(this.destinatariosEmCopiaOculta)) {
			throw new EmailException(ERRO_DESTINATARIO);
		}

		List<String> enderecosInvalidos = new ArrayList<>();

		if (this.charset != null) {
			try {
				Charset.forName(this.charset);
			} catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
				throw new EmailException("Charset invalido (" + getCharset() + ")");
			}
		}

		if (!Util.isEmpty(this.destinatarios)) {
			for (String email : getDestinatarios()) {
				if (!Util.isEmailValido(email)) {
					enderecosInvalidos.add(email);
				}
			}
		}

		if (!Util.isEmpty(this.destinatariosEmCopia)) {
			for (String email : getDestinatariosEmCopia()) {
				if (!Util.isEmailValido(email)) {
					enderecosInvalidos.add(email);
				}
			}
		}

		if (!Util.isEmpty(this.destinatariosEmCopiaOculta)) {
			for (String email : getDestinatariosEmCopiaOculta()) {
				if (!Util.isEmailValido(email)) {
					enderecosInvalidos.add(email);
				}
			}
		}

		if (!Util.isEmpty(enderecosInvalidos)) {
			throw new EmailException("Destinatarios invalidos: " + Util.getListAsString(enderecosInvalidos, ',') + " - adicione endereços de e-mail válidos!");
		}
	}
}