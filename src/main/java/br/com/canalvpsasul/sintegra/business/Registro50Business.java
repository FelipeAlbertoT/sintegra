package br.com.canalvpsasul.sintegra.business;

import java.util.List;

import br.com.canalvpsasul.sintegra.entities.CombinacaoCfopIcms;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaConsumo;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;
import coffeepot.br.sintegra.registros.Registro50;

public interface Registro50Business {

	List<CombinacaoCfopIcms> obterCombinacoesDocumento(NotaMercadoria nota);
		
	List<Registro50> obterRegistro50(NotaMercadoria nota);
	
	Registro50 obterRegistro50(NotaConsumo nota);
	
}
