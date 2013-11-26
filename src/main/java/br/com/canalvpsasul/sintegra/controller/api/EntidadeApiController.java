package br.com.canalvpsasul.sintegra.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.canalvpsasul.vpsabusiness.business.administrativo.EmpresaBusiness;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Entidade;

@Controller
@RequestMapping("/api/entidade")
public class EntidadeApiController {

	@Autowired
	private EmpresaBusiness empresaBusiness;
	
	@ResponseBody
    @RequestMapping(value = "empresa/{idempresa}/{filter}", method = RequestMethod.GET)
    public List<Entidade> get(@PathVariable("idempresa") Long idempresa, @PathVariable("filter") String filter) throws Exception {
		
		if(StringUtils.isEmpty(filter))
			return null;

		Empresa empresa = empresaBusiness.get(idempresa);
		
		if(empresa == null)
			return null;
		
		String grupo = null;
		filter = filter.toLowerCase();
        List<Entidade> matched = new ArrayList<Entidade>();
        for(Entidade entidade : empresa.getEntidades()) {
        	grupo = entidade.getNome().toLowerCase();
            if(grupo.contains(filter)) {
                matched.add(entidade);
            }
        }
        
        return matched;
    }
	
}
