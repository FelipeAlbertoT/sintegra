package br.com.canalvpsasul.sintegra.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonManagedReference;

import br.com.canalvpsasul.vpsabusiness.entities.EntityBase;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Portal;

@Entity
@Table(name="app_sintegra")
public class Sintegra extends EntityBase {

	private Empresa empresa;
	
	private String sintegra;
	
	public Sintegra() {}
	
	public Sintegra (Portal portal, Empresa empresa, String sintegra) {
		this.portal = portal;
		this.empresa = empresa;
		this.sintegra = sintegra;
	}
	
	@Id
	@GeneratedValue
	@Column(name = "id_sintegra")	
	@Override
	public Long getId() {
		return id;
	}
	
	@NotNull
	@Override
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.EAGER)
	@JoinColumn(name="id_portal", referencedColumnName="id_portal")
	@JsonManagedReference
	public Portal getPortal() {
		return portal;
	}

	@NotNull
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Column(columnDefinition="LONGTEXT")
	public String getSintegra() {
		return sintegra;
	}

	public void setSintegra(String sintegra) {
		this.sintegra = sintegra;
	}

	@Override
	public boolean equals(final Object anObject) {
	    if (anObject == null) 
	        return false;
	    else if (this == anObject)
	        return true;
	    else if (anObject instanceof Sintegra) {
	        final Sintegra aObj = (Sintegra) anObject;
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
