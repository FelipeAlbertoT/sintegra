package br.com.canalvpsasul.sintegra.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.canalvpsasul.sintegra.entities.Sintegra;
import br.com.canalvpsasul.sintegra.repository.SintegraRepository;
import br.com.canalvpsasul.vpsabusiness.business.BusinessBaseRootImpl;

@Service
public class ArmazenamentoSintegraImpl extends BusinessBaseRootImpl<Sintegra, SintegraRepository> implements ArmazenamentoSintegra {

	@Autowired
	protected ArmazenamentoSintegraImpl(SintegraRepository repository) {
		super(repository, Sintegra.class);
	}
	
	@Override
	public void controlarLimiteArmazenamento(Sintegra sintegra) throws Exception {
		
		if(sintegra.getPortal() == null || sintegra.getEmpresa() == null)
			throw new Exception("É preciso informar um portal e uma empresa para armazenar o arquivo para consulta futura.");
		
		/*
		 * Nessa primeira versão mantemos apenas o último arquivo gerado na base.
		 */
		Sintegra sintegraOld = ((SintegraRepository)repository).findByEmpresa(sintegra.getPortal(), sintegra.getEmpresa());
		if (sintegraOld != null)
			((SintegraRepository)repository).delete(sintegraOld);
		
	}
	
	@Override
	public Sintegra armazenarSintegra(Sintegra sintegra) throws Exception {
		
		controlarLimiteArmazenamento(sintegra);
		
		return ((SintegraRepository)repository).save(sintegra);
	}

	@Override
	protected void validateBeforeSave(Sintegra entity) throws Exception {

	}
	
}
