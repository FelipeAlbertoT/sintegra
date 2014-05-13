package br.com.canalvpsasul.sintegra.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.canalvpsasul.sintegra.business.SintegraImportadorBusiness;
import br.com.canalvpsasul.sintegra.entities.SintegraTemplate;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/importador")
public class ImportadorController {
	
	@Autowired
	private SintegraImportadorBusiness sintegraImportadorBusiness;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String root() {		
		return "redirect:/manutencao/consulta";
	}
	
	@RequestMapping(value = "/sintegra", method = RequestMethod.GET)
	public String ArquivoSintegra(Model model) throws Exception {		
		
		model = model.addAttribute("sintegraTemplate", new SintegraTemplate());
		
		return "sintegra/arquivo";
	}
	
	@RequestMapping(value = "/sintegra/processar", method = RequestMethod.POST)
	public String sintegraProcessamento(@Valid SintegraTemplate sintegraTemplate, BindingResult result, Model model) throws Exception {
		
		sintegraImportadorBusiness.processar(sintegraTemplate);
		
		return "sintegra/arquivo";
	}
}
