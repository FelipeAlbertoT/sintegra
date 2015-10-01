package br.com.canalvpsasul.sintegra.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.canalvpsasul.vpsabusiness.business.administrativo.UserBusiness;
import br.com.canalvpsasul.vpsabusiness.utils.git.GitHubIssue;
import br.com.canalvpsasul.vpsabusiness.utils.git.GitHubUtils;

@Controller 
@RequestMapping("/util")
public class UtilController {

	private static final Logger logger = LoggerFactory.getLogger(ConfiguracoesController.class);

	@Autowired
	private UserBusiness userBusiness;
	
	@Value("#{appProperties.repository}")
	private String repository;
	
	@RequestMapping(value = "/issue/cadastro", method = RequestMethod.GET)
	public String cadastrarIssue(Model model) throws Exception {
				
		model.addAttribute("gitHubIssue", new GitHubIssue());
		
		return "shared/formulario_issue";
	}
	
	@RequestMapping(value = "/issue/cadastro/salvar", method = RequestMethod.POST)
	public String salvarIssue(@Valid @ModelAttribute("gitHubIssue") GitHubIssue gitHubIssue,
			BindingResult result, Model model) throws Exception {

		if (result.hasErrors()) {

			for (ObjectError error : result.getAllErrors())
				logger.info("Erro: " + error.toString());

			model.addAttribute("gitHubIssue", gitHubIssue);
			
			return "shared/formulario_issue";
		}

		try{
			
			gitHubIssue.setRepository(repository);
			
			GitHubUtils.cadastrarIssue(userBusiness.getCurrent(), gitHubIssue);
		}
		catch(Exception e){
			model.addAttribute("message", e.getMessage());
			model.addAttribute("gitHubIssue", gitHubIssue);
			
			return "shared/formulario_issue";
		}
		
		model.addAttribute("messageSuccess", "<p>Sua solicitação foi enviada com sucesso.</p><p><strong>A equipe Varejonline agradece sua colaboração.</strong></p>");
		model.addAttribute("gitHubIssue", new GitHubIssue());
		
		return "shared/formulario_issue";
	}
	
}
