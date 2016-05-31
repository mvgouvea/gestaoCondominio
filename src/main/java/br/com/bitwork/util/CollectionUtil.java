/*
 * CollectionUtil.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.hibernate.internal.util.compare.ComparableComparator;

/**
 * Classe utilitária para manipulação de coleções.
 *
 * @author BitWork.
 */
public final class CollectionUtil {

	/**
	 * <p>Ordena a lista de entidades segundo o atributo informado na expressao.</p> 
	 * 
	 * <strong>Notas:</strong> 
	 * 
	 * <p>A expressão deve conter o nome do atributo existente na entidade, caso o atributo esteja em uma 
	 * subclasse o ponto de acesso '.'  pode ser usado. Ex:<br>
	 * Por atributo da Classe: Ordenar a lista de pessoas, pelo atributo id - Expressão: "id".<br>
	 * Por atributo da Subclasse: Ordenar a lista de pessoas, pelo atributo id da cidade, que é uma atributo de pessoa - Expressão: cidade.id.
	 * </p>
	 * <p>O atributo informado na expressão deve implementar a interface {@link Comparator}, caso 
	 * o atributo não implemente a interface a exceção {@link ClassCastException} 
	 * será lançada em tempo de execução.</p>
	 * 
	 * @param expressao
	 * @param lista
	 * @throws ClassCastException
	 */
	public static <T> void ordenarListaEntidadePorAtributo(final String expressao, List<T> lista) throws ClassCastException {
		ordenarListaEntidadePorAtributo(expressao, lista, Boolean.FALSE);
	}
	
	/**
	 * <p>Ordena a lista de entidades segundo o atributo informado na expressao.</p> 
	 * 
	 * <strong>Notas:</strong> 
	 * 
	 * <p>A expressão deve conter o nome do atributo existente na entidade, caso o atributo esteja em uma 
	 * subclasse o ponto de acesso '.'  pode ser usado. Ex:<br>
	 * Por atributo da Classe: Ordenar a lista de pessoas, pelo atributo id - Expressão: "id".<br>
	 * Por atributo da Subclasse: Ordenar a lista de pessoas, pelo atributo id da cidade, que é uma atributo de pessoa - Expressão: cidade.id.
	 * </p>
	 * <p>O atributo informado na expressão deve implementar a interface {@link Comparator}, caso 
	 * o atributo não implemente a interface a exceção {@link ClassCastException} 
	 * será lançada em tempo de execução.</p>
	 * 
	 * @param expressao
	 * @param lista
	 * @param reverse
	 * 
	 * @throws ClassCastException
	 */
	public static <T> void ordenarListaEntidadePorAtributo(final String expressao, List<T> lista, boolean reverse) throws ClassCastException {
		try{
			if(Util.isEmpty(expressao)){
				throw new IllegalArgumentException("Expressão não especificada.");
			}
			
			if(lista == null || lista.size() == 0){
				throw new IllegalArgumentException("Lista não especificada.");
			}
			
			BeanComparator<T> comparator = reverse ? new BeanComparator<T>(expressao, new ReverseComparator(new ComparableComparator())) : new BeanComparator<T>(expressao);
			
			Collections.sort(lista, comparator);
		
		}catch(ClassCastException e){
			throw new ClassCastException("Falha no processo de ordenação. O atributo informado na expressão, deve implementar a interface java.lang.Comparable. ");
		}
	}
}
