package br.com.canalvpsasul.sintegra.business;

import br.com.canalvpsasul.vpsabusiness.entities.fiscal.Imposto;

public class IpiBusinessImpl implements IpiBusiness {

	@Override
	public Float getValorIsentoNaoTributado(Imposto ipi, Float valorTotalItem) {

		String cst = ipi.getCst();
		
		if((cst.endsWith("40") || cst.endsWith("41")) || (cst.endsWith("300") || cst.endsWith("400")))
			return valorTotalItem;;	
		
		return (float)0;	
		
	}

	@Override
	public Float getValorOutros(Imposto ipi, Float valorTotalItem) {
		
		String cst = ipi.getCst();
		
		if((cst.endsWith("49") || cst.endsWith("99")))
			return valorTotalItem;;	
		
		return (float)0; 
	}

}
