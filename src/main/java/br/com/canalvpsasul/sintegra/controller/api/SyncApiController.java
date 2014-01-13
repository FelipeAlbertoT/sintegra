package br.com.canalvpsasul.sintegra.controller.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.canalvpsasul.sintegra.business.ConfiguracaoBusiness;
import br.com.canalvpsasul.sintegra.entities.Configuracao;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.EmpresaBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.PortalBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.SyncControlAdministrativoBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.UserBusiness;
import br.com.canalvpsasul.vpsabusiness.business.fiscal.SyncControlFiscalBusiness;
import br.com.canalvpsasul.vpsabusiness.business.operacional.CategoriaProdutoBusiness;
import br.com.canalvpsasul.vpsabusiness.business.operacional.SaldoMercadoriaBusiness;
import br.com.canalvpsasul.vpsabusiness.business.operacional.SyncControlOperacionalBusiness;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.security.VpsaOAuthService;

@Controller
@RequestMapping("/api/sync")
public class SyncApiController {

	@Autowired
	private UserBusiness userBusiness;
	
	@Autowired
	private PortalBusiness portalBusiness;

	@Autowired
	private EmpresaBusiness empresaBusiness;
	
	@Autowired
	private ConfiguracaoBusiness configuracaoBusiness;
	
	@Autowired
	private SaldoMercadoriaBusiness saldoMercadoriaBusiness; 
	
	@Autowired
	private CategoriaProdutoBusiness categoriaProdutoBusiness; 

	@Autowired
	private SyncControlAdministrativoBusiness syncControlAdministrativoBusiness;

	@Autowired
	private SyncControlOperacionalBusiness syncControlOperacionalBusiness;

	@Autowired
	private SyncControlFiscalBusiness syncControlFiscalBusiness;
	
	@Autowired
	private VpsaOAuthService service;
	
