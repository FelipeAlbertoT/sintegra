package br.com.canalvpsasul.sintegra.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
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
import br.com.canalvpsasul.vpsabusiness.business.administrativo.EmpresaBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.SyncControlAdministrativoBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.UserBusiness;
import br.com.canalvpsasul.vpsabusiness.business.fiscal.SyncControlFiscalBusiness;
import br.com.canalvpsasul.vpsabusiness.business.operacional.SyncControlOperacionalBusiness;
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
	private SyncControlAdministrativoBusiness syncControlAdministrativoBusiness;

	@Autowired
	private SyncControlOperacionalBusiness syncControlOperacionalBusiness;

	@Autowired
	private SyncControlFiscalBusiness syncControlFiscalBusiness;
	
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
		finalidades.add(FinalidadeArquivo.RETIFICACAO_ADITIVA);
		
		return finalidades;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String root() {		
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
				
		Portal portal = userBusiness.getCurrent().getPortal();
		
		addAttrsToModel(portal, model);
				
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

			Portal portal = userBusiness.getCurrent().getPortal();
			
			model.addAttribute("parametros", parametros);
			model.addAttribute("message", e.getMessage());
			addAttrsToModel(portal, model);
			
			return "home";
		}
		
		model.addAttribute("sintegra", sintegra);
		return "sintegra";
	}
	
	private Model addAttrsToModel(Portal portal, Model model) {
		
		model.addAttribute("needSyncEmpresa", syncControlAdministrativoBusiness.needSyncEmpresas(portal));
		model.addAttribute("needSyncEntidade", syncControlAdministrativoBusiness.needSyncEntidades(portal));
		model.addAttribute("needSyncProduto", syncControlOperacionalBusiness.needSyncProdutos(portal));
		model.addAttribute("needSyncNotasMercadorias", syncControlFiscalBusiness.needSyncNotasMercadorias(portal));
		model.addAttribute("needSyncNotasConsumo", syncControlFiscalBusiness.needSyncNotasConsumo(portal));
		model.addAttribute("needSyncTerceiros", syncControlAdministrativoBusiness.needSyncTerceiros(portal));
		model.addAttribute("needSyncReducoesZ", syncControlFiscalBusiness.needSyncReducoesZ(portal));
		
		return model;
	}
	
	@RequestMapping(value = "/home/download/{id}", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public byte[] downloadSintegra(@PathVariable Long id, Model model, HttpServletResponse response) throws Exception {
		
		/*
		 * Para que o retorno como byte[] funcione, deve ser configurado o
		 * message-converter:
		 * org.springframework.http.converter.ByteArrayHttpMessageConverter no
		 * arquivo servlet-context.
		 */
		
		Sintegra sintegra = sintegraBusiness.getSintegra(id);
		
		SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");	
		response.setHeader("Content-Disposition", "attachment; filename=sintegra_"+dt.format(new Date())+".txt"); 
		
		return sintegra.getSintegra().getBytes();
	}
	
}
