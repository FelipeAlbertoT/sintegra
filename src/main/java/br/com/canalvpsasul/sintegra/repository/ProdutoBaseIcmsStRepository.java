package br.com.canalvpsasul.sintegra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.canalvpsasul.sintegra.entities.ProdutoBaseIcmsSt;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.operacional.Produto;
import br.com.canalvpsasul.vpsabusiness.repository.RepositoryBaseRoot;

public interface ProdutoBaseIcmsStRepository extends RepositoryBaseRoot<ProdutoBaseIcmsSt> {
	
	@Query("select max(a) from ProdutoBaseIcmsSt a where a.empresa = :empresa and a.produto = :produto")
	ProdutoBaseIcmsSt findLastByEmpresaAndProduto(@Param("empresa") Empresa empresa, @Param("produto") Produto produto); 
	
	@Query("select a from ProdutoBaseIcmsSt a where a.empresa = :empresa and a.base > 0")
	List<ProdutoBaseIcmsSt> findProdutosComBaseST(@Param("empresa") Empresa empresa); 
	
}
