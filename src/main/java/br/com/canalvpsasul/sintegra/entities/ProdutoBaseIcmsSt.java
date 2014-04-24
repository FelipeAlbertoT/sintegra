package br.com.canalvpsasul.sintegra.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.canalvpsasul.vpsabusiness.entities.EntityBaseRoot;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.operacional.Produto;

@Entity
@Table(name="vpsa_produto_base_icms_st")
public class ProdutoBaseIcmsSt extends EntityBaseRoot {
	
	private Empresa empresa;
	
	private Produto produto;

	private Float base;
	
	private Date data;

	@Id
	@Override
	@Column(name = "id_base")	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="id_empresa", referencedColumnName="id_empresa")
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="id_produto", referencedColumnName="id_produto")
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Float getBase() {
		return base;
	}

	public void setBase(Float base) {
		this.base = base;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
