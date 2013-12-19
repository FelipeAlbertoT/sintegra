package br.com.canalvpsasul.sintegra.business.Registros;

import java.util.ArrayList;
import java.util.List;

import br.com.canalvpsasul.vpsabusiness.entities.fiscal.CupomFiscal;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ReducaoZ;
import coffeepot.br.sintegra.registros.Registro60A;
import coffeepot.br.sintegra.registros.Registro60M;
import coffeepot.br.sintegra.registros.Registro60R;

public interface Registro60Business {

	Registro60M obterRegistro60M(ReducaoZ reducao);
	
	/**
	 * Retorna os registros 60A dos totalizadores exigidos pelo sintegra mais que não são retornados pela redução Z, como CANC e DESC. 
	 * 
	 * @param cuponsFiscais Lista dos cupons fiscais emitidos pela ECF na data do movimento representado pela redução Z.
	 * @return Lista dos registros 60 A referentes aos totalizadores CANC e DESC quando aplicados.
	 */
	List<Registro60A> obterRegistro60A(List<CupomFiscal> cuponsFiscais);
	
	ArrayList<Registro60R> obterRegistro60R(List<CupomFiscal> cuponsFiscais);
	
}
