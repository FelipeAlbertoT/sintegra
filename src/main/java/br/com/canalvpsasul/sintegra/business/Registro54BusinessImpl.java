package br.com.canalvpsasul.sintegra.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.canalvpsasul.sintegra.entities.DadosPorTipoNota;
import br.com.canalvpsasul.sintegra.entities.TipoItemRegistro54;
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

		Float freteTotal = new Float(0);
		Float seguroTotal = new Float(0);
		Float outrasTotal = new Float(0);
		Float pisTotal = new Float(0);
		Float cofinsTotal = new Float(0);
		Float maiorValorItem = new Float(0);
		Long cfop = new Long(0);
		String cst = "";
		
		int count = 1;
		for(ItemNota item : nota.getItens()) {
			
			Registro54 registro54 = new Registro54();
			
			registro54.setCpfCnpj(dadosPorTipoNota.getCnpj());
			registro54.setModeloDocumento(dadosPorTipoNota.getModeloDocumentoFiscal());
			registro54.setSerieDocumento(dadosPorTipoNota.getSerie());

			registro54.setAliquotaIcms(new Double(item.getIcms().getAliquota()));
			registro54.setBasedeCalculoIcms(new Double(item.getIcms().getBase()));
			registro54.setBaseDeCalculoIcmsST(new Double(item.getIcmsst().getBase()));
			registro54.setCfop(Integer.parseInt(String.valueOf(item.getCfop())));
			registro54.setCodigoProduto(item.getProduto().getVpsaId().toString());			 
			registro54.setCst(item.getProduto().getOrigem() + item.getIcms().getCst());			
			registro54.setQuantidade(new Double(item.getQuantidade()));
			registro54.setValorBrutoItem(new Double(item.getQuantidade() * item.getValorUnitario()));			
			registro54.setNumeroDocumento(Integer.parseInt(String.valueOf(nota.getNumero())));
			registro54.setNumeroItem(count++);
			registro54.setValorDesconto(new Double(item.getValorDesconto()));
			if (item.getIpi() != null)
				registro54.setValorIpi(new Double(item.getIpi()
						.getValor()));

			registros.add(registro54);
			
			freteTotal += item.getValorFrete();
			seguroTotal += item.getValorSeguro();
			outrasTotal += item.getValorOutros();
			
			if(item.getPis().getValor() > 0)
				pisTotal += item.getPis().getValor();

			if(item.getCofins().getValor() > 0)
				cofinsTotal += item.getCofins().getValor();
			
			/*
			 * Consideramos o CFOP e CST do item com maior valor para os registros 54 referentes a frete, seguro, outras despesas e pis cofins
			 * */
			if(maiorValorItem < item.getValorTotal()) {
				
				cfop = item.getCfop();
				cst = item.getIcms().getCst();
				
				maiorValorItem = item.getValorTotal();
			}
		}
		
		if(freteTotal > 0)
			registros.add(obterRegistro54(TipoItemRegistro54.FRETE, dadosPorTipoNota, nota, cfop, cst, freteTotal));
		if(seguroTotal > 0)
			registros.add(obterRegistro54(TipoItemRegistro54.SEGURO, dadosPorTipoNota, nota, cfop, cst, seguroTotal));
		if(outrasTotal > 0)
			registros.add(obterRegistro54(TipoItemRegistro54.OUTRASDESPESAS, dadosPorTipoNota, nota, cfop, cst, outrasTotal));
		if(pisTotal > 0)
			registros.add(obterRegistro54(TipoItemRegistro54.PISCOFINS, dadosPorTipoNota, nota, cfop, cst, pisTotal + cofinsTotal));
		
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
		registro54.setValorDesconto(new Double(valorItem));
		
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
