/*
 * AjaxTimeoutRedirectFilter.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */

package br.com.bitwork.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filtro de verificação de chamadas ajax, onde é validada a sessão do usuário, ou seja, é validado se 
 * o usuário ainda esta autenticado e caso isso não se confirme envia o usuário para a página de login.
 * 
 *
 * @author BitWork.
 */
public class AjaxTimeoutRedirectFilter extends GenericFilterBean {
	private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
	private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

	private int customSessionExpiredErrorCode = 901;

	/**
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (IOException ex) {
			throw ex;
		} catch (Exception ex) {
			Throwable[] causeChain = throwableAnalyzer.determineCauseChain(ex);
			RuntimeException ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);

			if (ase == null) {
				ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
			}

			if (ase != null) {
				if (ase instanceof AuthenticationException) {
					throw ase;
				} else if (ase instanceof AccessDeniedException) {
					if (authenticationTrustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
						String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");

						if ("XMLHttpRequest".equals(ajaxHeader)) {
							HttpServletResponse resp = (HttpServletResponse) response;
							resp.sendError(this.customSessionExpiredErrorCode);
						} else {
							try {
								if (isAjaxRequest((HttpServletRequest) request)) {
									returnPartialResponseRedirect((HttpServletRequest) request, (HttpServletResponse) response);
								}
							} catch (IOException e) {
								throw e;
							}
							throw ase;
						}
					} else {
						throw ase;
					}
				}
			}

		}
	}

	/**
	 * TODO
	 * 
	 * @param customSessionExpiredErrorCode
	 * @author BitWork.
	 */
	public void setCustomSessionExpiredErrorCode(int customSessionExpiredErrorCode) {
		this.customSessionExpiredErrorCode = customSessionExpiredErrorCode;
	}

	/**
	 * Escreve a instrução para redirecionamento para as chamadas ajax.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author BitWork.
	 */
	private void returnPartialResponseRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException  {
		StringBuffer responseStr = new StringBuffer();
		String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/login.jsf";
		responseStr.append("<partial-response><redirect url=\"");
		responseStr.append(url + "\"/></partial-response>");;

        response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		PrintWriter out = response.getWriter();
	    out.write(responseStr.toString());
	    out.flush();
	    out.close();
	}

	/**
	 * Verifica se a chamada é ajax.
	 * 
	 * @param request
	 * @return
	 * @author BitWork.
	 */
	private Boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("Faces-Request");
		String param = request.getParameter("javax.faces.partial.ajax");
		return ("partial/ajax".equals(header) || "true".equals(param)) ? true : false;
	}
}