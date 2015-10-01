package br.com.canalvpsasul.sintegra.business.Registros;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Endereco;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Terceiro;
import br.com.canalvpsasul.vpsabusiness.utils.StringUtils;
import coffeepot.br.sintegra.registros.Registro10;
import coffeepot.br.sintegra.tipos.Convenio;
import coffeepot.br.sintegra.tipos.FinalidadeArquivo;
import coffeepot.br.sintegra.tipos.NaturezaOperacao;

@Service
public class Registro10BusinessImpl implements Registro10Business {

	@Override
	public Registro10 obterRegistro10(Empresa empresa, Date dataInicial,
			Date dataFinal, FinalidadeArquivo finalidadeArquivo,
			NaturezaOperacao naturezaOperacao) throws Exception {

		Registro10 registro10 = new Registro10();		
		
		/*
		 * TODO SINTEGRA (LIMITAÇÃO) - Colocar como risco (limitação) do mecanismo. Apenas tratamentos o convênio CONV_3_5795_7603.
		 * */
		registro10.setCodigoConvenio(Convenio.CONV_3_5795_7603);
		registro10.setDataInicial(dataInicial);
		registro10.setDataFinal(dataFinal);
		registro10.setFinalidadeArquivo(finalidadeArquivo);
		registro10.setNaturezaOperacao(naturezaOperacao);
		registro10.setCnpj(empresa.getDocumento());

		Terceiro empresaTerceiro = empresa.getTerceiro();
		if (empresaTerceiro == null)
			throw new Exception(
					"O usuário logado não tem privilégios suficientes para obter todas as informações necessárias na Varejonline.");

		if (empresaTerceiro.getEnderecos().size() == 0)
			throw new Exception(
					"Não existe endereço configurado no Varejonline para a empresa informada. Verifique o cadastro de terceiros da Varejonline e atualize o registro.");
		
		Endereco empresaEndereco = empresaTerceiro.getEnderecos().get(0);
		
		registro10.setRazaoSocial(StringUtils.removeAccents(empresaTerceiro.getNome()));
		registro10.setCidade(StringUtils.removeAccents(empresaEndereco.getMunicipio().getNome()));
		registro10.setUf(empresaEndereco.getMunicipio().getSiglaEstado());
		registro10.setIe(empresaTerceiro.getIe());
		registro10.setFax("");

		return registro10;
	}

}
