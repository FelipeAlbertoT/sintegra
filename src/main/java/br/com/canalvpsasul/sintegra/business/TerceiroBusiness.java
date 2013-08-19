package br.com.canalvpsasul.sintegra.business;

import br.com.canalvpsasul.sintegra.entities.Terceiro;

public interface TerceiroBusiness {

	public Terceiro get(Long id);
	
	public Terceiro reloadAndMerge(Terceiro terceiro);
	
	public Terceiro save(Terceiro terceiro); 
	
}
