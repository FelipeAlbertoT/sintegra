package br.com.canalvpsasul.sintegra.entities;

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
@Table(name="vpsa_produto_aliquota_icms")
public class ProdutoAliquotaIcmsInterna extends EntityBaseRoot {
	
	private Produto produto;
	
	private Empresa empresa;
	
	private Float aliquota;

	@Id
	@Override
	@Column(name = "id")	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="id_produto", referencedColumnName="id_produto")
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="id_empresa", referencedColumnName="id_empresa")
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Float getAliquota() {
		return aliquota;
	}

	public void setAliquota(Float aliquota) {
		this.aliquota = aliquota;
	}
}
