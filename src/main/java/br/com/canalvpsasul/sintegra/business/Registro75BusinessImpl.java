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
		
		/*
		 * TODO SINTEGRA Registro 75  - Configuração da empresa (Contribuinte do IPI?)
		 * 
		 * Caso seja contribuinte do IPI, então devemos buscar na tabela TIPI o valor da alíquota do IPI com base no NCM do produto.
		 * Caso contrário, informar alíquota 0
		 *  
		 * */
		registro75.setAliquotaIpi(new Double(0)); 	

		/*
		 * TODO SINTEGRA Registro 75 - Gerar conforme estado
		 * 
		 * PR 18%
		 * SC 17%
		 * RS 17% 
		 * */
		registro75.setAliquotaIcms(new Double(0)); 
		
		/*
		 * TODO SINTEGRA Registro 75 - Gerar com base na última entrada do item
		 * 
		 * Obter a última entrada do item e verificar se ele veio com ST.
		 * Caso tenha ST, dividir a base aplicada no item na entrada pela quantidade.
		 * Caso contrário, zerar o campo.
		 * */
		registro75.setBaseCalculoIcmsST(new Double(0)); 
		
		
		/*
		 * TODO SINTEGRA (LIMITAÇÃO) - Colocar como risco (limitação) do mecanismo. Se surgir um cliente que tenha beneficio de redução, então deveremos customizar na API do VPSA para retornar essa info. 
		 * */
		registro75.setPercentualReducaoBaseCalculoIcms(new Double(0));
		
		registro75.setCodigoProduto(produto.getVpsaId().toString());
		registro75.setDataInicial(sintegra.getRegistro10().getDataInicial());
		registro75.setDataFinal(sintegra.getRegistro10().getDataFinal());
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
