package br.com.canalvpsasul.sintegra.business;

import java.util.List;

import br.com.canalvpsasul.sintegra.entities.Informante;
import br.com.canalvpsasul.sintegra.repository.InformanteRepository;
import br.com.canalvpsasul.vpsabusiness.business.BusinessBaseRoot;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Portal;

public interface InformanteBusiness extends BusinessBaseRoot<Informante, InformanteRepository> {

	Informante getInformantePorEmpresa(Empresa empresa);
	
	List<Informante> getInformantesPorPortal(Portal portal);
	
	Informante create();
} 
