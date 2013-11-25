package br.com.canalvpsasul.sintegra.business.Registros;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.canalvpsasul.sintegra.business.CombinacaoCfopBusiness;
import br.com.canalvpsasul.sintegra.business.DadosPorTipoNotaBusiness;
import br.com.canalvpsasul.sintegra.business.Imposto.IpiBusiness;
import br.com.canalvpsasul.sintegra.entities.CombinacaoCfopIpi;
import br.com.canalvpsasul.sintegra.entities.DadosPorTipoNota;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;
import coffeepot.br.sintegra.registros.Registro51;

@Service
public class Registro51BusinessImpl implements Registro51Business {

	@Autowired
	private DadosPorTipoNotaBusiness dadosPorTipoNotaBusiness;
	
	@Autowired
	private CombinacaoCfopBusiness combinacaoCfopBusiness;

	@Autowired
	private IpiBusiness ipiBusiness;

	@Override
	public List<Registro51> obterRegistro51(NotaMercadoria nota) {

		DadosPorTipoNota dadosPorTipoNota = dadosPorTipoNotaBusiness
				.obterRemetenteDestinatario(nota);

		List<CombinacaoCfopIpi> combinacoes = combinacaoCfopBusiness.obterCombinacoesCfopIpiDocumento(nota);

		ArrayList<Registro51> registros = new ArrayList<Registro51>();
				
		for (CombinacaoCfopIpi comb : combinacoes) {

			Registro51 registro51 = new Registro51();

			registro51.setDataDocumento(nota.getData());
			registro51.setNumeroDocumento(nota.getNumero());

			registro51.setCfop(Integer.parseInt(comb.getCfop().toString()));
			registro51.setValorIpi(new Double(comb.getValorIpi()));
			registro51.setValorIsentas(new Double(comb.getIsentaNaoTribut()));
			registro51.setValorOutras(new Double(comb.getOutras()));
			registro51.setValorTotal(new Double(comb.getValorTotal()));

			registro51.setCpfCnpj(dadosPorTipoNota.getCnpj());
			registro51.setUf(dadosPorTipoNota.getUf());
			registro51.setIe(dadosPorTipoNota.getIe());
			registro51.setSerieDocumento(dadosPorTipoNota.getSerie());
			registro51.setSituacaoDocumento(dadosPorTipoNota.getSituacao());

			registros.add(registro51);
		}
		return registros;
	}

}
