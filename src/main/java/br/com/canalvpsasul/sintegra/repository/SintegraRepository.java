package br.com.canalvpsasul.sintegra.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.canalvpsasul.sintegra.entities.Sintegra;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Portal;
import br.com.canalvpsasul.vpsabusiness.repository.RepositoryBase;

public interface SintegraRepository   extends RepositoryBase<Sintegra> {
	 
	@Query("select s from Sintegra s where s.portal = :portal and s.empresa = :empresa")
	Sintegra findByEmpresa(@Param("portal") Portal portal, @Param("empresa") Empresa empresa); 
	
}