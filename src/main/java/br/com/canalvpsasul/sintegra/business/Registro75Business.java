package br.com.canalvpsasul.sintegra.business;

import br.com.canalvpsasul.sintegra.entities.Configuracao;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.operacional.Produto;
import coffeepot.br.sintegra.Sintegra;

public interface Registro75Business {

	void addRegistro75(Produto produto, Sintegra sintegra, Empresa empresa, Configuracao configuracaoEmpresa);
	
}
