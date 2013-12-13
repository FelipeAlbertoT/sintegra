package br.com.canalvpsasul.sintegra.controller;

import java.util.ArrayList;
import java.util.List;

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

import br.com.canalvpsasul.sintegra.business.ConfiguracaoBusiness;
import br.com.canalvpsasul.sintegra.business.InformanteBusiness;
import br.com.canalvpsasul.sintegra.entities.Configuracao;
import br.com.canalvpsasul.sintegra.entities.Informante;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.PortalBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.SyncControlAdministrativoBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.UserBusiness;
import br.com.canalvpsasul.vpsabusiness.business.fiscal.SyncControlFiscalBusiness;
import br.com.canalvpsasul.vpsabusiness.business.geral.SyncControlBusiness;
import br.com.canalvpsasul.vpsabusiness.business.operacional.SyncControlOperacionalBusiness;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Portal;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.StatusPortal;
import br.com.canalvpsasul.vpsabusiness.entities.geral.SyncControl;
import br.com.canalvpsasul.vpsabusiness.entities.geral.SyncControlType;

@Controller
@RequestMapping("/configuracoes")
public class ConfiguracoesController {

	private static final Logger logger = LoggerFactory
			.getLogger(ConfiguracoesController.class);

	@Autowired
	private InformanteBusiness informanteBusiness;

	@Autowired
	private ConfiguracaoBusiness configuracaoBusiness;

	@Autowired
	private UserBusiness userBusiness;

	@Autowired
	private PortalBusiness portalBusiness;

	@Autowired
	private SyncControlAdministrativoBusiness syncControlAdministrativoBusiness;

	@Autowired
	private SyncControlOperacionalBusiness syncControlOperacionalBusiness;

	@Autowired
	private SyncControlFiscalBusiness syncControlFiscalBusiness;

	@Autowired
	private SyncControlBusiness syncControlBusiness;

