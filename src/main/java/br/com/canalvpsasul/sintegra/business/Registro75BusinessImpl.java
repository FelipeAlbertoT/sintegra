package br.com.canalvpsasul.sintegra.business;

import org.springframework.stereotype.Service;

import br.com.canalvpsasul.vpsabusiness.entities.operacional.Produto;
import coffeepot.br.sintegra.Sintegra;
import coffeepot.br.sintegra.registros.Registro75;

@Service
public class Registro75BusinessImpl implements Registro75Business {

	@Override
	public void addRegistro75(Produto produto, Sintegra sintegra) {

		if(checkExists(sintegra, produto))
			return;
		
		Registro75 registro75 = new Registro75();
		
		registro75.setAliquotaIcms(new Double(0)); // TODO SINTEGRA Registro 75 - verificar como vamos resolver
		registro75.setAliquotaIpi(new Double(0)); // TODO SINTEGRA Registro 75 - verificar como vamos resolver
		registro75.setBaseCalculoIcmsST(new Double(0)); // TODO SINTEGRA Registro 75 - verificar como vamos resolver
		registro75.setPercentualReducaoBaseCalculoIcms(new Double(0)); // TODO SINTEGRA Registro 75 - verificar como vamos resolver
		
		registro75.setCodigoProduto(produto.getVpsaId().toString());
		registro75.setDataInicial(sintegra.getRegistro10().getDataInicial());
		registro75.setDataInicial(sintegra.getRegistro10().getDataFinal());
		registro75.setDescricaoProduto(produto.getDescricao());
		registro75.setNcm(produto.getCodigoNcm());
		registro75.setUnidade(produto.getUnidade());
		
		sintegra.getRegistros75().add(registro75);
	}
	
	private Boolean checkExists(Sintegra sintegra, Produto produto) {
		for(Registro75 registro75 : sintegra.getRegistros75()) {
			if(registro75.getCodigoProduto().equals(produto.getVpsaId())) {
				return true;
			}			
		}
		return false;
	}
}
