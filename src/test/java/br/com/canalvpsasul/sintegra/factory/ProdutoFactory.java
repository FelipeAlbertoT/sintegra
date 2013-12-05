package br.com.canalvpsasul.sintegra.factory;

import java.util.Date;
import java.util.Random;

import br.com.canalvpsasul.vpsabusiness.entities.operacional.Produto;
import br.com.canalvpsasul.vpsapi.entity.operacional.MetodoControle;

public class ProdutoFactory {

	public static Produto gerarProduto() {
		
		Random random = new Random();
		Produto produto = new Produto();
		
		produto.setAtivo(true);
		produto.setVpsaId(random.nextLong());
		produto.setDataAlteracao(new Date());
		produto.setDescricao("BOTÃO CALÇA JEANS");
		produto.setEspecificacao("Botão utilizado para fechar a calça Jeans");
		produto.setCodigoBarras("11111111111111");
		produto.setCodigoInterno("1");
		produto.setCodigoSistema("1");
		produto.setUnidade("UN");
		//produto.setCodigoNcm("61012000");
		produto.setMetodoControle(MetodoControle.ESTOCAVEL);
		produto.setPermiteVenda(true);
		produto.setCustoReferencial(new Float(0.68));
		produto.setPreco(new Float(0.68));
		produto.setDescontoMaximo(new Float(0));
		produto.setComissao(new Float(0));
		produto.setMargemLucro(new Float(140));
		produto.setQuantidadeEmEstoque(new Long(100));
		produto.setValorTotalEmEstoque(new Float(68));
		
		return produto;
	}
	
}
