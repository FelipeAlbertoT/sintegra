package br.com.canalvpsasul.sintegra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.canalvpsasul.sintegra.entities.Informante;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.repository.RepositoryBase;

public interface InformanteRepository  extends RepositoryBase<Informante> {
	 
	@Query("select i from Informante i where i.empresa = :empresa")
	Informante findByEmpresa(@Param("empresa") Empresa empresa); 
	
	@Query("select i from Informante i where i.empresa IN :empresas")
	List<Informante> findByEmpresas(@Param("empresas") List<Empresa> empresas); 
	
}

