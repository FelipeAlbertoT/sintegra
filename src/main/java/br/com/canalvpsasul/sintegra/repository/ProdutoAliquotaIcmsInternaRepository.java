package br.com.canalvpsasul.sintegra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.canalvpsasul.sintegra.entities.ProdutoAliquotaIcmsInterna;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.repository.RepositoryBaseRoot;

public interface ProdutoAliquotaIcmsInternaRepository extends RepositoryBaseRoot<ProdutoAliquotaIcmsInterna> {
	
	@Query("select c from ProdutoAliquotaIcmsInterna c where c.empresa = :empresa")
	List<ProdutoAliquotaIcmsInterna> findByEmpresa(@Param("empresa") Empresa empresa);
	
}
