package br.com.canalvpsasul.sintegra.entities;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="role")
public class Role {
	
	private Long id;
	
	private String name;
		
	private Set<User> userRoles;  

	@Id
	@GeneratedValue
	@Column(name = "id_role")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String role) {
		this.name = role;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinTable(name="vpsa_user_has_roles", 
		joinColumns = {@JoinColumn(name="id_role", referencedColumnName="id_role")},
		inverseJoinColumns = {@JoinColumn(name="id_user", referencedColumnName="id_user")}
	)
	public Set<User> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<User> userRoles) {
		this.userRoles = userRoles;
	}
	
	@Override
	public boolean equals(final Object anObject) {
	    if (anObject == null) 
	        return false;
	    else if (this == anObject)
	        return true;
	    else if (anObject instanceof Role) {
	        final Role aObj = (Role) anObject;
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