package br.com.canalvpsasul.sintegra.entities;

public class CombinacaoCfopIcms {

	private Long cfop;
	
	private Float aliquota;
	
	private Float valorTotal;
	
	private Float baseIcms;
	
	private Float valorIcms;
	
	private Float isentaNaoTribut;
	
	private Float outras;
	
	public CombinacaoCfopIcms() {
		this.valorTotal = (float)0;
		this.baseIcms = (float)0;
		this.valorIcms = (float)0;
		this.isentaNaoTribut = (float)0;
		this.outras = (float)0;
	}

	public Long getCfop() {
		return cfop;
	}

	public void setCfop(Long cfop) {
		this.cfop = cfop;
	}

	public Float getAliquota() {
		return aliquota;
	}

	public void setAliquota(Float aliquota) {
		this.aliquota = aliquota;
	}

	public Float getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Float valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Float getBaseIcms() {
		return baseIcms;
	}

	public void setBaseIcms(Float baseIcms) {
		this.baseIcms = baseIcms;
	}

	public Float getValorIcms() {
		return valorIcms;
	}

	public void setValorIcms(Float valorIcms) {
		this.valorIcms = valorIcms;
	}

	public Float getIsentaNaoTribut() {
		return isentaNaoTribut;
	}

	public void setIsentaNaoTribut(Float isentaNaoTribut) {
		this.isentaNaoTribut = isentaNaoTribut;
	}

	public Float getOutras() {
		return outras;
	}

	public void setOutras(Float outras) {
		this.outras = outras;
	}
}
