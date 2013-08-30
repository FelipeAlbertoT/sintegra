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
	
	private NaturezaOperacao naturezaOperacao = NaturezaOperacao.TOTALIDADE_DAS_OPERACOES;

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

	public NaturezaOperacao getNaturezaOperacao() {
		return naturezaOperacao;
	}

	public void setNaturezaOperacao(NaturezaOperacao naturezaOperacao) {
		this.naturezaOperacao = naturezaOperacao;
	}
	
}
