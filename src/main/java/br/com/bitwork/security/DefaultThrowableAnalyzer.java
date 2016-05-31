/*
 * DefaultThrowableAnalyzer.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.security;

import javax.servlet.ServletException;

import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.security.web.util.ThrowableCauseExtractor;

/**
 * TODO
 *
 * @author BitWork.
 */
public class DefaultThrowableAnalyzer extends ThrowableAnalyzer {
	/**
	 * @see org.springframework.security.web.util.ThrowableAnalyzer#initExtractorMap()
	 */
	protected void initExtractorMap() {
		super.initExtractorMap();

		registerExtractor(ServletException.class, new ThrowableCauseExtractor() {
			public Throwable extractCause(Throwable throwable) {
				ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
				return ((ServletException) throwable).getRootCause();
			}
		});
	}
}