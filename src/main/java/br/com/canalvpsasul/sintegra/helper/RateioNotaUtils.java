package br.com.canalvpsasul.sintegra.helper;

import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ItemNota;

public class RateioNotaUtils {

	/**
	 * Para notas com mais de uma CFOP, onde v�rios registros s�o gerados a partir da mesma nota, � necess�rio ratear o total da nota entre os registros gerados de acordo com os itens associados.
	 * Para que a soma dos valores totais de cada um dos registros referentes a uma mesma nota fiscal seja igual ao total da nota fiscal associada, diversos valores de cada item devem ser considerados.
	 * Este m�todo considera os valores de cada item e retorna o valor representativo do item ao total da nota fiscal. 
	 *  
	 * @param item Item a ser calculado.
	 * @return Valor representativo do item no total da nota fiscal.
	 */
	public static Float obterValorItemNota(ItemNota item) {

		Float valorTotal = item.getValorTotal() + item.getValorFrete() + item.getValorOutros() +item.getValorSeguro() - item.getValorDesconto();
		
		if(item.getIpi() != null)
			valorTotal += item.getIpi().getValor();
		
		if(item.getIcmsst() != null)
			valorTotal += item.getIcmsst().getValor();
		
		return valorTotal;
	}

}
