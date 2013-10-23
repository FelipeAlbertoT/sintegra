package br.com.canalvpsasul.sintegra.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.canalvpsasul.vpsabusiness.business.administrativo.EmpresaBusiness;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;


@Controller
@RequestMapping("/api/empresa")
public class EmpresaApiController {

	@Autowired
	private EmpresaBusiness empresaBusiness;
	
	@ResponseBody
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public List<Empresa> get(@RequestParam("query") String query) {
		
		if(StringUtils.isEmpty(query))
			return null;

		List<Empresa> empresas = empresaBusiness.getEmpresaFromCurrentUser();
		
		if(empresas == null)
			return null;
		
		String grupo = null;
        query = query.toLowerCase();
        List<Empresa> matched = new ArrayList<Empresa>();
        for(int i=0; i < empresas.size(); i++) {
        	grupo = empresas.get(i).getNome().toLowerCase();
            if(grupo.contains(query)) {
                matched.add(empresas.get(i));
            }
        }
        
        return matched;
    }
	
}
