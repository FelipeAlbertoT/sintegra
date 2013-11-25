package br.com.canalvpsasul.sintegra.business.Imposto;

import br.com.canalvpsasul.vpsabusiness.entities.fiscal.Imposto;

public interface IcmsBusiness {

	Float getValorIsentoNaoTributado(Imposto icms, Float valorTotalItem);
	
	Float getValorOutros(Imposto icms, Float valorTotalItem);
	
}
