package br.com.canalvpsasul.sintegra.business;

import java.util.List;

import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;
import coffeepot.br.sintegra.registros.Registro54;

public interface Registro54Business {

	List<Registro54> obterRegistro54(NotaMercadoria nota);
	
}
