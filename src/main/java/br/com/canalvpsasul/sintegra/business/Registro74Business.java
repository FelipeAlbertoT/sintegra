package br.com.canalvpsasul.sintegra.business;

import java.util.Date;

import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.operacional.Produto;
import coffeepot.br.sintegra.registros.Registro74;

public interface Registro74Business {

	Registro74 obterRegistro74(Produto produto, Empresa empresa, Date dataInventario);	
}
