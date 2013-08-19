package br.com.canalvpsasul.sintegra.vpsa;

import br.com.canalvpsasul.sintegra.entities.Terceiro;

public interface VpsaApi {

	Terceiro getTerceiroById(Long id) throws Exception;
	
}
