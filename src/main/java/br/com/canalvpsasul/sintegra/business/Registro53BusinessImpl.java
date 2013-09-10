package br.com.canalvpsasul.sintegra.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.canalvpsasul.sintegra.entities.CombinacaoCfop;
import br.com.canalvpsasul.sintegra.entities.DadosPorTipoNota;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;
import coffeepot.br.sintegra.registros.Registro53;

@Service
public class Registro53BusinessImpl implements Registro53Business {
	
	@Autowired
	private CombinacaoCfopBusiness combinacaoCfopBusiness;

	@Autowired
	private DadosPorTipoNotaBusiness dadosPorTipoNotaBusiness;

	@Override
	public List<Registro53> obterRegistro53(NotaMercadoria nota) {
		
		DadosPorTipoNota dadosPorTipoNota = dadosPorTipoNotaBusiness.obterRemetenteDestinatario(nota);

		ArrayList<Registro53> registros = new ArrayList<Registro53>();

		List<CombinacaoCfop> combinacoes = combinacaoCfopBusiness.obterCombinacoesCfopDocumento(nota);
		
		for (CombinacaoCfop comb : combinacoes) {
			
			if(comb.getBaseIcmsSt() == 0)
				continue;

			Registro53 registro53 = new Registro53();				
			
			registro53.setCfop(comb.getCfop());
			registro53.setCnpj(dadosPorTipoNota.getCnpj());
			
			//Substituição Tributária informada pelo substituto ou pelo substituído que não incorra em nenhuma das situações anteriores
			//registro53.setCodigoAntecipacao();
			
			registro53.setDataDocumento(nota.getData());
			registro53.setDespesas(new Double(comb.getSeguro() + comb.getFrete() + comb.getOutras()));
			registro53.setEmitente(dadosPorTipoNota.getEmitente());
			registro53.setIe(dadosPorTipoNota.getIe());
			registro53.setModeloDocumento(dadosPorTipoNota.getModeloDocumentoFiscal());
			registro53.setSerieDocumento(dadosPorTipoNota.getSerie());
			registro53.setSituacaoDocumento(dadosPorTipoNota.getSituacao());
			registro53.setNumeroDocumento(nota.getNumero());
			registro53.setUf(dadosPorTipoNota.getUf());

			registro53.setBaseST(new Double(comb.getBaseIcmsSt()));
			registro53.setIcmsRetido(new Double(comb.getValorIcmsSt()));
			
			registros.add(registro53);
		}

		return registros;
	}

}
