package br.com.canalvpsasul.sintegra.business.Registros;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import br.com.canalvpsasul.vpsabusiness.entities.fiscal.CupomFiscal;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ReducaoZ;
import coffeepot.br.sintegra.registros.Registro60A;
import coffeepot.br.sintegra.registros.Registro60M;
import coffeepot.br.sintegra.registros.Registro60R;

public interface Registro60Business {

	Registro60M obterRegistro60M(ReducaoZ reducao) throws ParseException;

	ArrayList<Registro60R> obterRegistro60R(List<CupomFiscal> cuponsFiscais);
	
	/**
	 * Para sintegras gerados a partir de Fevereiro de 2014, os totalizadores ser�o trazidos na redu��o Z, sem ser necess�rio calcul�-los via CFs do per�odo.
	 * 
	 * @param cuponsFiscais LIsta de cupons do per�odo de gera��o do sintegra para c�lculo dos totalizadores faltantes na redu��o.
	 * @return Registros 60A referentes aos totalizadores de Cancelamentos, Descontos e ISS.
	 */
	@Deprecated
	List<Registro60A> obterRegistro60A(List<CupomFiscal> cuponsFiscais);
}
