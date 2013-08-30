package br.com.canalvpsasul.sintegra.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.canalvpsasul.sintegra.business.SintegraBusiness;
import br.com.canalvpsasul.sintegra.entities.SintegraParametros;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.PortalBusiness;
import coffeepot.br.sintegra.tipos.FinalidadeArquivo;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(ConfiguracoesController.class);
	
	@Autowired
	private PortalBusiness portalBusiness;
	
	@Autowired
	private SintegraBusiness sintegraBusiness;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}
	
	@ModelAttribute("finalidades")
	public List<FinalidadeArquivo> populateFinalidades() {
		
		ArrayList<FinalidadeArquivo> finalidades = new ArrayList<FinalidadeArquivo>();
		
		finalidades.add(FinalidadeArquivo.NORMAL);
		finalidades.add(FinalidadeArquivo.RETIFICACAO_TOTAL);
		
		return finalidades;
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
				
		model.addAttribute("needSync", portalBusiness.needMakeBaseSync());
		
		SintegraParametros parametros = new SintegraParametros();
		
		model.addAttribute("parametros", parametros);
		
		return "home";
	}
	
	@RequestMapping(value = "/gerar", method = RequestMethod.POST)
	public String gerarSintegra(@Valid SintegraParametros parametros,
			BindingResult result, Model model) {

		if (result.hasErrors()) {

			for (ObjectError error : result.getAllErrors())
				logger.info("Erro: " + error.toString());

			model.addAttribute("parametros", parametros);
			
			return "home";
		}

		try {
			sintegraBusiness.gerarSintegra(parametros);
		} catch (Exception e) {

			model.addAttribute("parametros", parametros);
			model.addAttribute("message", e.getMessage());
			
			return "home";
		}
		
		// TODO Fazer o redirect para a view correta
		return "home";
	}
}
