package br.com.canalvpsasul.sintegra.business.Imposto;

import br.com.canalvpsasul.vpsabusiness.entities.fiscal.Imposto;

public interface IpiBusiness {

	Float getValorIsentoNaoTributado(Imposto ipi, Float valorTotalItem);
	
	Float getValorOutros(Imposto ipi, Float valorTotalItem); 
	
}