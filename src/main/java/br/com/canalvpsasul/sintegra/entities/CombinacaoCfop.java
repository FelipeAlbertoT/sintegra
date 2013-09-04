package br.com.canalvpsasul.sintegra.entities;

public class CombinacaoCfop {

	protected Long cfop;
	
	protected Float valorTotal;

	protected Float isentaNaoTribut; 
	
	protected Float outras;
	
	protected Float frete;
	
	protected Float seguro;
	
	public CombinacaoCfop() {
		this.valorTotal = (float)0;
		this.isentaNaoTribut = (float)0;
		this.outras = (float)0;
		this.frete = (float)0;
		this.seguro = (float)0;
	}

	public Long getCfop() {
		return cfop;
	}

	public void setCfop(Long cfop) {
		this.cfop = cfop;
	}

	public Float getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Float valorTotal) {
		this.valorTotal = valorTotal;
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

	public Float getFrete() {
		return frete;
	}

	public void setFrete(Float frete) {
		this.frete = frete;
	}

	public Float getSeguro() {
		return seguro;
	}

	public void setSeguro(Float seguro) {
		this.seguro = seguro;
	}
}
