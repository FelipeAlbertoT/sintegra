package br.com.canalvpsasul.sintegra.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.canalvpsasul.sintegra.entities.Configuracao;
import br.com.canalvpsasul.sintegra.repository.ConfiguracaoRepository;
import br.com.canalvpsasul.vpsabusiness.business.BusinessBaseRootImpl;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Portal;

@Service  
@Transactional
public class ConfiguracaoBusinessImpl extends BusinessBaseRootImpl<Configuracao, ConfiguracaoRepository> implements ConfiguracaoBusiness {

	@Autowired
	protected ConfiguracaoBusinessImpl(ConfiguracaoRepository repository) {
		super(repository, Configuracao.class);
	}

	@Override
	public List<Configuracao> getConfiguracaoPorPortal(Portal portal) {
		return ((ConfiguracaoRepository) repository).findByEmpresas(portal.getEmpresas());
	}

	@Override
	public Configuracao create() {
		return new Configuracao();
	}

	@Override
	protected void validateBeforeSave(Configuracao entity) throws Exception {
		Configuracao configuracao = ((ConfiguracaoRepository) repository).findByEmpresa(entity.getEmpresa());
		
		if(configuracao != null && configuracao.getId() != entity.getId()) {
			throw new Exception("Já existe uma configuração cadastrada para a empresa informada. Selecione outra empresa ou verifique a lista de configurações.");
		}
	}

	@Override
	public Configuracao getConfiguracaoPorEmpresa(Empresa empresa) {
		return ((ConfiguracaoRepository) repository).findByEmpresa(empresa);
	}

}
