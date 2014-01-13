package br.com.canalvpsasul.sintegra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.canalvpsasul.sintegra.entities.Configuracao;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.repository.RepositoryBase;

public interface ConfiguracaoRepository extends RepositoryBase<Configuracao> {
	 
	@Query("select c from Configuracao c where c.empresa = :empresa")
	Configuracao findByEmpresa(@Param("empresa") Empresa empresa); 
	
	@Query("select c from Configuracao c where c.empresa IN :empresas")
	List<Configuracao> findByEmpresas(@Param("empresas") List<Empresa> empresas); 
	
}

