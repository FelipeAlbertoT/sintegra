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

	private Boolean gerarRegistro74;
	
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

	public Boolean getGerarRegistro74() {
		return gerarRegistro74;
	}

	public void setGerarRegistro74(Boolean gerarRegistro74) {
		this.gerarRegistro74 = gerarRegistro74;
	}
	
}
