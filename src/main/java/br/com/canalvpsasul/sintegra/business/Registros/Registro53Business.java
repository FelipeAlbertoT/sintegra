package br.com.canalvpsasul.sintegra.business.Registros;

import java.util.List;

import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;
import coffeepot.br.sintegra.registros.Registro53;

public interface Registro53Business {

	List<Registro53> obterRegistro53(NotaMercadoria nota);
	
}
