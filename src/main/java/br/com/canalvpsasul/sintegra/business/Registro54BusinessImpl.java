package br.com.canalvpsasul.sintegra.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.canalvpsasul.sintegra.entities.DadosPorTipoNota;
import br.com.canalvpsasul.sintegra.entities.TipoItemRegistro54;
import br.com.canalvpsasul.sintegra.helper.ConversionUtils;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ItemNota;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;
import coffeepot.br.sintegra.registros.Registro54;

@Service
public class Registro54BusinessImpl implements Registro54Business {

	@Autowired
	private DadosPorTipoNotaBusiness dadosPorTipoNotaBusiness;
	
	@Override
	public List<Registro54> obterRegistro54(NotaMercadoria nota) {

		DadosPorTipoNota dadosPorTipoNota = dadosPorTipoNotaBusiness.obterRemetenteDestinatario(nota);
		
		ArrayList<Registro54> registros = new ArrayList<Registro54>();

		Float freteTotal = Float.valueOf(0);
		Float seguroTotal = Float.valueOf(0);
		Float outrasTotal = Float.valueOf(0);
		Long cfop = Long.valueOf(0);
		String cst = "";
		
		int count = 1;
		for(ItemNota item : nota.getItens()) {
			
			Registro54 registro54 = new Registro54();
			
			registro54.setCpfCnpj(dadosPorTipoNota.getCnpj());
			registro54.setModeloDocumento(dadosPorTipoNota.getModeloDocumentoFiscal());
			registro54.setSerieDocumento(dadosPorTipoNota.getSerie());

			registro54.setAliquotaIcms(ConversionUtils.fromFloat(item.getIcms().getAliquota()));
			registro54.setBasedeCalculoIcms(ConversionUtils.fromFloat(item.getIcms().getBase()));
			registro54.setBaseDeCalculoIcmsST(ConversionUtils.fromFloat(item.getIcmsst().getBase()));
			registro54.setCfop(Integer.parseInt(String.valueOf(item.getCfop())));
			registro54.setCodigoProduto(item.getProduto().getVpsaId().toString());
			registro54.setCst(item.getIcms().getCst());
			registro54.setQuantidade(ConversionUtils.fromFloat(item.getQuantidade()));
			registro54.setValorBrutoItem(ConversionUtils.fromFloat(item.getQuantidade() * item.getValorUnitario()));
			
			registro54.setNumeroDocumento(Integer.parseInt(String.valueOf(nota.getNumero())));
			
			registro54.setNumeroItem(count++);

			registro54.setValorDesconto((double) 0); // TODO SINTEGRA REGISTRO 54 - Após criação da API, alterar o desconto por item.
			if (item.getIpi() != null)
				registro54.setValorIpi(ConversionUtils.fromFloat(item.getIpi()
						.getValor()));

			registros.add(registro54);
			
			
			freteTotal += item.getValorFrete();
			seguroTotal += item.getValorSeguro();
			outrasTotal += item.getValorOutros();
			cfop = item.getCfop(); // TODO SINTEGRA Vpsa não retorna CFOP principal da nota, por momento pegamos qualquer um (Validar com consultor).
			cst = item.getIcms().getCst();
		}
		
		if(freteTotal > 0)
			registros.add(obterRegistro54(TipoItemRegistro54.FRETE, dadosPorTipoNota, nota, cfop, cst, freteTotal));
		if(seguroTotal > 0)
			registros.add(obterRegistro54(TipoItemRegistro54.SEGURO, dadosPorTipoNota, nota, cfop, cst, seguroTotal));
		if(outrasTotal > 0)
			registros.add(obterRegistro54(TipoItemRegistro54.OUTRASDESPESAS, dadosPorTipoNota, nota, cfop, cst, outrasTotal));
		
		// TODO SINTEGRA Registro 54 Gerar registo referente ao PIS/COFINS e Serviços não tributados.
		
		return registros;
	}
	
	private Registro54 obterRegistro54(TipoItemRegistro54 tipo, DadosPorTipoNota dadosPorTipoNota, NotaMercadoria nota, Long cfop, String cst, Float valorItem) {
		
		Registro54 registro54 = new Registro54();

		registro54.setCpfCnpj(dadosPorTipoNota.getCnpj());
		registro54.setModeloDocumento(dadosPorTipoNota.getModeloDocumentoFiscal());
		registro54.setNumeroDocumento(Integer.parseInt(String.valueOf(nota.getNumero())));
		registro54.setCfop(Integer.valueOf(cfop.toString()));
		registro54.setSerieDocumento(dadosPorTipoNota.getSerie());
		registro54.setCst(cst);
		
		registro54.setNumeroItem(Integer.valueOf(tipo.getCode()));
		registro54.setValorDesconto(ConversionUtils.fromFloat(valorItem));
		
		registro54.setCodigoProduto("");
		registro54.setValorBrutoItem(Double.valueOf(0));
		registro54.setBasedeCalculoIcms(Double.valueOf(0));
		registro54.setBaseDeCalculoIcmsST(Double.valueOf(0));
		registro54.setValorIpi(Double.valueOf(0));
		registro54.setAliquotaIcms(Double.valueOf(0));
		registro54.setQuantidade(Double.valueOf(0));
		
		return registro54;
	}
}
