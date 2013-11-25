package br.com.canalvpsasul.sintegra.business.Registros;

import java.util.Date;

import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import coffeepot.br.sintegra.registros.Registro10;
import coffeepot.br.sintegra.tipos.FinalidadeArquivo;
import coffeepot.br.sintegra.tipos.NaturezaOperacao;

public interface Registro10Business {

	Registro10 obterRegistro10(Empresa empresa, Date dataInicial,
			Date dataFinal, FinalidadeArquivo finalidadeArquivo,
			NaturezaOperacao naturezaOperacao) throws Exception;

}
