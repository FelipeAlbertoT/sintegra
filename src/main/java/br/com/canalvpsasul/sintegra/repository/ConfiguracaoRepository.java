package br.com.canalvpsasul.sintegra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.canalvpsasul.sintegra.entities.Configuracao;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;

public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Long> {
	 
	@Query("select c from Configuracao c where c.empresa = :empresa")
	Configuracao findByEmpresa(@Param("empresa") Empresa empresa); 
	
	@Query("select c from Configuracao c where c.empresa IN :empresas")
	List<Configuracao> findByEmpresas(@Param("empresas") List<Empresa> empresas); 
	
}

