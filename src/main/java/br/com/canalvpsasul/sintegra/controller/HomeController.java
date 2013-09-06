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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.canalvpsasul.sintegra.business.SintegraBusiness;
import br.com.canalvpsasul.sintegra.entities.Sintegra;
import br.com.canalvpsasul.sintegra.entities.SintegraParametros;
import br.com.canalvpsasul.vpsabusiness.business.SyncControlBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.EmpresaBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.UserBusiness;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Portal;
import coffeepot.br.sintegra.tipos.FinalidadeArquivo;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(ConfiguracoesController.class);
	
	@Autowired
	private UserBusiness userBusiness;
	
	@Autowired
	private EmpresaBusiness empresaBusiness;
	
	@Autowired
	private SintegraBusiness sintegraBusiness;

	@Autowired
	private SyncControlBusiness syncControlBusiness;
	
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
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String root() {		
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
				
		Portal portal = userBusiness.getCurrent().getPortal();
		
		model.addAttribute("needSyncEmpresa", syncControlBusiness.needSyncEmpresas(portal));
		model.addAttribute("needSyncEntidade", syncControlBusiness.needSyncEntidades(portal));
				
		SintegraParametros parametros = new SintegraParametros();		
		model.addAttribute("parametros", parametros);
		
		return "home";
	}
	
	@RequestMapping(value = "/home/gerar", method = RequestMethod.POST)
	public String gerarSintegra(@Valid SintegraParametros parametros,
			BindingResult result, Model model) {

		if (result.hasErrors()) {

			for (ObjectError error : result.getAllErrors())
				logger.info("Erro: " + error.toString());

			model.addAttribute("parametros", parametros);
			
			return "home";
		}

		Sintegra sintegra;
		try {
			parametros.setEmpresa(empresaBusiness.get(parametros.getEmpresa().getId()));
			sintegra = sintegraBusiness.gerarSintegra(parametros);
		} catch (Exception e) {

			model.addAttribute("parametros", parametros);
			model.addAttribute("message", e.getMessage());
			
			return "home";
		}
		
		model.addAttribute("sintegra", sintegra);
		return "sintegra";
	}
	
	@RequestMapping(value = "/home/download/{id}", produces = "text/plain")
	@ResponseBody
	public byte[] downloadSintegra(@PathVariable Long id, Model model) throws Exception {
		
		/*
		 * Para que o retorno como byte[] funcione, deve ser configurado o
		 * message-converter:
		 * org.springframework.http.converter.ByteArrayHttpMessageConverter no
		 * arquivo servlet-context.
		 */
		
		Sintegra sintegra = sintegraBusiness.getSintegra(id);
		
		return sintegra.getSintegra().getBytes();
	}
	
}
