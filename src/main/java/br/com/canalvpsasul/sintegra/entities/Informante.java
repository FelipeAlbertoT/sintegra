package br.com.canalvpsasul.sintegra.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.canalvpsasul.vpsabusiness.entities.EntityBaseRoot;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;

@Entity
@Table(name="app_informante")
public class Informante extends EntityBaseRoot  {

	private Empresa empresa;
	
	private String logradouro;	

	private String complemento;
	
	private Integer numero;
	
	private String bairro;
	
	private String cep;
	
	private String nome;
	
	private String telefone;
	
	@Id
	@GeneratedValue
	@Column(name = "id_informante")	
	@Override
	public Long getId() {
		return id;
	}

	@NotNull
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@NotNull
	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@NotNull
	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	@NotNull
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	@NotNull
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@NotNull
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotNull
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@Override
	public boolean equals(final Object anObject) {
	    if (anObject == null) 
	        return false;
	    else if (this == anObject)
	        return true;
	    else if (anObject instanceof Informante) {
	        final Informante aObj = (Informante) anObject;
	        if (aObj.getId() != null) {
	            return aObj.getId().equals(id);
	        }
	    }
	    return false;
	}

	@Override
	public int hashCode() {		
		
		if (id == null)
			return 0;
		
		return Long.valueOf(id).hashCode();
	}
}
