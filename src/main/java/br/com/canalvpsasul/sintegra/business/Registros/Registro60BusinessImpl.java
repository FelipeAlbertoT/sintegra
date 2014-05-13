package br.com.canalvpsasul.sintegra.business.Registros;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.canalvpsasul.vpsabusiness.entities.fiscal.CupomFiscal;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ItemNota;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ReducaoZ;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.TotalizadorReducao;
import coffeepot.br.sintegra.registros.Registro60A;
import coffeepot.br.sintegra.registros.Registro60M;
import coffeepot.br.sintegra.registros.Registro60R;

@Service
public class Registro60BusinessImpl implements Registro60Business {

	@Override
	public Registro60M obterRegistro60M(ReducaoZ reducao) throws ParseException {

		Registro60M registro60m = new Registro60M();
				
		registro60m.setCooFinal(Integer.valueOf(reducao.getCooFinal().toString()));
		registro60m.setCooInicial(Integer.valueOf(reducao.getCooInicial().toString()));
		registro60m.setCro(Integer.valueOf(reducao.getCro().toString()));
		registro60m.setCrz(Integer.valueOf(reducao.getCrz().toString()));
		registro60m.setDataEmissao(reducao.getDataReducaoZ());
		registro60m.setModeloDocumento("2D");
		registro60m.setNumeroOrdem(reducao.getNumeroSequencialECF());
		registro60m.setNumeroSerieEquipamento(reducao.getNumeroSerieECF());
		registro60m.setValorGT(new Double(reducao.getTotalizadorGeral())); 
		registro60m.setVendaBruta(new Double(reducao.getVendaBrutaDiaria()));
		registro60m.setRegistros60A(new ArrayList<Registro60A>());
		registro60m.getRegistros60A().addAll(obterRegistro60A(reducao));
		
		return registro60m;
	}
	
	@Override
	@Deprecated
	public List<Registro60A> obterRegistro60A(List<CupomFiscal> cuponsFiscais) {
		
		ArrayList<Registro60A> registros = new ArrayList<Registro60A>();
		
		Float cancelamentos = new Float(0);
		Float descontos = new Float(0);
		Date dataEmissao = null;
		String numeroSerieEcf = null;
		for(CupomFiscal cf : cuponsFiscais) {
		
			dataEmissao = cf.getData();
			numeroSerieEcf = cf.getNumeroSerieEcf();
			
			if(cf.getCancelado())
				cancelamentos += cf.getValorTotal();
			
			for(ItemNota itemNota : cf.getItens()) {
				if(itemNota.getValorDesconto() > 0) {
					descontos += itemNota.getValorDesconto();
				}
			}
		}
		
		if(cancelamentos > 0) {
			Registro60A registro60a = new Registro60A();
			registro60a.setDataEmissao(dataEmissao);
			registro60a.setNumeroSerieEquipamento(numeroSerieEcf);
			registro60a.setTotalizadorParcial("CANC");
			registro60a.setValorAcumulado(new Double(cancelamentos));
			
			registros.add(registro60a);
		}
		
		if(descontos > 0) {
			Registro60A registro60a = new Registro60A();
			registro60a.setDataEmissao(dataEmissao);
			registro60a.setNumeroSerieEquipamento(numeroSerieEcf);
			registro60a.setTotalizadorParcial("DESC");
			registro60a.setValorAcumulado(new Double(descontos));
			
			registros.add(registro60a);
		}
		
		return registros;
	}
	
