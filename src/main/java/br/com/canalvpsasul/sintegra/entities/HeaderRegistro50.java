package br.com.canalvpsasul.sintegra.entities;

import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Terceiro;
import br.com.canalvpsasul.vpsapi.entity.fiscal.StatusNota;
import br.com.canalvpsasul.vpsapi.entity.fiscal.TipoNota;
import coffeepot.br.sintegra.tipos.DocumentoFiscal;
import coffeepot.br.sintegra.tipos.Emitente;
import coffeepot.br.sintegra.tipos.SituacaoDocumentoFiscal;

public class HeaderRegistro50 {

	private DocumentoFiscal modeloDocumentoFiscal;
	
	private SituacaoDocumentoFiscal situacao;
	
	private Emitente emitente;
	
	private String ie;
	
	private String serie;
	
	private String cnpj;
	
	private String uf;
	
	public HeaderRegistro50(StatusNota status, Terceiro remetende, Terceiro destinatario, TipoNota tipo) {
		
		this.modeloDocumentoFiscal = DocumentoFiscal.NOTA_FISCAL_ELETRONICA;
		this.situacao = SituacaoDocumentoFiscal.NORMAL;
		this.emitente = Emitente.PROPRIO;
		
		this.ie = ""; // TODO SINTEGRA Solicitado na API VPSA de terceiros.
		this.serie = ""; // TODO SINTEGRA Solicitado na API VPSA de Nota de Mercadoria.
		
		this.cnpj = destinatario.getDocumento();			
		
		if(destinatario.getEnderecos().size() > 0) 
			this.uf = destinatario.getEnderecos().get(0).getSiglaEstado();
		
		if(status == StatusNota.CANCELADO)
			this.situacao = SituacaoDocumentoFiscal.CANCELADO;
		
		if(tipo == TipoNota.ENTRADA) {
			
			this.modeloDocumentoFiscal = DocumentoFiscal.NOTA_FISCAL_ENTRADA;
			this.emitente = Emitente.TERCEIROS;
			this.cnpj = remetende.getDocumento();
			
			if(remetende.getEnderecos().size() > 0)
				this.uf = remetende.getEnderecos().get(0).getSiglaEstado();
			
			this.ie = ""; // TODO SINTEGRA Solicitado na API VPSA de terceiros.			
		}
	}
	
	public DocumentoFiscal getModeloDocumentoFiscal() {
		return modeloDocumentoFiscal;
	}

	public void setModeloDocumentoFiscal(DocumentoFiscal modeloDocumentoFiscal) {
		this.modeloDocumentoFiscal = modeloDocumentoFiscal;
	}

	public SituacaoDocumentoFiscal getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoDocumentoFiscal situacao) {
		this.situacao = situacao;
	}

	public Emitente getEmitente() {
		return emitente;
	}

	public void setEmitente(Emitente emitente) {
		this.emitente = emitente;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
	
}