	@ModelAttribute("statusPortais")
	public List<StatusPortal> populateStatusPortais() {
		
		ArrayList<StatusPortal> statusPortals = new ArrayList<StatusPortal>();
		
		statusPortals.add(StatusPortal.BLOQUEADO);
		statusPortals.add(StatusPortal.LIBERADO);
		
		return statusPortals;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String configHome() {
		
		return "redirect:/configuracoes/informantes";
	}
	
	@RequestMapping(value = "/informantes", method = RequestMethod.GET)
	public String configInformante(Model model) {
		
		model.addAttribute("informantes", informanteBusiness.getInformantesPorPortal(userBusiness.getCurrent().getPortal()));
		
		return "config/informantes";
	}
	
	@RequestMapping(value = "/configuracao", method = RequestMethod.GET)
	public String configuracoes(Model model) {
		
		model.addAttribute("configuracoes", configuracaoBusiness.getConfiguracaoPorPortal(userBusiness.getCurrent().getPortal()));
		
		return "config/configuracao";
	}
	
	@RequestMapping(value = "/bases", method = RequestMethod.GET)
	public String configBases(Model model) {

		model.addAttribute("portais", portalBusiness.getAll());

		return "config/bases";
	}
	
	@RequestMapping(value = "/bases/cadastro/{id}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String cadastroBase(@PathVariable Long id, Model model)
			throws Exception {

		model = addAttrsToModel(portalBusiness.get(id), model);
		
		return "config/formulario_portal";
	}
	
	private Model addAttrsToModel(Portal portal, Model model) {
	
		model.addAttribute("portal", portal);
		
		SyncControl syncControl = syncControlBusiness.getLastByType(portal, SyncControlType.EMPRESA);
		model.addAttribute("needSyncEmpresa", syncControlAdministrativoBusiness.needSyncEmpresas(portal));
		if(syncControl != null)
			model.addAttribute("lastSyncEmpresa", syncControl.getSyncDate());
		else
			model.addAttribute("lastSyncEmpresa", null);
		
		syncControl = syncControlBusiness.getLastByType(portal, SyncControlType.ENTIDADE);
		model.addAttribute("needSyncEntidade", syncControlAdministrativoBusiness.needSyncEntidades(portal));
		if(syncControl != null)
			model.addAttribute("lastSyncEntidade", syncControl.getSyncDate());
		else
			model.addAttribute("lastSyncEntidade", null);
		
		syncControl = syncControlBusiness.getLastByType(portal, SyncControlType.PRODUTO);
		model.addAttribute("needSyncProduto", syncControlOperacionalBusiness.needSyncProdutos(portal));
		if(syncControl != null)
			model.addAttribute("lastSyncProduto", syncControl.getSyncDate());
		else
			model.addAttribute("lastSyncProduto", null);
		
		syncControl = syncControlBusiness.getLastByType(portal, SyncControlType.NOTAMERCADORIA);
		model.addAttribute("needSyncNotasMercadorias", syncControlFiscalBusiness.needSyncNotasMercadorias(portal));
		if(syncControl != null)
			model.addAttribute("lastSyncNotasMercadorias", syncControl.getSyncDate());
		else
			model.addAttribute("lastSyncNotasMercadorias", null);
		
		syncControl = syncControlBusiness.getLastByType(portal, SyncControlType.NOTASCONSUMO);
		model.addAttribute("needSyncNotasConsumo", syncControlFiscalBusiness.needSyncNotasConsumo(portal));
		if(syncControl != null)
			model.addAttribute("lastSyncNotasConsumo", syncControl.getSyncDate());
		else
			model.addAttribute("lastSyncNotasConsumo", null);
		
		syncControl = syncControlBusiness.getLastByType(portal, SyncControlType.TERCEIRO);
		model.addAttribute("needSyncTerceiros", syncControlAdministrativoBusiness.needSyncTerceiros(portal));
		if(syncControl != null)
			model.addAttribute("lastSyncTerceiros", syncControl.getSyncDate());
		else
			model.addAttribute("lastSyncTerceiros", null);
		
		syncControl = syncControlBusiness.getLastByType(portal, SyncControlType.REDUCAOZ);
		model.addAttribute("needSyncReducoesZ", syncControlFiscalBusiness.needSyncReducoesZ(portal));
		if(syncControl != null)
			model.addAttribute("lastSyncReducoesZ", syncControl.getSyncDate());
		else
			model.addAttribute("lastSyncReducoesZ", null);
		
		return model;
	}
	
	@RequestMapping(value = "/bases/cadastro/salvar", method = RequestMethod.POST)
	public String salvarBase(@Valid @ModelAttribute("portal") Portal portal,
			BindingResult result, Model model) {

		if (result.hasErrors()) {

			for (ObjectError error : result.getAllErrors())
				logger.info("Erro: " + error.toString());
			
			model = addAttrsToModel(portal, model);
			
			return "config/formulario_portal";
		}

		try {
			
			if(portal.getStatusPortal() == StatusPortal.LIBERADO)			
				portalBusiness.liberarPortal(portal);
			else
				portalBusiness.bloquearPortal(portal);
			
		} catch (Exception e) {

			model = addAttrsToModel(portal, model);
			
			model.addAttribute("message", e.getMessage());

			return "config/formulario_portal";
		}

		return "redirect:/configuracoes/bases";
	}
	
	@RequestMapping(value = "/informantes/cadastro", method = RequestMethod.GET)
	public String configNewInformante(Model model) {

		model.addAttribute("informante", informanteBusiness.create());
		
		return "config/formulario_informante";
	}
	
	@RequestMapping(value = "/configuracao/cadastro", method = RequestMethod.GET)
	public String configNew(Model model) {

		model.addAttribute("configuracao", configuracaoBusiness.create());
		
		return "config/formulario_configuracao";
	}

	@RequestMapping(value = "/informantes/cadastro/{id}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String cadastroInformante(@PathVariable Long id, Model model)
			throws Exception {

		model.addAttribute("informante", informanteBusiness.get(id));
		
		return "config/formulario_informante";
	}

	@RequestMapping(value = "/configuracao/cadastro/{id}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String cadastroConfiguracao(@PathVariable Long id, Model model)
			throws Exception {

		model.addAttribute("configuracao", configuracaoBusiness.get(id));
		
		return "config/formulario_configuracao";
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
	
	@RequestMapping(value = "/configuracao/cadastro/salvar", method = RequestMethod.POST)
	public String salvarConfiguracao(@Valid @ModelAttribute("configuracao") Configuracao configuracao,
			BindingResult result, Model model) {

		if (result.hasErrors()) {

			for (ObjectError error : result.getAllErrors())
				logger.info("Erro: " + error.toString());

			model.addAttribute("configuracao", configuracao);

			return "config/formulario_configuracao";
		}

		try {
			configuracaoBusiness.salvar(configuracao);
		} catch (Exception e) {

			model.addAttribute("configuracao", configuracao);
			model.addAttribute("message", e.getMessage());

			return "config/formulario_configuracao";
		}

		return "redirect:/configuracoes/configuracao";
	}
	
}
