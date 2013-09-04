package br.com.canalvpsasul.sintegra.business;

import java.util.List;

import br.com.canalvpsasul.sintegra.entities.CombinacaoCfopIpi;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;
import coffeepot.br.sintegra.registros.Registro51;

public interface Registro51Business {
	
	List<CombinacaoCfopIpi> obterCombinacoesDocumento(NotaMercadoria nota);
	
	List<Registro51> obterRegistro51(NotaMercadoria nota);

}
