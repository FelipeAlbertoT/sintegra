package br.com.canalvpsasul.sintegra.business;

import org.springframework.stereotype.Service;

import br.com.canalvpsasul.vpsabusiness.entities.fiscal.Imposto;

@Service  
public class IcmsBusinessImpl implements IcmsBusiness {

	@Override
	public Float getValorIsentoNaoTributado(Imposto icms, Float valorTotalItem) {

		String cst = icms.getCst();
		
		if((cst.endsWith("40") || cst.endsWith("41")) || (cst.endsWith("300") || cst.endsWith("400")))
			return 	valorTotalItem;
		
		if((cst.endsWith("20") || cst.endsWith("30") || cst.endsWith("70")) || (cst.endsWith("203")))
			return obterBaseReduzida(icms, valorTotalItem);
		
		return (float)0;	
	}

	@Override
	public Float getValorOutros(Imposto icms, Float valorTotalItem) {
		
		String cst = icms.getCst();
		
		if((cst.endsWith("50") || cst.endsWith("51") || cst.endsWith("60") || cst.endsWith("90")) || (cst.endsWith("500") || cst.endsWith("900")))
			return 	valorTotalItem;
		
		return (float)0;	
	}

	private Float obterBaseReduzida(Imposto icms, Float valorTotalItem) {
		return valorTotalItem - icms.getBase();		
	}
}
