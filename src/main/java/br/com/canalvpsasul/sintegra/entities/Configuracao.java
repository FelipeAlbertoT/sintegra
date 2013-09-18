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
@Table(name="app_configuracao")
public class Configuracao extends EntityBaseRoot  {

	private Empresa empresa;

	private Boolean contribuinteIpi;
	
	@Id
	@GeneratedValue
	@Column(name = "id_configuracao")	
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

	public Boolean getContribuinteIpi() {
		return contribuinteIpi;
	}

	public void setContribuinteIpi(Boolean contribuinteIpi) {
		this.contribuinteIpi = contribuinteIpi;
	}
	
	@Override
	public boolean equals(final Object anObject) {
	    if (anObject == null) 
	        return false;
	    else if (this == anObject)
	        return true;
	    else if (anObject instanceof Configuracao) {
	        final Configuracao aObj = (Configuracao) anObject;
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
