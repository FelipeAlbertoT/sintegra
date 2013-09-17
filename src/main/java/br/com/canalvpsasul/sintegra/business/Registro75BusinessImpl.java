package br.com.canalvpsasul.sintegra.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.canalvpsasul.vpsabusiness.business.fiscal.NotaMercadoriaBusiness;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ItemNota;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;
import br.com.canalvpsasul.vpsabusiness.entities.operacional.Produto;
import coffeepot.br.sintegra.Sintegra;
import coffeepot.br.sintegra.registros.Registro75;

@Service
public class Registro75BusinessImpl implements Registro75Business {

	@Autowired
	private NotaMercadoriaBusiness notaMercadoriaBusiness;
	
	@Override
	public void addRegistro75(Produto produto, Sintegra sintegra, Empresa empresa) {

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
		 * Gerar conforme estado (No momento não temos como obter essa informação do sistema)
		 * Então pegamos a alíquota interna e consideramos ela.
		 * 
		 * PR 18%
		 * SC 17%
		 * RS 17% 
		 * */		
		if(empresa.getTerceiro().getEnderecos().get(0).getSiglaEstado().equalsIgnoreCase("pr"))
			registro75.setAliquotaIcms(new Double(18));
		else if(empresa.getTerceiro().getEnderecos().get(0).getSiglaEstado().equalsIgnoreCase("sc"))
			registro75.setAliquotaIcms(new Double(17));
		else if(empresa.getTerceiro().getEnderecos().get(0).getSiglaEstado().equalsIgnoreCase("rs"))
			registro75.setAliquotaIcms(new Double(17));
		
		/*
		 * Obter a última entrada do item e verificar se ele veio com ST.
		 * Caso tenha ST, dividir a base aplicada no item na entrada pela quantidade.
		 * Caso contrário, zerar o campo.
		 * */
		NotaMercadoria nota = notaMercadoriaBusiness.obterUltimaNotaEntradaProduto(empresa, produto);
		registro75.setBaseCalculoIcmsST(new Double(0));
		if(nota != null) {			
			for(ItemNota item :nota.getItens()) {
				if(item.getProduto().equals(produto)){
					
					if(item.getIcmsst() != null)
						registro75.setBaseCalculoIcmsST(new Double(item.getIcmsst().getBase()));
					
					break;
				}
			}			
		}
			
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
