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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.canalvpsasul.vpsabusiness.business.administrativo.EntidadeBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.UserBusiness;
import br.com.canalvpsasul.vpsabusiness.business.fiscal.ReducaoZBusiness;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Entidade;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ReducaoZ;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.TotalizadorReducao;

@Controller
@RequestMapping("/reducao")
public class ReducaoController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReducaoController.class);
	
	@Autowired
	private UserBusiness userBusiness;
	
	@Autowired
	private ReducaoZBusiness reducaoZBusiness;
	
	@Autowired
	private EntidadeBusiness entidadeBusiness;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@ModelAttribute("entidades")
	public List<Entidade> populateFinalidades() {
		
		List<Empresa> empresas = userBusiness.getCurrent().getPortal().getEmpresas();
		
		ArrayList<Entidade> entidades = new ArrayList<Entidade>();
		
		for(Empresa empresa : empresas) 
			entidades.addAll(empresa.getEntidades());
		
		return entidades;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String root() {		
		return "redirect:/reducao/nova";
	}
	
	private ReducaoZ criarReducaoEmpty() {
		
		ReducaoZ reducaoZ = new ReducaoZ();
		
		reducaoZ.setPortal(userBusiness.getCurrent().getPortal());
		
		TotalizadorReducao totalizadorReducaoF1 = new TotalizadorReducao();
		
		totalizadorReducaoF1.setCodigo("F1");
		totalizadorReducaoF1.setValorAcumulado(0F);
		reducaoZ.getTotalizadores().add(totalizadorReducaoF1);
		
		TotalizadorReducao totalizadorReducaoI1 = new TotalizadorReducao();
		
		totalizadorReducaoI1.setCodigo("I1");
		totalizadorReducaoI1.setValorAcumulado(0F);
		reducaoZ.getTotalizadores().add(totalizadorReducaoI1);
		
		TotalizadorReducao totalizadorReducaoN1 = new TotalizadorReducao();
		
		totalizadorReducaoN1.setCodigo("N1");
		totalizadorReducaoN1.setValorAcumulado(0F);
		reducaoZ.getTotalizadores().add(totalizadorReducaoN1);
		
		TotalizadorReducao totalizadorReducaoFS1 = new TotalizadorReducao();
		
		totalizadorReducaoFS1.setCodigo("FS1");
		totalizadorReducaoFS1.setValorAcumulado(0F);
		reducaoZ.getTotalizadores().add(totalizadorReducaoFS1);
		
		TotalizadorReducao totalizadorReducaoIS1 = new TotalizadorReducao();
		
		totalizadorReducaoIS1.setCodigo("IS1");
		totalizadorReducaoIS1.setValorAcumulado(0F);
		reducaoZ.getTotalizadores().add(totalizadorReducaoIS1);
		
		TotalizadorReducao totalizadorReducaoNS1 = new TotalizadorReducao();
		
		totalizadorReducaoNS1.setCodigo("NS1");
		totalizadorReducaoNS1.setValorAcumulado(0F);
		reducaoZ.getTotalizadores().add(totalizadorReducaoNS1);
		
		return reducaoZ;
	}
	
	@RequestMapping(value = "/nova", method = RequestMethod.GET)
	public String novaReducao(Locale locale, Model model) {
		
		model.addAttribute("reducaoZ", criarReducaoEmpty());
		
		return "reducao/nova";
	}

	@Transactional
	@RequestMapping(value = "/nova/salvar", method = RequestMethod.POST)
	public String salvarReducao(@Valid ReducaoZ reducaoZ, BindingResult result, Model model) throws Exception {

		if (result.hasErrors()) {

			for (ObjectError error : result.getAllErrors())
				logger.info("Erro: " + error.toString());
			
			return "reducao/nova";
		}
		
		for(TotalizadorReducao totalizadorReducao : reducaoZ.getTotalizadores()) {
			
			totalizadorReducao.setReducaoZ(reducaoZ);
			
			totalizadorReducao.setCodigo(totalizadorReducao.getCodigo().replace("%", ""));
			totalizadorReducao.setCodigo(totalizadorReducao.getCodigo().replace(",", ""));
		}
		
		reducaoZ.setAtivo(true);
		reducaoZ.setDataAlteracao(new Date());
		reducaoZ.setDataCriacao(new Date());
		reducaoZ.setEntidade(entidadeBusiness.get(reducaoZ.getEntidade().getId()));
		reducaoZ.setDataReducaoZ(reducaoZ.getDataMovimento());
		
		reducaoZBusiness.salvar(reducaoZ);

		model.addAttribute("reducaoZ", criarReducaoEmpty());
		
		model.addAttribute("message", "Redução cadastrada com sucesso.");
		
		return "reducao/nova";
	}
	
}
