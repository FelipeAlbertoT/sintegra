package br.com.canalvpsasul.sintegra.business;

import org.springframework.stereotype.Service;

import br.com.canalvpsasul.sintegra.entities.DadosPorTipoNota;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Terceiro;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaConsumo;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;
import br.com.canalvpsasul.vpsapi.entity.fiscal.ModeloGerencial;
import br.com.canalvpsasul.vpsapi.entity.fiscal.StatusNota;
import br.com.canalvpsasul.vpsapi.entity.fiscal.TipoNota;
import coffeepot.br.sintegra.tipos.DocumentoFiscal;
import coffeepot.br.sintegra.tipos.Emitente;
import coffeepot.br.sintegra.tipos.SituacaoDocumentoFiscal;

@Service 
public class DadosPorTipoNotaBusinessImpl implements DadosPorTipoNotaBusiness {

	@Override
	public DadosPorTipoNota obterRemetenteDestinatario(NotaMercadoria nota) {
		return getRemetenteDestinatario(nota.getStatus(), nota.getTerceiroRemetente(), nota.getTerceiroDestinatario(), nota.getTipo(), nota.getModeloGerencial(), nota.getSerie());
	}

	@Override
	public DadosPorTipoNota obterRemetenteDestinatario(NotaConsumo nota) {
		return getRemetenteDestinatario(nota.getStatus(), nota.getTerceiroRemetente(), nota.getTerceiroDestinatario(), nota.getTipo(), nota.getModeloGerencial(), nota.getSerie());
	}
	
	@SuppressWarnings("incomplete-switch")
	private DadosPorTipoNota getRemetenteDestinatario(StatusNota status, Terceiro remetente, Terceiro destinatario, TipoNota tipo, ModeloGerencial modelo, String serie) {
		
		DocumentoFiscal tipoDocumentoFiscal = null;
		
		switch(modelo) {
			
			case COMUNICACAO:
				tipoDocumentoFiscal = DocumentoFiscal.NOTA_FISCAL_SERVICO_COMUNICACAO;
				break;
			case ENERGIA_ELETRICA:
				tipoDocumentoFiscal = DocumentoFiscal.NOTA_FISCAL_ENERGIA_ELETRICA;
				break;
			case MERCADORIA:
				
				tipoDocumentoFiscal = DocumentoFiscal.NOTA_FISCAL_ELETRONICA;
				if(tipo == TipoNota.ENTRADA)				
					tipoDocumentoFiscal = DocumentoFiscal.NOTA_FISCAL;
				
				break;
			case TELECOMUNICACAO:
				tipoDocumentoFiscal = DocumentoFiscal.NOTA_FISCAL_SERVICO_TELECOMUNICACAO;
				break;
			case CTE:
				tipoDocumentoFiscal = DocumentoFiscal.CONHECIMENTO_TRANSPORTE_ELETRONICO;
				break;
			case IMPRESSO:
				tipoDocumentoFiscal = DocumentoFiscal.CONHECIMENTO_TRANSPORTE_RODOVIARIO_CARGAS;
				break;
			case DOISD:
				tipoDocumentoFiscal = DocumentoFiscal.NOTA_FISCAL_VENDA_CONSUMIDOR;
				break;
			case DOISC:
				tipoDocumentoFiscal = DocumentoFiscal.NOTA_FISCAL_VENDA_CONSUMIDOR;
				break;
			case DOISB:
				tipoDocumentoFiscal = DocumentoFiscal.NOTA_FISCAL_VENDA_CONSUMIDOR;
				break;			
		}
		
		
		DadosPorTipoNota remetenteDestinatario = new DadosPorTipoNota();
		
		remetenteDestinatario.setModeloDocumentoFiscal(tipoDocumentoFiscal);
		remetenteDestinatario.setEmitente(Emitente.PROPRIO);
		remetenteDestinatario.setSerie(serie);	
		remetenteDestinatario.setCnpj(destinatario.getDocumento());		
		remetenteDestinatario.setIe(destinatario.getIe());
		
		if(destinatario.getEnderecos().size() > 0) 
			remetenteDestinatario.setUf(destinatario.getEnderecos().get(0).getSiglaEstado());
		
		remetenteDestinatario.setSituacao(SituacaoDocumentoFiscal.NORMAL);
		if(status == StatusNota.CANCELADO)
			remetenteDestinatario.setSituacao(SituacaoDocumentoFiscal.CANCELADO);
		
		if(tipo == TipoNota.ENTRADA) {
			
			remetenteDestinatario.setEmitente(Emitente.TERCEIROS);
			remetenteDestinatario.setCnpj(remetente.getDocumento());
			
			if(remetente.getEnderecos().size() > 0)
				remetenteDestinatario.setUf(remetente.getEnderecos().get(0).getSiglaEstado());
			
			remetenteDestinatario.setIe(remetente.getIe());		
		}
		
		return remetenteDestinatario;
	}

}
