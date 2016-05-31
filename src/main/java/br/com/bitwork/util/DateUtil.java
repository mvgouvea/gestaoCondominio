/*
 * DateUtil.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import br.com.bitwork.constantes.Constantes;

/**
 * Classe de utilitários de {@link Date}.
 *
 * @author BitWork.
 */
public class DateUtil {
	
	/**
     * Retorna a data formatada no padrão <b>dd/MM/yyyy</b>.
     * 
     * @param data
     * @return
     * @author BitWork.
     */
    public static String getDataFormatada(final Date data) {
    	return getDataFormatada(data, Constantes.FORMATO_PADRAO_DATA);
    }

    /**
     * Retorna a data formatada segundo o padrão de formato informado.
     * 
     * @param data
     * @param formato
     * @return
     * @author BitWork.
     */
    public static String getDataFormatada(final Date data, final String formato) {
    	if (data == null || Util.isEmpty(formato)) {
    		throw new IllegalArgumentException("Especifique um objeto Date valido e um Formato.");
    	}
    	
    	return new SimpleDateFormat(formato).format(data);
    }

    /**
     * Retorna a instância de {@link Date} com a data atual  mantendo a Hora, Minutos, Segundos e os Milésimos de segundos em ZERO: Wed Nov 26 00:00:00 BRST 2014.
     *
     * @return
     * @author BitWork.
     */
    public static Date getDataAtualZeroHora() {
    	return getDataZeroHora(new Date());
    }
    
    /**
     * Retorna a instância de {@link Date} mantendo a Hora, Minutos, Segundos e os Milésimos de segundos em ZERO: Wed Nov 26 00:00:00 BRST 2014.
     * 
     * @param data
     * @return
     * @author BitWork.
     */
    public static Date getDataZeroHora(final Date data) {
    	if 	(data == null) {
			throw new IllegalArgumentException("A data não foi espedificada.");
		}

    	/** Caso a instância seja de "java.sql.Date" uma nova instância de "java.util.Date" será criada, evitando falhas na recuperação dos dias. */
    	Date presente = data instanceof java.sql.Date ? new  Date(data.getTime()) : data;
    	
    	LocalDate localDate = presente.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}