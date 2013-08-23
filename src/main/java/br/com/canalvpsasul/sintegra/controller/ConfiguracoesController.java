package br.com.canalvpsasul.sintegra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/configuracoes")
public class ConfiguracoesController {

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String configHome() {
		
		return "config/home";
	}
	

	@RequestMapping(value = "/informante", method = RequestMethod.GET)
	public String configInformante() {
		
		return "config/informante";
	}
	
}
