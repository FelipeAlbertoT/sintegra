package br.com.canalvpsasul.sintegra.formatter;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import br.com.canalvpsasul.vpsabusiness.business.administrativo.EmpresaBusiness;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;

@Component
public class EmpresaFormatter implements Formatter<Empresa> {
	
    @Autowired
    private EmpresaBusiness empresaBusiness;

    @Override
    public String print(Empresa empresa, Locale arg1) {
    	
    	if(empresa.getId() == null) return "";
    	
        return empresa.getId().toString();
    }
    
    @Override
	public Empresa parse(String id, Locale arg1) {
    	try {
			return empresaBusiness.get(Long.parseLong(id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
	}
}