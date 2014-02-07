package br.com.canalvpsasul.sintegra.helper;

import java.text.Normalizer;

public class StringUtils extends org.springframework.util.StringUtils {

	public static String removeAccents(String str) {  
	    str = Normalizer.normalize(str, Normalizer.Form.NFD);  
	    str = str.replaceAll("[^\\p{ASCII}]", "");
	    return str;  
	} 
	
}
