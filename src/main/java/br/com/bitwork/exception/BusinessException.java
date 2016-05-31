/*
 * BusinessException.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.exception;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import br.com.bitwork.util.Util;

/**
 * Classe base para exceções de negócio fornecendo métodos úteis para lidar com arquivo <b>properties</b> e com mensagens padrões de erro.
 *
 * @author BitWork.
 */
public abstract class BusinessException extends Exception {
	private static final long serialVersionUID = -6159531221687503820L;

	private String chave;
	private boolean concatenaParametros;
	private List<?> parametros;
	
	/**
	 * Retrona o arquivo *.properties, onde estão descritas as mensagens de validação e negócio.
	 * 
	 * @return
	 * @author BitWork.
	 */
	protected abstract String getMessageProperties();
	
	/**
	 * Construtor da classe.
	 *
	 * @param message
	 * @param cause
	 * @author BitWork.
	 */
	public BusinessException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Construtor da classe.
	 *
	 * @param message
	 * @author BitWork.
	 */
	public BusinessException(final String message) {
		super(message);
	}

	/**
	 * Construtor da classe.
	 *
	 * @param cause
	 * @author BitWork.
	 */
	public BusinessException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Construtor da classe.
	 *
	 * @param code
	 * @author BitWork.
	 */
	public <T> BusinessException(T code) {
		this.chave = code.toString();
	}

	/**
	 * Construtor da classe.
	 *
	 * @param chave
	 * @param concatenaParametros
	 * @param parametros
	 * @author BitWork.
	 */
	public <E, T> BusinessException(T chave, boolean concatenaParametros, List<E> parametros) {
		this.chave = chave.toString();
		this.concatenaParametros = concatenaParametros;
		this.parametros = parametros;
	}
	
	/**
	 * Construtor da classe.
	 * 
	 * @param chave
	 * @param parametros
	 * @author BitWork.
	 */
	public <E, T> BusinessException(T chave, List<E> parametros) {
		this(chave, Boolean.TRUE, parametros);
	}

	/**
	 * Construtor da classe.
	 *
	 * @param chave
	 * @param concatenaParametros
	 * @param parametros
	 * @author BitWork.
	 */
	public <T> BusinessException(T chave, boolean concatenaParametros, Object... parametros) {
		this.chave = chave.toString();
		this.concatenaParametros = concatenaParametros;
		
		if (parametros != null) {
			this.parametros = Arrays.asList(parametros);
		}
	}
		
	/**
	 * Construtor da classe.
	 *
	 * @param chave
	 * @param parametros
	 * @author BitWork.
	 */
	public <T> BusinessException(T chave, Object... parametros) {
		this(chave, Boolean.TRUE, parametros);
	}
	
	/**
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		String mensagem = super.getMessage();

		if (mensagem == null) {
			mensagem = getMensagemChave();
			
			if(parametros != null) {
				if(concatenaParametros) {
					mensagem = getMensagemComParametrosConcactenados(mensagem);
				} else {
					mensagem = getMensagemComParametros(mensagem);
				}
			}
		}

		return mensagem;
	}
	
	/**
	 * Inclui os parâmetros Concatenados na mensagem informada.
	 * 
	 * @param mensagem
	 * @return
	 * @author BitWork.
	 */
	private String getMensagemComParametrosConcactenados(final String mensagem) {
		String mensagemFormatada = mensagem;
		String parametrosConcatenados = getParametrosConcatenados();

		if (parametrosConcatenados.trim().length() > 0) {
			mensagemFormatada = getMensagemFormatada(mensagem, parametrosConcatenados);
		}

		return mensagemFormatada;
	}
	
	/**
	 * Inclui os parâmetros a mensagem informada.
	 * 
	 * @param mensagem
	 * @return
	 * @author BitWork.
	 */
	private String getMensagemComParametros(final String mensagem) {
		return getMensagemFormatada(mensagem, parametros.toArray());
	}
	
	/**
	 * Retorna a mensagem formatada com os parâmetros informados.
	 * 
	 * @param mensagem
	 * @param parametros
	 * @return
	 * @author BitWork.
	 */
	private String getMensagemFormatada(final String mensagem, final Object... parametros) {
		return MessageFormat.format(mensagem, parametros);
	}
		
	/**
	 * Retorna a mensagem obtida segundo o código da chave.
	 * 
	 * @return
	 * @author BitWork.
	 */
	private String getMensagemChave() {
		return getResourceBundle().getString(chave);
	}
	
	/**
	 * Retorna a instância de {@link ResourceBundle}.
	 * 
	 * @return
	 * @author BitWork.
	 */
	private ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle(getMessageProperties());
	}
	
	/**
	 * Retorna a {@link List} de parâmetros separados por virgula concatenados em uma {@link String}.
	 * 
	 * @return
	 * @author BitWork.
	 */
	private String getParametrosConcatenados() {
		StringBuilder build = new StringBuilder();
		Iterator<?> iterator =  parametros.iterator();
		
		while (iterator.hasNext()) {
			Object valor = iterator.next().toString();
			
			if (getResourceBundle().containsKey(valor.toString())) {
				build.append(getResourceBundle().getString(valor.toString()));
			} else {
				build.append(valor.toString());
			}
			
			if(iterator.hasNext()) {
				build.append(", ");
			}
		}

		return build.toString();
	}
	
	/**
	 * Verifica se exceção de negócio se refere a validação de campos obrigatórios.
	 * 
	 * @return
	 * @author BitWork.
	 */
	public boolean isValidacaoCamposObrigatorios() {
		return !Util.isEmpty(chave) || parametros != null && parametros.size() > 0;
	}

	/**
	 * @return the chave
	 */
	public String getChave() {
		return chave;
	}

	/**
	 * @return the parametros
	 */
	public List<?> getParametros() {
		return parametros;
	}
}