	@ResponseBody
    @RequestMapping(value = "empresas", method = RequestMethod.GET)
    public String syncEmpresas() {
			
		try {
			syncControlAdministrativoBusiness.syncEmpresas(userBusiness.getCurrent().getPortal());
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização dos registros de empresas.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização de empresas realizada com Sucesso!</p>";
	}
	
	@ResponseBody
    @RequestMapping(value = "empresas/portal/{id}", method = RequestMethod.GET)
    public String syncEmpresasPortal(@PathVariable("id") Long id) {
			
		try {
			syncControlAdministrativoBusiness.syncEmpresasAsync(portalBusiness.get(id));
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização dos registros de empresas.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização de empresas realizada com Sucesso!</p>";
	}
	
	@ResponseBody
    @RequestMapping(value = "entidades", method = RequestMethod.GET)
    public String syncEntidades() {
			
		try {
			syncControlAdministrativoBusiness.syncEntidades(userBusiness.getCurrent().getPortal());
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização dos registros de entidades.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização de entidades realizada com Sucesso!</p>";
	}
	
	@ResponseBody
    @RequestMapping(value = "entidades/portal/{id}", method = RequestMethod.GET)
    public String syncEntidadesPortal(@PathVariable("id") Long id) {
			
		try {
			syncControlAdministrativoBusiness.syncEntidadesAsync(portalBusiness.get(id));
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização dos registros de entidades.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização de entidades realizada com Sucesso!</p>";
	}
	
	@ResponseBody
    @RequestMapping(value = "produtos", method = RequestMethod.GET)
    public String syncProdutos() {
		
		try {
			syncControlOperacionalBusiness.syncProdutos(userBusiness.getCurrent().getPortal());
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização dos registros de produtos.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização de produtos realizada com Sucesso!</p>";
	}
	
	@ResponseBody
    @RequestMapping(value = "produtos/portal/{id}", method = RequestMethod.GET)
    public String syncProdutosPortal(@PathVariable("id") Long id) {
			
		try {
			syncControlOperacionalBusiness.syncProdutosAsync(portalBusiness.get(id));
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização dos registros de produtos.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização de produtos realizada com Sucesso!</p>";
	}
	
	@ResponseBody
    @RequestMapping(value = "terceiros", method = RequestMethod.GET)
    public String syncTerceiros() {
			
		try {
			syncControlAdministrativoBusiness.syncTerceiros(userBusiness.getCurrent().getPortal());
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização dos registros de terceiros.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização de terceiros realizada com Sucesso!</p>";
	}
	
	@ResponseBody
    @RequestMapping(value = "terceiros/portal/{id}", method = RequestMethod.GET)
    public String syncTerceirosPortal(@PathVariable("id") Long id) {
			
		try {
			syncControlAdministrativoBusiness.syncTerceirosAsync(portalBusiness.get(id));
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização dos registros de terceiros.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização de terceiros realizada com Sucesso!</p>";
	}

	@ResponseBody
    @RequestMapping(value = "notas/mercadoria", method = RequestMethod.GET)
    public String syncNotasMercadoria() {
			
		try {
			syncControlFiscalBusiness.syncNotasMercadorias(userBusiness.getCurrent().getPortal());
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização dos registros de Notas de Mercadorias.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização de Notas de Mercadorias realizada com Sucesso!</p>";
	}
	
	@ResponseBody
    @RequestMapping(value = "notas/mercadoria/portal/{id}", method = RequestMethod.GET)
    public String syncNotasMercadoriaPortal(@PathVariable("id") Long id) {
			
		try {
			syncControlFiscalBusiness.syncNotasMercadoriasAsync(portalBusiness.get(id));
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização dos registros de Notas de Mercadorias.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização de Notas de Mercadorias realizada com Sucesso!</p>";
	}

	@ResponseBody
    @RequestMapping(value = "notas/consumo", method = RequestMethod.GET)
    public String syncNotasConsumo() {
			
		try {
			syncControlFiscalBusiness.syncNotasConsumo(userBusiness.getCurrent().getPortal());
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização dos registros de Notas de Consumo.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização de Notas de Consumo realizada com Sucesso!</p>";
	}
	
	@ResponseBody
    @RequestMapping(value = "notas/consumo/portal/{id}", method = RequestMethod.GET)
    public String syncNotasConsumoPortal(@PathVariable("id") Long id) {
			
		try {
			syncControlFiscalBusiness.syncNotasConsumoAsync(portalBusiness.get(id));
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização dos registros de Notas de Consumo.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização de Notas de Consumo realizada com Sucesso!</p>";
	}

	@ResponseBody
    @RequestMapping(value = "reducoes", method = RequestMethod.GET)
    public String syncReducoes() {
			
		try {
			syncControlFiscalBusiness.syncReducoesZ(userBusiness.getCurrent().getPortal());
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização dos registros de Reduções Z.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização deReduções Z realizada com Sucesso!</p>";
	}
	
	@ResponseBody
    @RequestMapping(value = "reducoes/portal/{id}", method = RequestMethod.GET)
    public String syncReducoesPortal(@PathVariable("id") Long id) {
			
		try {
			syncControlFiscalBusiness.syncReducoesZAsync(portalBusiness.get(id));
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização dos registros de Reduções Z.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização deReduções Z realizada com Sucesso!</p>";
	}

	@ResponseBody
    @RequestMapping(value = "saldos/mercadorias/empresa/{idempresa}/{data}", method = RequestMethod.GET)
    public String syncSaldoMercadorias(@PathVariable("idempresa") Long idempresa, @PathVariable("data") String data) throws Exception {
		
		Empresa empresa = empresaBusiness.get(idempresa);
		Configuracao configuracao = configuracaoBusiness.getConfiguracaoPorEmpresa(empresa);
		
		Date dataRef = null;		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
        	dataRef = format.parse(data);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
		
		try {
			
			saldoMercadoriaBusiness.syncSaldoMercadoria(userBusiness.getCurrent().getPortal(), configuracao.getEntidades(), dataRef);
			
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização do Saldos de Mercadorias.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização de Saldos de Mercadorias realizada com Sucesso!</p>";
	}
}
