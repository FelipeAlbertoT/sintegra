package br.com.canalvpsasul.sintegra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.canalvpsasul.sintegra.entities.Role;

public interface RoleRepository  extends JpaRepository<Role, Long> {

	@Query("select r from Role r where r.name = :name")
	Role findByName(@Param("name") String name); 
	
}
