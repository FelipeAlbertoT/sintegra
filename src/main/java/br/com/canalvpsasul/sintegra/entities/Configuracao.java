package br.com.canalvpsasul.sintegra.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.canalvpsasul.vpsabusiness.entities.EntityBaseRoot;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Entidade;

@Entity
@Table(name="app_configuracao")
public class Configuracao extends EntityBaseRoot  {

	private Empresa empresa;

	private Boolean contribuinteIpi;
	
	private List<Entidade> entidades;
	
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
	
	/**
	 * @return Lista de entidade consideradas no cálculo do saldo dos produtos para o registro 74.
	 */
	@OneToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	public List<Entidade> getEntidades() {
		return entidades;
	}

	/**
	 * @param entidades Define a lista de entidade consideradas no cálculo do saldo dos produtos para o registro 74.
	 */
	public void setEntidades(List<Entidade> entidades) {
		this.entidades = entidades;
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
