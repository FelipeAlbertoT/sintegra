package br.com.canalvpsasul.sintegra.business;

import br.com.canalvpsasul.sintegra.entities.Sintegra;
import br.com.canalvpsasul.sintegra.entities.SintegraParametros;

public interface SintegraBusiness {

	br.com.canalvpsasul.sintegra.entities.Sintegra gerarSintegra(SintegraParametros parametros) throws Exception;
	
	Sintegra getSintegra(Long id) throws Exception;
}
 