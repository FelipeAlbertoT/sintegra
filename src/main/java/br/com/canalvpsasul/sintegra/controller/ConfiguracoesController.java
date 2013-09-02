package br.com.canalvpsasul.sintegra.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.canalvpsasul.sintegra.business.InformanteBusiness;
import br.com.canalvpsasul.sintegra.entities.Informante;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.UserBusiness;

@Controller
@RequestMapping("/configuracoes")
public class ConfiguracoesController {

	private static final Logger logger = LoggerFactory
			.getLogger(ConfiguracoesController.class);

	@Autowired
	private InformanteBusiness informanteBusiness;

	@Autowired
	private UserBusiness userBusiness;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String configHome() {
		
		return "redirect:/configuracoes/informantes";
	}
	

	@RequestMapping(value = "/informantes", method = RequestMethod.GET)
	public String configInformante(Model model) {
		
		model.addAttribute("informantes", informanteBusiness.getInformantesPorPortal(userBusiness.getCurrent().getPortal()));
		
		return "config/informantes";
	}
	
	@RequestMapping(value = "/informantes/cadastro", method = RequestMethod.GET)
	public String configNewInformante(Model model) {

		model.addAttribute("informante", informanteBusiness.create());
		
		return "config/formulario_informante";
	}

	@RequestMapping(value = "/informantes/cadastro/{id}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String cadastroInformante(@PathVariable Long id, Model model)
			throws Exception {

		model.addAttribute("informante", informanteBusiness.get(id));
		
		return "config/formulario_informante";
	}
	
	@RequestMapping(value = "/informantes/cadastro/salvar", method = RequestMethod.POST)
	public String salvarInformante(@Valid @ModelAttribute("informante") Informante informante,
			BindingResult result, Model model) {

		if (result.hasErrors()) {

			for (ObjectError error : result.getAllErrors())
				logger.info("Erro: " + error.toString());

			model.addAttribute("informante", informante);

			return "config/formulario_informante";
		}

		try {
			informanteBusiness.salvar(informante);
		} catch (Exception e) {

			model.addAttribute("informante", informante);
			model.addAttribute("message", e.getMessage());

			return "config/formulario_informante";
		}

		return "redirect:/configuracoes/informantes";
	}
	
}
