package br.com.canalvpsasul.sintegra.entities;

import coffeepot.br.sintegra.tipos.DocumentoFiscal;
import coffeepot.br.sintegra.tipos.Emitente;
import coffeepot.br.sintegra.tipos.SituacaoDocumentoFiscal;

public class DadosPorTipoNota {

private DocumentoFiscal modeloDocumentoFiscal;
	
	private SituacaoDocumentoFiscal situacao;
	
	private Emitente emitente;
	
	private String ie;
	
	private String serie;
	
	private String cnpj;
	
	private String uf;
	
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
