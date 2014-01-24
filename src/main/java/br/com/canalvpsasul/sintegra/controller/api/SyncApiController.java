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
import br.com.canalvpsasul.vpsabusiness.business.administrativo.UserBusiness;
import br.com.canalvpsasul.vpsabusiness.business.operacional.SaldoMercadoriaBusiness;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;

@Controller
@RequestMapping("/api/sync")
public class SyncApiController extends br.com.canalvpsasul.vpsaweb.api.SyncApiController {

	@Autowired
	private UserBusiness userBusiness;
	
	@Autowired
	private EmpresaBusiness empresaBusiness;
	
	@Autowired
	private ConfiguracaoBusiness configuracaoBusiness;
	
	@Autowired
	private SaldoMercadoriaBusiness saldoMercadoriaBusiness; 

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
