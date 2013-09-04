package br.com.canalvpsasul.sintegra.business;

import org.springframework.stereotype.Service;

import br.com.canalvpsasul.sintegra.entities.DadosPorTipoNota;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Terceiro;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaConsumo;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;
import br.com.canalvpsasul.vpsapi.entity.fiscal.StatusNota;
import br.com.canalvpsasul.vpsapi.entity.fiscal.TipoNota;
import coffeepot.br.sintegra.tipos.DocumentoFiscal;
import coffeepot.br.sintegra.tipos.Emitente;
import coffeepot.br.sintegra.tipos.SituacaoDocumentoFiscal;

@Service 
public class DadosPorTipoNotaBusinessImpl implements DadosPorTipoNotaBusiness {

	@Override
	public DadosPorTipoNota obterRemetenteDestinatario(NotaMercadoria nota) {
		return getRemetenteDestinatario(nota.getStatus(), nota.getTerceiroRemetente(), nota.getTerceiroDestinatario(), nota.getTipo());
	}

	@Override
	public DadosPorTipoNota obterRemetenteDestinatario(NotaConsumo nota) {
		return getRemetenteDestinatario(nota.getStatus(), nota.getTerceiroRemetente(), nota.getTerceiroDestinatario(), nota.getTipo());
	}
	
	private DadosPorTipoNota getRemetenteDestinatario(StatusNota status, Terceiro remetente, Terceiro destinatario, TipoNota tipo) {
		
		DadosPorTipoNota remetenteDestinatario = new DadosPorTipoNota();
		
		remetenteDestinatario.setModeloDocumentoFiscal(DocumentoFiscal.NOTA_FISCAL_ELETRONICA);
		remetenteDestinatario.setEmitente(Emitente.PROPRIO);
		remetenteDestinatario.setSerie(""); // TODO SINTEGRA Solicitado na API VPSA de Nota de Mercadoria.	
		remetenteDestinatario.setCnpj(destinatario.getDocumento());		
		remetenteDestinatario.setIe(destinatario.getIe());
		
		if(destinatario.getEnderecos().size() > 0) 
			remetenteDestinatario.setUf(destinatario.getEnderecos().get(0).getSiglaEstado());
		
		remetenteDestinatario.setSituacao(SituacaoDocumentoFiscal.NORMAL);
		if(status == StatusNota.CANCELADO)
			remetenteDestinatario.setSituacao(SituacaoDocumentoFiscal.CANCELADO);
		
		if(tipo == TipoNota.ENTRADA) {
			
			remetenteDestinatario.setModeloDocumentoFiscal(DocumentoFiscal.NOTA_FISCAL_ENTRADA);
			remetenteDestinatario.setEmitente(Emitente.TERCEIROS);
			remetenteDestinatario.setCnpj(remetente.getDocumento());
			
			if(remetente.getEnderecos().size() > 0)
				remetenteDestinatario.setUf(remetente.getEnderecos().get(0).getSiglaEstado());
			
			remetenteDestinatario.setIe(remetente.getIe());		
		}
		
		return remetenteDestinatario;
	}

}
