package br.com.canalvpsasul.sintegra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.canalvpsasul.sintegra.entities.Informante;
import br.com.canalvpsasul.vpsabusiness.entities.Empresa;

public interface InformanteRepository  extends JpaRepository<Informante, Long> {
	
	@Query("select i from Informante i where i.empresa = :empresa")
	Informante findByEmpresa(@Param("empresa") Empresa empresa); 
	
}

