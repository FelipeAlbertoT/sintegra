package br.com.canalvpsasul.sintegra.business;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.canalvpsasul.sintegra.helper.ConversionUtils;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.operacional.Produto;
import coffeepot.br.sintegra.registros.Registro74;
import coffeepot.br.sintegra.tipos.CodigoDePosse;

@Service
public class Registro74BusinessImpl implements Registro74Business {
	
	@Override			
	public Registro74 obterRegistro74(Produto produto, Empresa empresa) {
		
		String uf = "";
		if(empresa.getTerceiro().getEnderecos().size() > 0) 
			uf = empresa.getTerceiro().getEnderecos().get(0).getSiglaEstado();
		
		Registro74 registro74 = new Registro74();
		
		registro74.setCnpjPossuidor("0"); 
		registro74.setCodigoPosse(CodigoDePosse.PROPRIEDADE_INFORMANTE_POSSE_INFORMATANTE);
		registro74.setCodigoProduto(produto.getVpsaId().toString());
		registro74.setDataInventario(new Date()); // TODO SINTEGRA Registro 74 - Verificar como funciona a questão da data do inventário.
		registro74.setIePossuidor("");
		registro74.setQuantidade(Double.valueOf(produto.getQuantidadeEmEstoque().toString()));		
		registro74.setUfPossuidor(uf);
		registro74.setValorTotalProduto(ConversionUtils.fromFloat(produto.getValorTotalEmEstoque()));		
		
		return registro74;
	}

}
