package br.com.canalvpsasul.sintegra.factory;

import br.com.canalvpsasul.vpsabusiness.entities.fiscal.Imposto;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ItemNota;
import br.com.canalvpsasul.vpsabusiness.entities.operacional.Produto;

public class ItemNotaFactory {

	public static ItemNota gerarItemNotaComStIpiIcms() {
		
		ItemNota itemNota = new ItemNota(); 
		
		itemNota.setCfop(new Long(5401));
		itemNota.setValorUnitario(new Float(0.68));		
		itemNota.setQuantidade(new Float(1000));
		itemNota.setValorFrete(new Float(48));
		itemNota.setValorOutros(new Float(0));
		itemNota.setValorSeguro(new Float(0));
		itemNota.setValorTotal(new Float(itemNota.getQuantidade() * itemNota.getValorUnitario()));
		
		Produto produto = ProdutoFactory.gerarProduto();
		itemNota.setProduto(produto);
		
		Imposto icms = new Imposto();
		icms.setCst("040");
		icms.setAliquota(new Float(18));
		icms.setBase(new Float(itemNota.getValorTotal()));
		icms.setValor(new Float(icms.getBase() * icms.getAliquota() / 100));
		itemNota.setIcms(icms);
		
		Imposto ipi = new Imposto(); 
		ipi.setAliquota(Float.valueOf(40));
		ipi.setCst("050");
		ipi.setBase(new Float(itemNota.getValorTotal()));
		ipi.setValor(new Float(ipi.getBase() * ipi.getAliquota() / 100));
		itemNota.setIpi(ipi);
		
		Float baseSt = new Float(itemNota.getQuantidade() * itemNota.getValorUnitario());
		baseSt += ipi.getValor();
		baseSt += itemNota.getValorFrete();
		baseSt = baseSt + (produto.getMargemLucro() * baseSt / 100);
		
		Float valorSt = baseSt * icms.getAliquota() / 100;
		valorSt -= icms.getValor();
		
		Imposto icmsSt = new Imposto();
		icmsSt.setAliquota(new Float(18));
		icmsSt.setValor(valorSt);
		icmsSt.setBase(baseSt);
		itemNota.setIcmsst(icmsSt);
		
		return itemNota;
	}
	
}
