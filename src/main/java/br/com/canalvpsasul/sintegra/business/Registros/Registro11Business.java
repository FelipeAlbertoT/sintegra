package br.com.canalvpsasul.sintegra.business.Registros;

import br.com.canalvpsasul.sintegra.entities.Informante;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import coffeepot.br.sintegra.registros.Registro11;

public interface Registro11Business {
	
	Registro11 obterRegistro11(Empresa empresa, Informante informante);
}
