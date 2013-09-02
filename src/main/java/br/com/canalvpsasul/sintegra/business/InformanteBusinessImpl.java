package br.com.canalvpsasul.sintegra.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.canalvpsasul.sintegra.entities.Informante;
import br.com.canalvpsasul.sintegra.repository.InformanteRepository;
import br.com.canalvpsasul.vpsabusiness.business.BusinessBaseRootImpl;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Portal;

@Service  
@Transactional
public class InformanteBusinessImpl extends BusinessBaseRootImpl<Informante, InformanteRepository> implements InformanteBusiness {

	@Autowired 
	private InformanteRepository informanteRepository;
	
	@Autowired
	protected InformanteBusinessImpl(InformanteRepository repository) {
		super(repository, Informante.class);
	}
	
	@Override
	public Informante getInformantePorEmpresa(Empresa empresa) {
		return informanteRepository.findByEmpresa(empresa);
	}

	@Override
	public Informante create() {
		return new Informante();
	}

	@Override
	public List<Informante> getInformantesPorPortal(Portal portal) {
		return informanteRepository.findByEmpresas(portal.getEmpresas());
	}

	@Override
	protected void validateBeforeSave(Informante entity) throws Exception  {
		
		Informante informante = informanteRepository.findByEmpresa(entity.getEmpresa());
		
		if(informante != null && informante.getId() != entity.getId()) {
			throw new Exception("Já existe um informante cadastrado para a empresa informada. Selecione outra empresa ou verifique a lista de informantes.");
		}
		
	}
}
