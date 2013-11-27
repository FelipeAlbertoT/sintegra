package br.com.canalvpsasul.sintegra.business.Registros;

import java.util.ArrayList;
import java.util.List;

import br.com.canalvpsasul.vpsabusiness.entities.fiscal.CupomFiscal;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ReducaoZ;
import coffeepot.br.sintegra.registros.Registro60M;
import coffeepot.br.sintegra.registros.Registro60R;

public interface Registro60Business {

	Registro60M obterRegistro60M(ReducaoZ reducao);
	
	ArrayList<Registro60R> obterRegistro60R(List<CupomFiscal> cuponsFiscais);
	
}
