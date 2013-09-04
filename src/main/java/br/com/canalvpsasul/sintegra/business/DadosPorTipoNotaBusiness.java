package br.com.canalvpsasul.sintegra.business;

import br.com.canalvpsasul.sintegra.entities.DadosPorTipoNota;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaConsumo;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;

public interface DadosPorTipoNotaBusiness {

	DadosPorTipoNota obterRemetenteDestinatario(NotaMercadoria nota);
	
	DadosPorTipoNota obterRemetenteDestinatario(NotaConsumo nota);
	
}
