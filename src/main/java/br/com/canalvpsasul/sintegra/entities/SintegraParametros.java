package br.com.canalvpsasul.sintegra.entities;

import java.util.Date;

import javax.validation.constraints.NotNull;

import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import coffeepot.br.sintegra.tipos.FinalidadeArquivo;
import coffeepot.br.sintegra.tipos.NaturezaOperacao;

public class SintegraParametros {

	@NotNull
	private Empresa empresa;
	
	@NotNull
	private Date dataInicial;
	
	@NotNull
	private Date dataFinal;
	
	@NotNull
	private FinalidadeArquivo finalidadeArquivo;
	
	@NotNull
	private Date dataInventario;
	
	private NaturezaOperacao naturezaOperacao = NaturezaOperacao.TOTALIDADE_DAS_OPERACOES;
	
	private Boolean gerarRegistro50;
	
	private Boolean gerarRegistro51;
	
	private Boolean gerarRegistro53;
	
	private Boolean gerarRegistro54;
	
	private Boolean gerarRegistro60;

	private Boolean gerarRegistro74;
	
	private Boolean gerarRegistro75;
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public FinalidadeArquivo getFinalidadeArquivo() {
		return finalidadeArquivo;
	}

	public void setFinalidadeArquivo(FinalidadeArquivo finalidadeArquivo) {
		this.finalidadeArquivo = finalidadeArquivo;
	}

	public Date getDataInventario() {
		return dataInventario;
	}

	public void setDataInventario(Date dataInventario) {
		this.dataInventario = dataInventario;
	}

	public NaturezaOperacao getNaturezaOperacao() {
		return naturezaOperacao;
	}

	public void setNaturezaOperacao(NaturezaOperacao naturezaOperacao) {
		this.naturezaOperacao = naturezaOperacao;
	}

	public Boolean getGerarRegistro50() {
		return gerarRegistro50;
	}

	public void setGerarRegistro50(Boolean gerarRegistro50) {
		this.gerarRegistro50 = gerarRegistro50;
	}

	public Boolean getGerarRegistro51() {
		return gerarRegistro51;
	}

	public void setGerarRegistro51(Boolean gerarRegistro51) {
		this.gerarRegistro51 = gerarRegistro51;
	}

	public Boolean getGerarRegistro53() {
		return gerarRegistro53;
	}

	public void setGerarRegistro53(Boolean gerarRegistro53) {
		this.gerarRegistro53 = gerarRegistro53;
	}

	public Boolean getGerarRegistro54() {
		return gerarRegistro54;
	}

	public void setGerarRegistro54(Boolean gerarRegistro54) {
		this.gerarRegistro54 = gerarRegistro54;
	}

	public Boolean getGerarRegistro60() {
		return gerarRegistro60;
	}

	public void setGerarRegistro60(Boolean gerarRegistro60) {
		this.gerarRegistro60 = gerarRegistro60;
	}

	public Boolean getGerarRegistro74() {
		return gerarRegistro74;
	}

	public void setGerarRegistro74(Boolean gerarRegistro74) {
		this.gerarRegistro74 = gerarRegistro74;
	}

	public Boolean getGerarRegistro75() {
		return gerarRegistro75;
	}

	public void setGerarRegistro75(Boolean gerarRegistro75) {
		this.gerarRegistro75 = gerarRegistro75;
	}
	
}
