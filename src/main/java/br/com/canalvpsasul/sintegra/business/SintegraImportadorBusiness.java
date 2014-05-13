package br.com.canalvpsasul.sintegra.business;

import br.com.canalvpsasul.sintegra.entities.SintegraTemplate;

public interface SintegraImportadorBusiness { 

	void processar(SintegraTemplate sintegraTemplate) throws Exception;
}
