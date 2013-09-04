package br.com.canalvpsasul.sintegra.formatter;

import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import coffeepot.br.sintegra.tipos.FinalidadeArquivo;

@Component
public class FinalidadeArquivoFormatter  implements Formatter<FinalidadeArquivo> {
	
    @Override
    public String print(FinalidadeArquivo finalidadeArquivo, Locale arg1) {
        return finalidadeArquivo.getDescricao();
    }
    
    @Override
	public FinalidadeArquivo parse(String id, Locale arg1) {
    	try {
    		
    		for(FinalidadeArquivo finalidadeArquivo : FinalidadeArquivo.values()) {
    			if(finalidadeArquivo.getCodigo().equals(id))
    				return finalidadeArquivo;
    		}
    		
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
	}
}