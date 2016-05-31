/*
 * DAOException.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.persistence.exception;

/**
 * Classe padrão de exceções da camada de persistência.
 *
 * @author BitWork.
 */
public class DAOException extends Exception {

	private static final long serialVersionUID = -848947020664440018L;

    /**
     * Construtor da classe.
     *
     * @param message
     * @param e
     * @author BitWork.
     */
    public DAOException(String message, Throwable e) {
        super(message, e);
    }
    
    /**
     * Construtor da classe.
     *
     * @param message
     * @author BitWork.
     */
    public DAOException(String message) {
        super(message);
    }
}