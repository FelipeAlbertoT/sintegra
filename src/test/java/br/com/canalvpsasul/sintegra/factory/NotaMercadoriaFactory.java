package br.com.canalvpsasul.sintegra.factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ItemNota;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;
import br.com.canalvpsasul.vpsapi.entity.fiscal.ModeloGerencial;
import br.com.canalvpsasul.vpsapi.entity.fiscal.StatusNota;
import br.com.canalvpsasul.vpsapi.entity.fiscal.TipoNota;

public class NotaMercadoriaFactory {
	
	public static NotaMercadoria gerarCabecalhoNotaSaida() {

		Random random = new Random();
		NotaMercadoria notaMercadoria = new NotaMercadoria();

		notaMercadoria.setId(random.nextLong());	
		notaMercadoria.setNumero(new Long(999999));
		notaMercadoria.setModeloGerencial(ModeloGerencial.MERCADORIA);	
		notaMercadoria.setData(new Date());
		notaMercadoria.setTerceiroDestinatario(TerceiroFactory.gerarTerceiroDestinatario());
		notaMercadoria.setTerceiroRemetente(TerceiroFactory.gerarTerceiroRemetente());
		
		return notaMercadoria;
	}
	
	
	public static NotaMercadoria gerarNotaSaidaMercadoriaStIpi() {
			
		NotaMercadoria notaMercadoria = gerarCabecalhoNotaSaida();

		notaMercadoria.setTipo(TipoNota.SAIDA);
		notaMercadoria.setStatus(StatusNota.EMITIDO);
		notaMercadoria.setValorTotal(new Float(1309.60));
		
		ArrayList<ItemNota> itens = new ArrayList<ItemNota>();
		itens.add(ItemNotaFactory.gerarItemNotaComStIpiIcms());
		
		notaMercadoria.setItens(itens);		
		
		return notaMercadoria;	
	}
	
	public static NotaMercadoria gerarNotaSaida() {
	
		NotaMercadoria notaMercadoria = gerarCabecalhoNotaSaida();

		notaMercadoria.setTipo(TipoNota.SAIDA);
		notaMercadoria.setStatus(StatusNota.EMITIDO);
		
		// TODO SINTEGRA Terminar a criação do mock da nota de saída.
		
		return notaMercadoria;	
	}
	
	public static NotaMercadoria gerarNotaSaidaCancelada() {
	
		NotaMercadoria notaMercadoria = gerarCabecalhoNotaSaida();

		notaMercadoria.setTipo(TipoNota.SAIDA);
		notaMercadoria.setStatus(StatusNota.CANCELADO);
		
		// TODO SINTEGRA Terminar a criação do mock da nota de saída cancelada.
		
		return notaMercadoria;	
	}
	
}