	private ArrayList<Registro60A> obterRegistro60A(ReducaoZ reducao) throws ParseException {

		ArrayList<Registro60A> registros = new ArrayList<Registro60A>();
		
		for(TotalizadorReducao totalizadorReducao : reducao.getTotalizadores()) {
			
			if(totalizadorReducao.getValorAcumulado() == 0)
				continue;
			
			Registro60A registro60a = new Registro60A();
			registro60a.setDataEmissao(reducao.getDataReducaoZ());
			registro60a.setNumeroSerieEquipamento(reducao.getNumeroSerieECF());
			
			registro60a.setValorAcumulado(new Double(totalizadorReducao.getValorAcumulado()));
			
			Boolean capturaOk = false;
			if(totalizadorReducao.getCodigo().equals("I1")){
				registro60a.setTotalizadorParcial("I");
				capturaOk = true;
			}
			else if(totalizadorReducao.getCodigo().equals("F1")){
				registro60a.setTotalizadorParcial("F");
				capturaOk = true;
			}
			else if(totalizadorReducao.getCodigo().equals("N1")){
				registro60a.setTotalizadorParcial("N");
				capturaOk = true;
			}
			else if(totalizadorReducao.getCodigo().equals("FS1")){
				registro60a.setTotalizadorParcial("ISS");
				capturaOk = true;
			}
			else if(totalizadorReducao.getCodigo().contains("T")){
				String codigo = totalizadorReducao.getCodigo();
				String[] codigosStrings = codigo.split("T");	
				if(codigosStrings.length > 0) {
					registro60a.setTotalizadorParcial(codigosStrings[1]);
					capturaOk = true;
				}
			}

			if(capturaOk)
				registros.add(registro60a);
		}
		
		
		/*
		 * Como as melhorias na API de redução Z saíram no dia 30/01/2014,
		 * para geração de sintegra de antes dessa data, é necessário
		 * calcular os totalizadores de cancelamento, desconto e iss
		 * mediante avaliação dos cupons do período. Após essa data, os
		 * totalizadores já são preenchidos de acordo com os dados da 
		 * redução. 
		 */
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateFimString = "31-01-2014 00:00:00";
		String dateInicioString = "01-01-2014 00:00:00";
		Date datefim = sdf.parse(dateFimString);
		Date dateInicio = sdf.parse(dateInicioString);

		/*
		 * Regra específica para a Box - Remover após passar a necessidade
		 * Reduções cadastradas manualmente
		 * */
		Boolean corrigirViaCupom = true;
		if(reducao.getPortal().getCnpj().equals("16646228000193") && reducao.getDataMovimento().before(dateInicio)) {
			/*
			 * Na box nesse perído, as reduções foram cadastradas manualmente, então deve pegar da redução os cancelamentos e descontos de ICMS.
			 * */
			corrigirViaCupom = false;
		}
		
		if (reducao.getDataReducaoZ().before(datefim) && corrigirViaCupom)
			return registros;
		
		if(reducao.getCancelamentoICMS() > 0) {
			Registro60A registro60a = new Registro60A();
			registro60a.setDataEmissao(reducao.getDataReducaoZ());
			registro60a.setNumeroSerieEquipamento(reducao.getNumeroSerieECF());
			registro60a.setTotalizadorParcial("CANC");
			registro60a.setValorAcumulado(new Double(reducao.getCancelamentoICMS()));
			
			registros.add(registro60a);
		}
		
		if(reducao.getDescontoICMS() > 0) {
			Registro60A registro60a = new Registro60A();
			registro60a.setDataEmissao(reducao.getDataReducaoZ());
			registro60a.setNumeroSerieEquipamento(reducao.getNumeroSerieECF());
			registro60a.setTotalizadorParcial("DESC");
			registro60a.setValorAcumulado(new Double(reducao.getDescontoICMS()));
			
			registros.add(registro60a);
		}
		
		if(reducao.getTotalISS() > 0) {
			Registro60A registro60a = new Registro60A();
			registro60a.setDataEmissao(reducao.getDataReducaoZ());
			registro60a.setNumeroSerieEquipamento(reducao.getNumeroSerieECF());
			registro60a.setTotalizadorParcial("ISS");
			registro60a.setValorAcumulado(new Double(reducao.getTotalISS()));
			
			registros.add(registro60a);
		}
		
		return registros;
	}

	@Override
	public ArrayList<Registro60R> obterRegistro60R(List<CupomFiscal> cuponsFiscais) {

		ArrayList<Registro60R> registros = new ArrayList<Registro60R>();
		String situacaoTributaria = "";
		SimpleDateFormat formatDate = new SimpleDateFormat("MMyyyy");
		
		for(CupomFiscal cf : cuponsFiscais) {
			for(ItemNota itemNota : cf.getItens()) {
				
				if(itemNota.getIcms() != null) {
					
					if(itemNota.getIcms().getCst().equals("00") || itemNota.getIcms().getCst().equals("101")) {
						situacaoTributaria = new Double(itemNota.getIcms().getAliquota() * 100).toString();
						if(situacaoTributaria.length() == 3)
							situacaoTributaria = "0" + situacaoTributaria;
					}
					else if(itemNota.getIcms().getCst().equals("40") || itemNota.getIcms().getCst().equals("103")) {
						situacaoTributaria = "I";
					}
					else if(itemNota.getIcms().getCst().equals("41") || itemNota.getIcms().getCst().equals("400") || itemNota.getIcms().getCst().equals("102")) {
						situacaoTributaria = "N";
					}
					else if(itemNota.getIcms().getCst().equals("60") || itemNota.getIcms().getCst().equals("500")) {
						situacaoTributaria = "F";
					}	
				}
				
				Registro60R registro60r = null;
				Boolean inArray = false;
				for(Registro60R reg : registros) {
					if(reg.getCodigoProduto().equals(itemNota.getProduto().getVpsaId().toString()) && reg.getTotalizadorParcial().equals(situacaoTributaria)) {
						registro60r = reg;
						inArray = true;
						break;
					}
				}
				
				if(registro60r == null) {
					registro60r = new Registro60R();
					registro60r.setBaseDeCalculoIcms(new Double(0));
					registro60r.setQuantidade(new Double(0));
					registro60r.setValorLiquidoProduto(new Double(0));
					registro60r.setTotalizadorParcial(situacaoTributaria);
					registro60r.setCodigoProduto(itemNota.getProduto().getVpsaId().toString());
					registro60r.setMesAno(formatDate.format(cf.getData()));
				}
				
				if(itemNota.getIcms() != null) {
					registro60r.setBaseDeCalculoIcms(registro60r.getBaseDeCalculoIcms() + itemNota.getIcms().getBase());
				}
				
				registro60r.setQuantidade(registro60r.getQuantidade() + itemNota.getQuantidade());
				registro60r.setValorLiquidoProduto(registro60r.getValorLiquidoProduto() + (itemNota.getValorTotal() - itemNota.getValorDesconto()));
				
				if(!inArray)
					registros.add(registro60r);
			}
		}
		

		Registro60R regTemp = null;
		for(int i = 0; i < registros.size() - 1; i++) {
			for(int j = i + 1; j < registros.size(); j++) {
				if(new Long(registros.get(i).getCodigoProduto()) > new Long(registros.get(j).getCodigoProduto())) {
					regTemp = registros.get(i);
					registros.set(i, registros.get(j));
					registros.set(j, regTemp);
				}
			}
		}
		
		return registros;
	}
}
