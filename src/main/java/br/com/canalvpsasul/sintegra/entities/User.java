package br.com.canalvpsasul.sintegra.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="vpsa_user")
public class User {
	
	private Long id;	
	
	private String authCode;
	
	private String token;
	
	private Terceiro terceiro;
	
	private Set<Role> roles;

	@Id
	@GeneratedValue
	@Column(name = "id_user")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "auth_code")
	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	@NotNull
	@Column(name = "access_token")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@NotNull
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="id_terceiro", referencedColumnName="id_terceiro")
	public Terceiro getTerceiro() {
		return terceiro;
	} 

	public void setTerceiro(Terceiro terceiro) {
		this.terceiro = terceiro;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinTable(name="vpsa_user_has_roles", 
		joinColumns = {@JoinColumn(name="id_user", referencedColumnName="id_user")},
		inverseJoinColumns = {@JoinColumn(name="id_role", referencedColumnName="id_role")}
	)
	public Set<Role> getRoles() {
		
		if(roles == null)
			roles = new HashSet<Role>();
		
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}	
	
	@Override
	public boolean equals(final Object anObject) {
	    if (anObject == null) 
	        return false;
	    else if (this == anObject)
	        return true;
	    else if (anObject instanceof User) {
	        final User aObj = (User) anObject;
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