package br.com.canalvpsasul.sintegra.business.Registros;

import org.springframework.stereotype.Service;

import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ConhecimentoTransporte;
import br.com.canalvpsasul.vpsapi.entity.fiscal.ModalidadeFrete;
import br.com.canalvpsasul.vpsapi.entity.fiscal.StatusNota;
import br.com.canalvpsasul.vpsapi.entity.fiscal.TipoNota;
import coffeepot.br.sintegra.registros.Registro70;
import coffeepot.br.sintegra.tipos.Frete;
import coffeepot.br.sintegra.tipos.SituacaoDocumentoFiscal;

@Service
public class Registro70BusinessImpl implements Registro70Business {

	@Override
	public Registro70 obterRegistro70(ConhecimentoTransporte conhec){

		Registro70 registro70 = new Registro70(); 
				
		registro70.setBaseDeCalculoIcms((double)conhec.getIcms().getBase());
		registro70.setCfop(conhec.getCfop());
		registro70.setDataEmissaoUtilizacao(conhec.getData());
		registro70.setModeloDocumento("08");
		registro70.setNumeroDocumento(conhec.getNumeroConhecimentoTransporte().intValue());
		registro70.setSerieDocumento(conhec.getSerie());
		registro70.setValorIcms((double)conhec.getIcms().getValor());
		registro70.setValorOutras((double)conhec.getPis().getValor() + conhec.getCofins().getValor());
		registro70.setValorTotal((double)conhec.getValorTotal());
		
		if(conhec.getModalidadeFrete() == ModalidadeFrete.POR_DESTINATARIO)
			registro70.setTipoFrete(Frete.FOB);
		else if(conhec.getModalidadeFrete() == ModalidadeFrete.POR_EMITENTE)
			registro70.setTipoFrete(Frete.CIF);
		else
			registro70.setTipoFrete(Frete.OUTROS);
		
		if(conhec.getStatus() == StatusNota.CANCELADO)
			registro70.setSituacaoDocumento(SituacaoDocumentoFiscal.CANCELADO);
		else
			registro70.setSituacaoDocumento(SituacaoDocumentoFiscal.NORMAL);
		
		
		if(conhec.getTipo()==TipoNota.ENTRADA){
			registro70.setCpfCnpj(conhec.getTerceiroRemetente().getDocumento());
			registro70.setIe(conhec.getTerceiroRemetente().getIe());
			if(conhec.getTerceiroRemetente().getEnderecos().size()>0)
				registro70.setUf(conhec.getTerceiroRemetente().getEnderecos().get(0).getMunicipio().getSiglaEstado());
		}
		if(conhec.getTipo()==TipoNota.SAIDA){
			registro70.setCpfCnpj(conhec.getTerceiroDestinatario().getDocumento());
			registro70.setIe(conhec.getTerceiroDestinatario().getIe());
			if(conhec.getTerceiroDestinatario().getEnderecos().size()>0)
				registro70.setUf(conhec.getTerceiroDestinatario().getEnderecos().get(0).getMunicipio().getSiglaEstado());
		}
					
		
		return registro70;
	}
}
