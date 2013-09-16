package br.com.canalvpsasul.sintegra.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.canalvpsasul.vpsabusiness.business.SyncControlBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.PortalBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.UserBusiness;

@Controller
@RequestMapping("/api/sync")
public class SyncApiController {

	@Autowired
	private UserBusiness userBusiness;
	
	@Autowired
	private PortalBusiness portalBusiness;

	@Autowired
	private SyncControlBusiness syncControlBusiness;
	
	@ResponseBody
    @RequestMapping(value = "empresas", method = RequestMethod.GET)
    public String syncEmpresas() {
			
		try {
			syncControlBusiness.syncEmpresas(userBusiness.getCurrent().getPortal());
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualiza��o dos registros de empresas.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualiza��o de empresas realizada com Sucesso!</p>";
	}
	
	@ResponseBody
    @RequestMapping(value = "entidades", method = RequestMethod.GET)
    public String syncEntidades() {
			
		try {
			syncControlBusiness.syncEntidades(userBusiness.getCurrent().getPortal());
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualiza��o dos registros de entidades.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualiza��o de entidades realizada com Sucesso!</p>";
	}
	
	@ResponseBody
    @RequestMapping(value = "produtos", method = RequestMethod.GET)
    public String syncProdutos() {
		
		try {
			syncControlBusiness.syncProdutos(userBusiness.getCurrent().getPortal());
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualiza��o dos registros de produtos.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualiza��o de produtos realizada com Sucesso!</p>";
	}
	
	@ResponseBody
    @RequestMapping(value = "terceiros", method = RequestMethod.GET)
    public String syncTerceiros() {
			
		try {
			syncControlBusiness.syncTerceiros(userBusiness.getCurrent().getPortal());
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualiza��o dos registros de terceiros.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualiza��o de terceiros realizada com Sucesso!</p>";
	}

	@ResponseBody
    @RequestMapping(value = "notas/mercadoria", method = RequestMethod.GET)
    public String syncNotasMercadoria() {
			
		try {
			syncControlBusiness.syncNotasMercadorias(userBusiness.getCurrent().getPortal());
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualiza��o dos registros de Notas de Mercadorias.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualiza��o de Notas de Mercadorias realizada com Sucesso!</p>";
	}

	@ResponseBody
    @RequestMapping(value = "notas/consumo", method = RequestMethod.GET)
    public String syncNotasConsumo() {
			
		try {
			syncControlBusiness.syncNotasConsumo(userBusiness.getCurrent().getPortal());
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualiza��o dos registros de Notas de Consumo.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualiza��o de Notas de Consumo realizada com Sucesso!</p>";
	}
}
