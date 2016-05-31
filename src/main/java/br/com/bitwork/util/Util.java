/*
 * Util.java
 * Copyright (c) BitWork.
 *
 * Este software é confidencial e propriedade da BitWork.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da BitWork.
 * Este arquivo contém informações proprietárias.
 * 
 * @author BitWork.
 */
package br.com.bitwork.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.ParseException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.swing.text.MaskFormatter;

import br.com.bitwork.util.exception.UtilException;

/**
 * Classe de utilitários do sistema.
 *
 * @author BitWork.
 */
public class Util {
	
	private static final String REGEX_EMAIL_VALIDO = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern emailPattern = Pattern.compile(REGEX_EMAIL_VALIDO);
    private static final int TAMANHO_PADRAO_CPF  = 11;
    
    private static final String MASCARA_CODIGO_AREA = "(##) ";
    private static final String MASCARA_TELEFONE = MASCARA_CODIGO_AREA + "####-####";
    private static final String MASCARA_TELEFONE_NONO_DIGITO = MASCARA_CODIGO_AREA + "#####-####";
    
    private static byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x34, (byte) 0xE3, (byte) 0x03 };
	private static int iterationCount = 19;
	private static String passPhrase = "key_inter";

	/**
     * Verifica se a {@link String} informada é nula ou vazia.
     * 
     * @param valor
     * @return
     * @author BitWork.
     */
    public static boolean isEmpty(final String valor) {
        return valor == null || "".equals(valor);
    }
      
    /**
     * Verifica se a {@link Collection} informada é nula ou vazia.
     * 
     * @param collection
     * @return
     * @author BitWork.
     */
    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Verifica se o {@link Map} informado é nulo ou vazio.
     * 
     * @param map
     * @return
     * @author BitWork.
     */
    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
	
    /** 
     * Verifica se a {@link String} informada não esta vazia, desconsiderando espaços no momento da validação.
     * 
     * @param valor
     * @return
     * @author BitWork.
     */
    public static boolean isBlank(final String valor) {
    	return isEmpty(valor) || valor.trim().length() == 0;
    }
    
    /**
     * Verifica se o e-mail informado é válido.
     *
     * @param email
     * @return
     * @author BitWork.
     */
    public static boolean isEmailValido(final String email) {
        return emailPattern.matcher(email).matches();
    }
 
    /**
     * Valida um CPF (completa com zeros a esquerda caso o tamanho seja menor que o padrao)
     * @param cpf
     * @return
     */
    public static boolean isCpfValido(String cpf) {
        return isCpfValido(cpf, true);
    }
    
    public static boolean isCpfValido(String cpf, boolean completarZerosEsquerda) {
        if(isEmpty(cpf)) return false;
            
        cpf = removerCaracteresNaoNumericos(cpf);
        
        if(cpf.length() != TAMANHO_PADRAO_CPF) {
            if (completarZerosEsquerda) {
                cpf = completarZerosEsquerda(cpf, TAMANHO_PADRAO_CPF - cpf.length());
            } else {
                return false;
            }
        }
        
        if("00000000000".intern().equals(cpf) || "11111111111".intern().equals(cpf) || 
           "22222222222".intern().equals(cpf) || "33333333333".intern().equals(cpf) || 
           "44444444444".intern().equals(cpf) || "55555555555".intern().equals(cpf) || 
           "66666666666".intern().equals(cpf) || "77777777777".intern().equals(cpf) || 
           "88888888888".intern().equals(cpf) || "99999999999".intern().equals(cpf)) {
            return false;
        }
        
        int d1, d2;
        int digito1, digito2, resto;
        int digitoCPF;
        String nDigResult;

        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;

        //Calculando os dois digitos verificadores
        //Recupera os 9 primeiros digitos e multiplica cada um por 
        for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
            digitoCPF = Integer.valueOf(cpf.substring(nCount - 1, nCount)).intValue();
            
            d1 += (11 - nCount) * digitoCPF; //somatorio inicial para calculo do 1o DV
            d2 += (12 - nCount) * digitoCPF; //idem para o 2o DV
        }

        //Primeiro resto da divisão por 11.  
        resto = (d1 % 11);

        //Se o resultado for < 2 o digito é 0 caso contrário o digito é 11 menos o resto da divisao
        if (resto < 2) digito1 = 0;
        else digito1 = 11 - resto;

        d2 += 2 * digito1; //ao final, o DV1 entra no somatorio do DV2 multiplicado por 2

        //Segundo resto da divisão por 11.  
        resto = (d2 % 11);

        //Se o resultado for < 2 o digito é 0 caso contrário o digito é 11 menos o resto da divisao  
        if (resto < 2) digito2 = 0;
        else digito2 = 11 - resto;

        //Digito verificador do CPF que está sendo validado
        String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());

        //Concatenando o primeiro resto com o segundo
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

        //Verificando se os digitos calculados batem com os do CPF
        return nDigVerific.equals(nDigResult);
    }

    /**
     * Retorna o telefone formatado com o código de área, seguindo o seguinte formato: 
     * Caso o telefone possua o nono dígito: (00) 00000-0000. 
     * Caso o telefone possua oito dígitos: (00) 0000-0000.
     * 
     * @param codigoArea
     * @param numero
     * @return
     * @author BitWork.
     */
    public static String getTelefoneFormatado(final String numero) {
    	StringBuilder builder = new StringBuilder();

    	if (!isEmpty(numero)) {	
    		if(numero.length() < 11) {
    			builder.append(formatarString(numero, MASCARA_TELEFONE));
    		} else {
    			builder.append(formatarString(numero, MASCARA_TELEFONE_NONO_DIGITO));
    		}
    	}
    	return builder.toString();
    }
   
    /**
     * Retorna o valor formatado segundo a máscara informada.
     * 
     * @param valor
     * @param mascara
     * @return
     * @author BitWork.
     */
    private static String formatarString(final String valor, final String mascara) {
        try {
            MaskFormatter formatter = new MaskFormatter(mascara);
            formatter.setValueContainsLiteralCharacters(false);
            return formatter.valueToString(valor);
        } catch(ParseException e) {
            return valor;
        }
    }  
  
    /** 
     * Remove os caracteres não numéricos da {@link String} informada.
     *
     * @param valor
     * @return
     * @author BitWork.
     */
    public static String removerCaracteresNaoNumericos(final String valor) {
    	String resultado = valor;

        if (valor != null) {
        	resultado = valor.replaceAll("[^\\d]", "" );
        }

        return resultado;
    }

    /**
	 * Remove os caracteres especiais da {@link String} informada.
	 * 
	 * @param valor
	 * @return
	 * @author BitWork.
	 */
	public static String removerCaracteresEspeciais(final String valor) {
		return valor.replaceAll("[^a-zZ-Z0-9 ]", "");
	}

	/**
	 * Remove os espações entre palavras da {@link String} informada.
	 *
	 * @param valor
	 * @return
	 * @author BitWork.
	 */
	public static String removerEspacoEntrePalavras(final String valor) {
		return valor.replaceAll("\\s+", "");
	}
    
	/**
	 * Transforma a lista informada em {@link String}.
	 *
	 * @param lista
	 * @return
	 * @author BitWork.
	 */
	public static String getListAsString(final List<? extends Object> lista) {
		return getCollectionAsString(lista, null);
	}

	/**
	 * Transforma a lista em {@link String} segundo o separador informado. 
	 * 
	 * @param lista
	 * @param separador
	 * @return
	 * @author BitWork.
	 */
	public static String getListAsString(final List<? extends Object> lista, Character separador) {
		return getCollectionAsString(lista, separador);
	}

	/**
	 * Transforma a coleção em {@link String} segundo o separador informado.
	 *
	 * @param colecao
	 * @param separador
	 * @return
	 * @author BitWork.
	 */
	public static String getCollectionAsString(final Collection<? extends Object> colecao, Character separador) {
		String resultado = "";
		
		if (!isEmpty(colecao)) {
			if (separador == null) {
				separador = ' ';
			}

			StringBuilder retorno = new StringBuilder();
			int i = 1;
			Iterator<?> iterator = colecao.iterator();
			while (iterator.hasNext()) {
				retorno.append(iterator.next());
				if (i++ < colecao.size()) {
					retorno.append(separador);
				}
			}
			resultado = retorno.toString();
		}
		
		return resultado;
	}
	
	/**
	 * Realiza a encriptação de uma mensagem.
	 * 
	 * @param message
	 * @return
	 * @throws UtilException
	 * @author BitWork.
	 */
	@SuppressWarnings("restriction")
	public static String encrypt(String message) throws UtilException {
		try {
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
			SecretKey key;
			key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
			Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
	
			byte[] utf8 = message.getBytes("UTF8");
			byte[] enc = ecipher.doFinal(utf8);
			return new sun.misc.BASE64Encoder().encode(enc);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException |  InvalidKeyException | InvalidAlgorithmParameterException 
				| UnsupportedEncodingException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			throw new UtilException(e);
		}
	}

	/**
	 * Realiza a decriptação de uma mensagem.
	 * 
	 * @param message
	 * @return
	 * @throws UtilException 
	 * @throws Exception 
	 * @throws ChamatecNegocioException
	 */
	@SuppressWarnings("restriction")
	public static String decrypt(String message) throws UtilException  {
		byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x34, (byte) 0xE3, (byte) 0x03 };
		int iterationCount = 19;
		KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
		SecretKey key;
		try {
			key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
			Cipher dcipher = Cipher.getInstance(key.getAlgorithm());
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

			byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(message);
			byte[] utf8 = dcipher.doFinal(dec);
			return new String(utf8, "UTF8");
		} catch (InvalidKeySpecException | NoSuchAlgorithmException |  InvalidKeyException | InvalidAlgorithmParameterException 
				| NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | IOException e) {
			throw new UtilException(e);
		}
	}
	
	/**
	 * Gera uma hash MD5 a partir de um valor informado.
	 * 
	 * @param value
	 * @return
	 * @author BitWork.
	 * @throws UtilException 
	 * @throws Exception 
	 */
	public static String generateMD5(String value) throws UtilException {
		MessageDigest messageDigest;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(value.getBytes());

			byte byteData[] = messageDigest.digest();

			for (int i = 0; i < byteData.length; i++) {
				stringBuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			return stringBuffer.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new UtilException(e);
		}
	}

	/**
	 * Completa a {@link String} informada com zero a esquerda.
	 * 
	 *
	 * @param valor
	 * @param totalZeros
	 * @return
	 * @author BitWork.
	 */
	public static String completarZerosEsquerda(final String valor, final int totalZeros) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 1; i <= totalZeros; i++) {
            stringBuilder.append("0");
        }

        return stringBuilder.append(valor).toString();
    }
    
}