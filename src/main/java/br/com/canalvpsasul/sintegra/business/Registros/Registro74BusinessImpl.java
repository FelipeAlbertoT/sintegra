package br.com.canalvpsasul.sintegra.business.Registros;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.canalvpsasul.vpsabusiness.business.operacional.SaldoMercadoriaBusiness;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Entidade;
import br.com.canalvpsasul.vpsabusiness.entities.operacional.Produto;
import coffeepot.br.sintegra.registros.Registro74;
import coffeepot.br.sintegra.tipos.CodigoDePosse;

@Service
public class Registro74BusinessImpl implements Registro74Business {
	
	@Autowired
	private SaldoMercadoriaBusiness saldoMercadoriaBusiness;
	
	@Override			
	public Registro74 obterRegistro74(Produto produto, Empresa empresa, Date dataInventario, List<Entidade> entidades) throws Exception {
		
		String uf = "";
		if(empresa.getTerceiro().getEnderecos().size() > 0) 
			uf = empresa.getTerceiro().getEnderecos().get(0).getMunicipio().getSiglaEstado();
		
		Registro74 registro74 = new Registro74();
		
		Float saldoMercadoria = 0F;
		try {
			saldoMercadoria = saldoMercadoriaBusiness.getSaldoByDate(produto, dataInventario, entidades);
		}
		catch (Exception e) {
			saldoMercadoria = 0F;
		}
		
		/*
		 * TODO SINTEGRA (LIMITAÇÃO) - Colocar como risco (limitação) do mecanismo. Seguindo limitações do ERP, consideramos como possuidor da mercadoria sempre o PROPRIEDADE_INFORMANTE_POSSE_INFORMATANTE.
		 * */
		registro74.setCnpjPossuidor("0"); 
		registro74.setIePossuidor("");
		registro74.setCodigoPosse(CodigoDePosse.PROPRIEDADE_INFORMANTE_POSSE_INFORMATANTE);
		registro74.setUfPossuidor(uf);		
		registro74.setCodigoProduto(produto.getVpsaId().toString());
		registro74.setDataInventario(dataInventario); 
		registro74.setQuantidade(new Double(saldoMercadoria));
		registro74.setValorTotalProduto(new Double(saldoMercadoria * produto.getPreco()));		
		
		return registro74;
	}
}
