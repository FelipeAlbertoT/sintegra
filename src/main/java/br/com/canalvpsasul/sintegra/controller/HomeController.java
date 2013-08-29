package br.com.canalvpsasul.sintegra.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.canalvpsasul.vpsabusiness.business.administrativo.PortalBusiness;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private PortalBusiness portalBusiness;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
				
		model.addAttribute("needSync", portalBusiness.needMakeBaseSync());
		
		return "home";
	}
	
}
