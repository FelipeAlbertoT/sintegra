package br.com.canalvpsasul.sintegra.entities;

public class CombinacaoCfopIcms extends CombinacaoCfop {

	private Float aliquota;
	
	private Float baseIcms;
	
	private Float valorIcms;

	public CombinacaoCfopIcms()  {
		super();
		
		this.baseIcms = (float)0;
		this.valorIcms = (float)0;
	}

	public Float getAliquota() {
		return aliquota;
	}

	public void setAliquota(Float aliquota) {
		this.aliquota = aliquota;
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
}
