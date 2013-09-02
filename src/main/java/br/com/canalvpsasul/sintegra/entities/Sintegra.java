package br.com.canalvpsasul.sintegra.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.canalvpsasul.vpsabusiness.entities.EntityBaseRoot;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Terceiro;

@Entity
@Table(name="app_sintegra")
public class Sintegra extends EntityBaseRoot {

	private Terceiro terceiro;
	
	private String sintegra;
	
	@Id
	@GeneratedValue
	@Column(name = "id_sintegra")	
	@Override
	public Long getId() {
		return id;
	}

	@NotNull
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	public Terceiro getTerceiro() {
		return terceiro;
	}

	public void setTerceiro(Terceiro terceiro) {
		this.terceiro = terceiro;
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
