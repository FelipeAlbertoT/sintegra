package br.com.canalvpsasul.sintegra.business;

import br.com.canalvpsasul.sintegra.entities.Sintegra;
import br.com.canalvpsasul.sintegra.repository.SintegraRepository;
import br.com.canalvpsasul.vpsabusiness.business.BusinessBaseRoot;

public interface ArmazenamentoSintegra extends BusinessBaseRoot<Sintegra, SintegraRepository> {

	void controlarLimiteArmazenamento(Sintegra sintegra) throws Exception;

	Sintegra armazenarSintegra(Sintegra sintegra) throws Exception;

}
