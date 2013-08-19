package br.com.canalvpsasul.sintegra.business;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.canalvpsasul.sintegra.entities.Terceiro;
import br.com.canalvpsasul.sintegra.repository.TerceiroRepository;

@Service  
@Transactional
public class TerceiroBusinessImpl implements TerceiroBusiness {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired  
    private TerceiroRepository terceiroRepository;  
		
	@Override
	public Terceiro get(Long id) {
		return terceiroRepository.findOne(id);
	}

	@Override
	public Terceiro reloadAndMerge(Terceiro terceiro) {
		Terceiro attachedTerceiro = entityManager.getReference(Terceiro.class, terceiro.getId());
		entityManager.merge(terceiro);
		return attachedTerceiro;
	}

	@Override
	public Terceiro save(Terceiro terceiro) {

		if(terceiro.getId() != null)
		{
			entityManager.getReference(Terceiro.class, terceiro.getId());
			entityManager.merge(terceiro);
			
			return terceiro;
		}
		
		return terceiroRepository.save(terceiro);
	}

}
