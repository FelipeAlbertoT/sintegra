package br.com.canalvpsasul.sintegra.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.canalvpsasul.vpsabusiness.business.administrativo.EmpresaBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.EntidadeBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.TerceiroBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.UserBusiness;
import br.com.canalvpsasul.vpsabusiness.business.fiscal.NotaMercadoriaBusiness;
import br.com.canalvpsasul.vpsabusiness.business.operacional.ProdutoBusiness;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.User;

@Controller
@RequestMapping("/api/sync")
public class SyncApiController {

	@Autowired
	private UserBusiness userBusiness;
	
	@Autowired
	private EmpresaBusiness empresaBusiness;
	
	@Autowired
	private EntidadeBusiness entidadeBusiness;
	
	@Autowired
	private ProdutoBusiness produtoBusiness;
	
	@Autowired
	private TerceiroBusiness terceiroBusiness;
	
	@Autowired
	private NotaMercadoriaBusiness notasMercadoriaBusiness;
	
	
	@ResponseBody
    @RequestMapping(value = "empresas", method = RequestMethod.GET)
    public String syncEmpresas() {
			
		User user = userBusiness.getCurrent();
		 
		try {
			empresaBusiness.syncEmpresasFromUser(user);
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização dos registros de empresas.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização de empresas realizada com Sucesso!</p>";
	}
	
	@ResponseBody
    @RequestMapping(value = "entidades", method = RequestMethod.GET)
    public String syncEntidades() {
			
		User user = userBusiness.getCurrent();
		 
		try {
			entidadeBusiness.syncEntidadesFromUser(user);
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização dos registros de entidades.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização de entidades realizada com Sucesso!</p>";
	}
	
	@ResponseBody
    @RequestMapping(value = "produtos", method = RequestMethod.GET)
    public String syncProdutos() {
			
		User user = userBusiness.getCurrent();
		 
		try {
			produtoBusiness.syncProdutosFromUser(user);
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização dos registros de produtos.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização de produtos realizada com Sucesso!</p>";
	}
	
	@ResponseBody
    @RequestMapping(value = "terceiros", method = RequestMethod.GET)
    public String syncTerceiros() {
			
		User user = userBusiness.getCurrent();
		 
		try {
			terceiroBusiness.syncTerceirosFromUser(user);
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização dos registros de terceiros.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização de terceiros realizada com Sucesso!</p>";
	}
	
	@ResponseBody
    @RequestMapping(value = "notas/mercadorias", method = RequestMethod.GET)
    public String syncNotasMercadorias() {
			
		User user = userBusiness.getCurrent();
		 
		try {
			notasMercadoriaBusiness.syncEntitiesFromUser(user);
		} catch (Exception e) {
			return "<p class='text-error'>Erro ao realizar a atualização dos registros de notas de mercadorias.</p><p>Detalhes: " + e.getMessage() + "</p>";
		}
		
        return "<p class='text-success'>Atualização de notas de mercadorias realizada com Sucesso!</p>";
	}
}
