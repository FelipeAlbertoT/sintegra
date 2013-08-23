package br.com.canalvpsasul.sintegra.business;

import java.util.Date;

import coffeepot.br.sintegra.tipos.FinalidadeArquivo;
import coffeepot.br.sintegra.tipos.NaturezaOperacao;

import br.com.canalvpsasul.vpsabusiness.entities.Empresa;

public interface SintegraBusiness {

	public String gerarSintegra(Empresa empresa, Date dataInicial, Date dataFinal, FinalidadeArquivo finalidadeArquivo, NaturezaOperacao naturezaOperacao) throws Exception;
	 
}
 