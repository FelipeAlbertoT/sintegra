package br.com.canalvpsasul.sintegra.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.canalvpsasul.sintegra.entities.CombinacaoCfopIcms;
import br.com.canalvpsasul.sintegra.entities.DadosPorTipoNota;
import br.com.canalvpsasul.sintegra.helper.ConversionUtils;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaConsumo;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;
import br.com.canalvpsasul.vpsapi.entity.fiscal.StatusNota;
import coffeepot.br.sintegra.registros.Registro50;

@Service
public class Registro50BusinessImpl implements Registro50Business {

	@Autowired
	private IcmsBusiness icmsBusiness;
	
	@Autowired
	private CombinacaoCfopBusiness combinacaoCfopBusiness;

	@Autowired
	private DadosPorTipoNotaBusiness dadosPorTipoNotaBusiness;

	@Override
	public List<Registro50> obterRegistro50(NotaMercadoria nota) {

		DadosPorTipoNota dadosPorTipoNota = dadosPorTipoNotaBusiness.obterRemetenteDestinatario(nota);

		ArrayList<Registro50> registros = new ArrayList<Registro50>();

		List<CombinacaoCfopIcms> combinacoes = combinacaoCfopBusiness.obterCombinacoesCfopIcmsDocumento(nota);

		for (CombinacaoCfopIcms comb : combinacoes) {

			Registro50 registro50 = new Registro50();

			registro50.setDataDocumento(nota.getData());
			registro50.setNumeroDocumento(nota.getNumero());

			registro50.setEmitente(dadosPorTipoNota.getEmitente());
			registro50.setCpfCnpj(dadosPorTipoNota.getCnpj());
			registro50.setUf(dadosPorTipoNota.getUf());
			registro50.setIe(dadosPorTipoNota.getIe());
			registro50.setModeloDocumento(dadosPorTipoNota.getModeloDocumentoFiscal());
			registro50.setSerieDocumento(dadosPorTipoNota.getSerie());
			registro50.setSituacaoDocumento(dadosPorTipoNota.getSituacao());
			
			registro50.setCfop(Integer.parseInt(comb.getCfop().toString()));
			
			if(nota.getStatus() == StatusNota.CANCELADO) {
				registro50.setAliquotaIcms(Double.valueOf(0));
				registro50.setBaseDeCalculoIcms(Double.valueOf(0));
				registro50.setValorIcms(Double.valueOf(0));
				registro50.setValorIsentas(Double.valueOf(0));
				registro50.setValorOutras(Double.valueOf(0));
				registro50.setValorTotal(Double.valueOf(0));
			}
			else {
				registro50.setAliquotaIcms(ConversionUtils.fromFloat(comb.getAliquota()));
				registro50.setBaseDeCalculoIcms(ConversionUtils.fromFloat(comb.getBaseIcms()));
				registro50.setValorIcms(ConversionUtils.fromFloat(comb.getValorIcms()));
				registro50.setValorIsentas(ConversionUtils.fromFloat(comb.getIsentaNaoTribut()));
				registro50.setValorOutras(ConversionUtils.fromFloat(comb.getOutras()));
				registro50.setValorTotal(new Double(comb.getValorTotal()));
			}
			
			registros.add(registro50);
		}

		return registros;
	}

	@Override
	public Registro50 obterRegistro50(NotaConsumo nota) {

		DadosPorTipoNota dadosPorTipoNota = dadosPorTipoNotaBusiness.obterRemetenteDestinatario(nota);

		Registro50 registro50 = new Registro50();

		registro50.setCfop(Integer.parseInt(nota.getCfop().toString()));
		
		registro50.setEmitente(dadosPorTipoNota.getEmitente());
		registro50.setCpfCnpj(dadosPorTipoNota.getCnpj());
		registro50.setUf(dadosPorTipoNota.getUf());
		registro50.setIe(dadosPorTipoNota.getIe());
		registro50.setModeloDocumento(dadosPorTipoNota.getModeloDocumentoFiscal());
		registro50.setSerieDocumento(dadosPorTipoNota.getSerie());
		registro50.setSituacaoDocumento(dadosPorTipoNota.getSituacao());

		if(nota.getStatus() == StatusNota.CANCELADO) {
			registro50.setAliquotaIcms(Double.valueOf(0));
			registro50.setBaseDeCalculoIcms(Double.valueOf(0));
			registro50.setValorIcms(Double.valueOf(0));
			registro50.setValorIsentas(Double.valueOf(0));
			registro50.setValorOutras(Double.valueOf(0));
			registro50.setValorTotal(Double.valueOf(0));
		}
		else {
			registro50.setAliquotaIcms(ConversionUtils.fromFloat(nota.getIcms().getAliquota()));
			registro50.setBaseDeCalculoIcms(ConversionUtils.fromFloat(nota.getIcms().getBase()));
			registro50.setValorIcms(ConversionUtils.fromFloat(nota.getIcms().getValor()));			
			registro50.setValorIsentas(ConversionUtils.fromFloat(icmsBusiness.getValorIsentoNaoTributado(nota.getIcms(),nota.getValorTotal())));
			registro50.setValorOutras(ConversionUtils.fromFloat(icmsBusiness.getValorOutros(nota.getIcms(), nota.getValorTotal())));
			registro50.setValorTotal(ConversionUtils.fromFloat(nota.getValorTotal()));
		}
		
		registro50.setDataDocumento(nota.getData());
		registro50.setNumeroDocumento(nota.getNumero());
		
		return registro50;
		
	}

}
