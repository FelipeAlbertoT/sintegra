package br.com.canalvpsasul.sintegra.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.canalvpsasul.sintegra.entities.CombinacaoCfop;
import br.com.canalvpsasul.sintegra.entities.CombinacaoCfopIcms;
import br.com.canalvpsasul.sintegra.entities.CombinacaoCfopIpi;
import br.com.canalvpsasul.sintegra.helper.RateioNotaUtils;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ItemNota;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;

@Service
public class CombinacaoCfopBusinessImpl implements CombinacaoCfopBusiness {

	@Autowired
	private IcmsBusiness icmsBusiness;
	
	@Autowired
	private IpiBusiness ipiBusiness;
	
	@Override
	public List<CombinacaoCfopIcms> obterCombinacoesCfopIcmsDocumento(
			NotaMercadoria nota) {

		ArrayList<CombinacaoCfopIcms> combinacoes = new ArrayList<CombinacaoCfopIcms>();

		for (ItemNota item : nota.getItens()) {

			CombinacaoCfopIcms combinacao = null;
			for (CombinacaoCfopIcms comb : combinacoes) {
				if (item.getCfop().equals(comb.getCfop())
						&& item.getIcms().getAliquota()
								.equals(comb.getAliquota())) {
					combinacao = comb;
					break;
				}
			}

			if (combinacao == null) {
				combinacao = new CombinacaoCfopIcms();
				combinacao.setAliquota(item.getIcms().getAliquota());
				combinacao.setCfop(item.getCfop());
			}

			combinacao.setBaseIcms(combinacao.getBaseIcms()
					+ item.getIcms().getBase());
			combinacao.setValorIcms(combinacao.getValorIcms()
					+ item.getIcms().getValor());

			combinacao.setValorIcmsSt(combinacao.getValorIcmsSt()
					+ item.getIcmsst().getValor());
			combinacao.setValorIcmsSt(combinacao.getValorIcmsSt()
					+ item.getIcmsst().getValor());

			combinacao.setValorTotal(combinacao.getValorTotal() + RateioNotaUtils.obterValorItemNota(item));
			
			combinacao.setOutras(combinacao.getOutras()
					+ icmsBusiness.getValorOutros(item.getIcms(),
							item.getValorTotal()));
			combinacao.setIsentaNaoTribut(combinacao.getIsentaNaoTribut()
					+ icmsBusiness.getValorIsentoNaoTributado(item.getIcms(),
							item.getValorTotal()));
			
			combinacoes.remove(combinacao);
			combinacoes.add(combinacao);
		}
		
		if(combinacoes.size() == 1)
			combinacoes.get(0).setValorIcms(nota.getValorTotal());		

		return combinacoes;

	}

	@Override
	public List<CombinacaoCfopIpi> obterCombinacoesCfopIpiDocumento(NotaMercadoria nota) {

		ArrayList<CombinacaoCfopIpi> combinacoes = new ArrayList<CombinacaoCfopIpi>();

		for (ItemNota item : nota.getItens()) {

			if (item.getIpi() == null || item.getIpi().getAliquota() == 0)
				continue;

			CombinacaoCfopIpi combinacao = null;
			for (CombinacaoCfopIpi comb : combinacoes) {
				if (item.getCfop().equals(comb.getCfop())) {
					combinacao = comb;
					break;
				}
			}

			if (combinacao == null) {
				combinacao = new CombinacaoCfopIpi();
				combinacao.setCfop(item.getCfop());
			}
			
			combinacao.setValorIpi(combinacao.getValorIpi()
					+ item.getIpi().getValor());
			combinacao.setOutras(combinacao.getOutras()
					+ ipiBusiness.getValorOutros(item.getIcms(),
							item.getValorTotal()));
			combinacao.setIsentaNaoTribut(combinacao.getIsentaNaoTribut()
					+ ipiBusiness.getValorIsentoNaoTributado(item.getIcms(),
							item.getValorTotal()));
			
			combinacoes.remove(combinacao);
			combinacoes.add(combinacao);
		}

		return combinacoes;
	}

	@Override
	public List<CombinacaoCfop> obterCombinacoesCfopDocumento(
			NotaMercadoria nota) {
		
		ArrayList<CombinacaoCfop> combinacoes = new ArrayList<CombinacaoCfop>();

		for (ItemNota item : nota.getItens()) {

			CombinacaoCfop combinacao = null;
			for (CombinacaoCfop comb : combinacoes) {
				if (item.getCfop().equals(comb.getCfop())) {
					combinacao = comb;
					break;
				}
			}

			if (combinacao == null) {
				combinacao = new CombinacaoCfop();
				combinacao.setCfop(item.getCfop());
			}
			
			combinacao.setValorIcmsSt(combinacao.getValorIcmsSt()
					+ item.getIcmsst().getValor());
			combinacao.setBaseIcmsSt(combinacao.getBaseIcmsSt()
					+ item.getIcmsst().getBase());
			
			combinacao.setOutras(combinacao.getOutras()
					+ ipiBusiness.getValorOutros(item.getIcms(),
							item.getValorTotal()));
			combinacao.setIsentaNaoTribut(combinacao.getIsentaNaoTribut()
					+ ipiBusiness.getValorIsentoNaoTributado(item.getIcms(),
							item.getValorTotal()));
			
			combinacoes.remove(combinacao);
			combinacoes.add(combinacao);
		}

		return combinacoes;
	}
}
