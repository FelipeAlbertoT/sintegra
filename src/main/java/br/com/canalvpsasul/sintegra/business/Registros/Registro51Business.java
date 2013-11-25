package br.com.canalvpsasul.sintegra.business.Registros;

import java.util.List;

import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;
import coffeepot.br.sintegra.registros.Registro51;

public interface Registro51Business {
	
	List<Registro51> obterRegistro51(NotaMercadoria nota);

}
