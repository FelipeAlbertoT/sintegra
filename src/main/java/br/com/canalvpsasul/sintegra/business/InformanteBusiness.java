package br.com.canalvpsasul.sintegra.business;

import br.com.canalvpsasul.sintegra.entities.Informante;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;

public interface InformanteBusiness {

	public Informante getInformantePorEmpresa(Empresa empresa);
			
} 
