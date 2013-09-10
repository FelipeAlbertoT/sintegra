package br.com.canalvpsasul.sintegra.business;

import java.util.List;

import br.com.canalvpsasul.sintegra.entities.CombinacaoCfop;
import br.com.canalvpsasul.sintegra.entities.CombinacaoCfopIcms;
import br.com.canalvpsasul.sintegra.entities.CombinacaoCfopIpi;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;

public interface CombinacaoCfopBusiness {

	List<CombinacaoCfopIcms> obterCombinacoesCfopIcmsDocumento(NotaMercadoria nota);
	
	List<CombinacaoCfopIpi> obterCombinacoesCfopIpiDocumento(NotaMercadoria nota);
	
	List<CombinacaoCfop> obterCombinacoesCfopDocumento(NotaMercadoria nota);;
	
}
