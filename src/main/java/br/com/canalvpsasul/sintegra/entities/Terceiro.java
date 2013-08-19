package br.com.canalvpsasul.sintegra.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@Entity
@Table(name="vpsa_terceiro")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Terceiro {

	private Long id;

	private Long idVpsa;
	
	private String nome;
	
	private String documento;

	@Id
	@GeneratedValue
	@Column(name = "id_terceiro")	
	public Long getId() {
		return id;
	}

	@JsonIgnore
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "id_terceiro_vpsa")
	public Long getIdVpsa() {
		return idVpsa;
	}

	@JsonProperty("id")
	public void setIdVpsa(Long idVpsa) {
		this.idVpsa = idVpsa;
	}

	@NotNull(message="{notnull}") 
	@Size(min=5, message="{min.error}")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotNull(message="{notnull}") 
	@Size(min=11, message="{min.error}")
	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	@Override
	public boolean equals(final Object anObject) {
	    if (anObject == null) 
	        return false;
	    else if (this == anObject)
	        return true;
	    else if (anObject instanceof Terceiro) {
	        final Terceiro aObj = (Terceiro) anObject;
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
