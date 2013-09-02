package br.com.canalvpsasul.sintegra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.canalvpsasul.sintegra.entities.Sintegra;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Terceiro;

public interface SintegraRepository   extends JpaRepository<Sintegra, Long> {
	 
	@Query("select s from Sintegra s where s.terceiro = :terceiro")
	Sintegra findByTerceiro(@Param("terceiro") Terceiro terceiro); 
	
}