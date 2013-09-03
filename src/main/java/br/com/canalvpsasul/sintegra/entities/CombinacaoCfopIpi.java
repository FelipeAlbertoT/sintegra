package br.com.canalvpsasul.sintegra.entities;

public class CombinacaoCfopIpi extends CombinacaoCfop {

	private Float valorIpi;
	
	public CombinacaoCfopIpi()  {
		super();
		
		this.valorIpi = (float)0;
	}

	public Float getValorIpi() {
		return valorIpi;
	}

	public void setValorIpi(Float valorIpi) {
		this.valorIpi = valorIpi;
	}
	
}
