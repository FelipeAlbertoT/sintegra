package br.com.canalvpsasul.sintegra.business;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.operacional.Produto;
import coffeepot.br.sintegra.registros.Registro74;
import coffeepot.br.sintegra.tipos.CodigoDePosse;

@Service
public class Registro74BusinessImpl implements Registro74Business {
	
	@Override			
	public Registro74 obterRegistro74(Produto produto, Empresa empresa, Date dataInventario) {
		
		String uf = "";
		if(empresa.getTerceiro().getEnderecos().size() > 0) 
			uf = empresa.getTerceiro().getEnderecos().get(0).getSiglaEstado();
		
		Registro74 registro74 = new Registro74();
		
		registro74.setCnpjPossuidor("0"); 
		registro74.setCodigoPosse(CodigoDePosse.PROPRIEDADE_INFORMANTE_POSSE_INFORMATANTE);
		registro74.setCodigoProduto(produto.getVpsaId().toString());
		
		registro74.setDataInventario(dataInventario); 
		registro74.setIePossuidor("");
		
		if(produto.getQuantidadeEmEstoque() < 0)
			registro74.setQuantidade(new Double(0));
		else
			registro74.setQuantidade(new Double(produto.getQuantidadeEmEstoque()));
		
		registro74.setUfPossuidor(uf);
		registro74.setValorTotalProduto(new Double(produto.getValorTotalEmEstoque()));		
		
		return registro74;
	}
}
