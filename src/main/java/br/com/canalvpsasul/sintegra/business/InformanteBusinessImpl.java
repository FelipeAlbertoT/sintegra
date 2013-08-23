package br.com.canalvpsasul.sintegra.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.canalvpsasul.sintegra.entities.Informante;
import br.com.canalvpsasul.sintegra.repository.InformanteRepository;
import br.com.canalvpsasul.vpsabusiness.entities.Empresa;

@Service  
@Transactional
public class InformanteBusinessImpl implements InformanteBusiness {

	@Autowired
	private InformanteRepository informanteRepository;
	
	@Override
	public Informante getInformantePorEmpresa(Empresa empresa) {
		return informanteRepository.findByEmpresa(empresa);
	}

}
