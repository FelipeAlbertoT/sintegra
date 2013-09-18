package br.com.canalvpsasul.sintegra.business;

import java.util.List;

import br.com.canalvpsasul.sintegra.entities.Configuracao;
import br.com.canalvpsasul.sintegra.repository.ConfiguracaoRepository;
import br.com.canalvpsasul.vpsabusiness.business.BusinessBaseRoot;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Portal;

public interface ConfiguracaoBusiness extends BusinessBaseRoot<Configuracao, ConfiguracaoRepository> {

	List<Configuracao> getConfiguracaoPorPortal(Portal portal);
	
	Configuracao create();
	
}
