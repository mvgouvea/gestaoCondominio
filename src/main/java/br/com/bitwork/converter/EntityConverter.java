/*
 * EntityConverter.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.bitwork.entities.Entidade;

/**
 * Converter para as entidades do sistema.
 *
 * @author BitWork.
 */
@FacesConverter(value = "entityConverter", forClass = Entidade.class)
public class EntityConverter implements Converter {

	/**
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Object object = null;
		
		if (value != null) {
			object = this.getAttributesFrom(component).get(value);
		}
		return object;
	}

	/**
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String resultado = "";
		if (value != null && !"".equals(value) && !"0".equals(value) && value instanceof Entidade) {
			Entidade entity = (Entidade) value;

			if (entity.getId() != null) {
				this.addAttribute(component, entity);
				resultado = String.valueOf(entity.getId());
			} else {
				this.addAttribute(component, entity);
				resultado = value.toString();
			}
		}
		return resultado;
	}
	
	/**
	 * Adiciona atributos segundo os parâmetros informados.
	 * 
	 * @param component
	 * @param entidade
	 * @author BitWork.
	 */
	private void addAttribute(UIComponent component, Entidade entidade) {
		if (entidade.getId() != null) {
			this.getAttributesFrom(component).put(entidade.getId().toString(), entidade);
		} else {
			this.getAttributesFrom(component).put(entidade.toString(), entidade);
		}
	}

	/** 
	 * Obtém mapa de atributos segundo o componente informado.
	 * 
	 * @param component
	 * @return
	 * @author BitWork.
	 */
	private Map<String, Object> getAttributesFrom(UIComponent component) {
		return component.getAttributes();
	}
}