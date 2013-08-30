package br.com.canalvpsasul.sintegra.business;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.canalvpsasul.sintegra.entities.CombinacaoCfopIcms;
import br.com.canalvpsasul.sintegra.entities.HeaderRegistro50;
import br.com.canalvpsasul.sintegra.entities.Informante;
import br.com.canalvpsasul.sintegra.helper.ConversionUtils;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.EmpresaBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.TerceiroBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.UserBusiness;
import br.com.canalvpsasul.vpsabusiness.business.fiscal.NotaConsumoBusiness;
import br.com.canalvpsasul.vpsabusiness.business.fiscal.NotaMercadoriaBusiness;
import br.com.canalvpsasul.vpsabusiness.business.operacional.ProdutoBusiness;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Portal;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Terceiro;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ItemNota;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaConsumo;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;
import coffeepot.br.sintegra.Sintegra;
import coffeepot.br.sintegra.registros.Registro10;
import coffeepot.br.sintegra.registros.Registro11;
import coffeepot.br.sintegra.registros.Registro50;
import coffeepot.br.sintegra.registros.Registro54;
import coffeepot.br.sintegra.tipos.Convenio;
import coffeepot.br.sintegra.tipos.DocumentoFiscal;
import coffeepot.br.sintegra.tipos.FinalidadeArquivo;
import coffeepot.br.sintegra.tipos.NaturezaOperacao;
import coffeepot.br.sintegra.writer.SintegraWriter;

@Service  
@Transactional
public class SintegraBusinessImpl implements SintegraBusiness {

	@Autowired 
	private EmpresaBusiness empresaBusiness;
	
	@Autowired
	private InformanteBusiness informanteBusiness;
	
	@Autowired
	private UserBusiness userBusiness;
	
	@Autowired
	private IcmsBusiness icmsBusiness;
	
	@Autowired
	private ProdutoBusiness produtoBusiness;
	
	@Autowired
	private TerceiroBusiness terceiroBusiness;
	
	@Autowired
	private NotaMercadoriaBusiness notasMercadoriaBusiness;
	
	@Autowired
	private NotaConsumoBusiness notasConsumoBusiness;
	
	@Override
	public String gerarSintegra(Empresa empresa, Date dataInicial, Date dataFinal, FinalidadeArquivo finalidadeArquivo, NaturezaOperacao naturezaOperacao) throws Exception {
		
		syncRegistros();
		
		OutputStream oStream = generateOutputStream();
	    Writer fw = new BufferedWriter(new OutputStreamWriter(oStream, "ISO-8859-1"));       
	    
	    SintegraWriter sintegraWriter = new SintegraWriter(fw);

		Sintegra sintegra = new Sintegra();
		
		sintegra.setRegistro10(gerarRegistro10(empresa, dataInicial, dataFinal, finalidadeArquivo, naturezaOperacao));
		sintegra.setRegistro11(gerarRegistro11(empresa));
				
		gerarRegistros50(sintegra, dataInicial, dataFinal);
		
		sintegra.gerarRegistros90();
		
	    sintegraWriter.write(sintegra);  
	    sintegraWriter.writerFlush();
	    sintegraWriter.writerClose();
	    
	    return oStream.toString();
	}
	
	private void syncRegistros() throws Exception {
		
		Portal portal = userBusiness.getCurrent().getPortal();
		
		try {
			terceiroBusiness.syncTerceiros(portal);
		}
		catch(Exception e) {
			throw new Exception("Ocorreu um erro ao sincronizar os dados de Terceiros da VPSA.", e);
		}
		
		try {
			produtoBusiness.syncProdutos(portal);
		}
		catch(Exception e) {
			throw new Exception("Ocorreu um erro ao sincronizar os dados de Produtos da VPSA.", e);
		}
		
		try {
			notasMercadoriaBusiness.syncEntities(portal);
		}
		catch(Exception e) {
			throw new Exception("Ocorreu um erro ao sincronizar os dados de Notas de mercadorias da VPSA.", e);
		}
		
		try {
			notasConsumoBusiness.syncEntities(portal);
		}
		catch(Exception e) {
			throw new Exception("Ocorreu um erro ao sincronizar os dados de Notas de consumo da VPSA.", e);
		}
		
	}
	
	private OutputStream generateOutputStream() {
		return new OutputStream()
	    {
	        private StringBuilder string = new StringBuilder();
	        @Override
	        public void write(int b) throws IOException {
	            this.string.append((char) b );
	        }

	        @Override
	        public String toString(){
	            return this.string.toString();
	        }
	    };
	} 
	
	private Registro10 gerarRegistro10(Empresa empresa, Date dataInicial, Date dataFinal, FinalidadeArquivo finalidadeArquivo, NaturezaOperacao naturezaOperacao) throws Exception {
		
		Portal portal = userBusiness.getCurrent().getPortal();
		
		Registro10 registro10 = new Registro10();
		
		registro10.setCnpj(empresa.getDocumento());
		registro10.setRazaoSocial(empresa.getNome());
		registro10.setCodigoConvenio(Convenio.CONV_1_5795_3002);
		registro10.setDataInicial(dataInicial);
		registro10.setDataFinal(dataFinal);
		registro10.setFinalidadeArquivo(finalidadeArquivo);
		registro10.setNaturezaOperacao(naturezaOperacao);
		
		Terceiro empresaTerceiro = terceiroBusiness.findByDocumento(portal, empresa.getDocumento());
		if(empresaTerceiro == null) 
			throw new Exception("O usuário logado não tem privilégios suficientes para obter todas as informações necessárias na VPSA.");
		
		if(empresaTerceiro.getEnderecos().size() == 0)
			throw new Exception("Não existe endereço configurado no VPSA para a empresa informada. Verifique o cadastro de terceiros da VPSA e atualize o registro.");
		
		registro10.setCidade(empresaTerceiro.getEnderecos().get(0).getCidade());
		registro10.setUf(empresaTerceiro.getEnderecos().get(0).getSiglaEstado());
		
		registro10.setIe(""); // TODO SINTEGRA REGISTRO 10 Solicitado na API VPSA de terceiros.
		
		registro10.setFax("");
			
		return registro10;
	}
	
	private Registro11 gerarRegistro11(Empresa empresa) {
		
		Informante informante = informanteBusiness.getInformantePorEmpresa(empresa);
		
		Registro11 registro11 = new Registro11();
		
		registro11.setBairro(informante.getBairro());
		registro11.setCep(informante.getCep());
		registro11.setComplemento(informante.getComplemento());
		registro11.setLogradouro(informante.getLogradouro());
		registro11.setNumero(informante.getNumero());
		registro11.setResponsavel(informante.getNome());
		registro11.setTelefone(informante.getTelefone());
				
		return registro11;
	}
	
	private void gerarRegistros50(Sintegra sintegra, Date dataInicial, Date dataFinal) {
		
		sintegra.setRegistros50(new ArrayList<Registro50>());
		
		Portal portal = userBusiness.getCurrent().getPortal();
				
		List<NotaConsumo> notasConsumo = notasConsumoBusiness.findByDate(portal, dataInicial, dataFinal);
		List<NotaMercadoria> notasMercadoria = notasMercadoriaBusiness.findByDate(portal, dataInicial, dataFinal);
		
		for(NotaConsumo nota : notasConsumo) {
			gerarRegistro50(sintegra, nota);
		}
		
		for(NotaMercadoria nota : notasMercadoria) {
			gerarRegistro50(sintegra, nota);
		}
	}	
	
	private void gerarRegistro50(Sintegra sintegra, NotaMercadoria nota) {
		
		HeaderRegistro50 header = new HeaderRegistro50(nota.getStatus(), nota.getTerceiroRemetente(), nota.getTerceiroDestinatario(), nota.getTipo());
		
		ArrayList<CombinacaoCfopIcms> combinacoes = new ArrayList<CombinacaoCfopIcms>();
		ArrayList<Registro54> registros54 = new ArrayList<Registro54>();

		int count = 1;
		for (ItemNota item: nota.getItens()) {
			
			CombinacaoCfopIcms combinacao = null;
			for(CombinacaoCfopIcms comb : combinacoes) {
				if(item.getCfop().equals(comb.getCfop()) && item.getIcms().getAliquota().equals(comb.getAliquota())) {
					combinacao = comb;
					break;
				}				
			}
			
			if(combinacao == null) {
				combinacao = new CombinacaoCfopIcms();
				combinacao.setAliquota(item.getIcms().getAliquota());
				combinacao.setCfop(item.getCfop());
				combinacoes.add(combinacao);
			}			
			
			//TODO Verificar se as alterações aqui estão alterando os valores do item na coleção.
			
			combinacao.setBaseIcms(combinacao.getBaseIcms() + item.getIcms().getBase());
			combinacao.setValorIcms(combinacao.getValorIcms() + item.getIcms().getValor());
			combinacao.setValorTotal(combinacao.getValorTotal() + item.getValorTotal());
			combinacao.setOutras(combinacao.getOutras() + icmsBusiness.getValorOutros(item.getIcms(), item.getValorTotal()));
			combinacao.setIsentaNaoTribut(combinacao.getIsentaNaoTribut() + icmsBusiness.getValorIsentoNaoTributado(item.getIcms(), item.getValorTotal()));
			
			// TODO SINTEGRA REGISTRO 50 Aguardar a melhoria na API VPSA para obter o desconto.
			Double descontoItemDouble = getDescontoItem(((float) 0), nota.getValorTotal(), item.getValorTotal());
			registros54.add(gerarRegistro54(item, header.getCnpj(), count++, header.getModeloDocumentoFiscal(), nota.getNumero(), header.getSerie(), descontoItemDouble));
		}
		
		for(CombinacaoCfopIcms comb : combinacoes) {
			
			Registro50 registro50 = new Registro50();
			
			registro50.setAliquotaIcms(ConversionUtils.fromFloat(comb.getAliquota()));
			registro50.setBaseDeCalculoIcms(ConversionUtils.fromFloat(comb.getBaseIcms()));
			registro50.setCfop(Integer.parseInt(comb.getCfop().toString()));
			registro50.setEmitente(header.getEmitente());
			registro50.setCpfCnpj(header.getCnpj());
			registro50.setUf(header.getUf());	
			registro50.setIe(header.getIe()); 
			registro50.setModeloDocumento(header.getModeloDocumentoFiscal());
			
			registro50.setDataDocumento(nota.getData());
			registro50.setNumeroDocumento(nota.getNumero());
			registro50.setRegistros54(registros54);
			registro50.setSerieDocumento(header.getSerie());
			registro50.setSituacaoDocumento(header.getSituacao());
			registro50.setValorIcms(ConversionUtils.fromFloat(comb.getValorIcms()));
			registro50.setValorIsentas(ConversionUtils.fromFloat(comb.getIsentaNaoTribut()));
			registro50.setValorOutras(ConversionUtils.fromFloat(comb.getOutras()));
			registro50.setValorTotal(ConversionUtils.fromFloat(comb.getValorTotal()));
			
			sintegra.getRegistros50().add(registro50);
		}
	}	
	
	private Registro54 gerarRegistro54(ItemNota item, String cpfCnpj, int nrItem, DocumentoFiscal modeloDocumentoFiscal, long numeroNota, String serie, Double descontoItem) {
		
		Registro54 registro54 = new Registro54();
		
		registro54.setAliquotaIcms(ConversionUtils.fromFloat(item.getIcms().getAliquota()));
		registro54.setBasedeCalculoIcms(ConversionUtils.fromFloat(item.getIcms().getBase()));
		
		registro54.setBaseDeCalculoIcmsST(ConversionUtils.fromFloat(item.getIcmsst().getBase()));
		registro54.setCfop(Integer.parseInt(String.valueOf(item.getCfop())));
		registro54.setCodigoProduto(item.getProduto().getVpsaId().toString());
		registro54.setCpfCnpj(cpfCnpj);
		registro54.setCst(item.getIcms().getCst());
		registro54.setModeloDocumento(modeloDocumentoFiscal);
		registro54.setNumeroDocumento(Integer.parseInt(String.valueOf(numeroNota)));
		registro54.setNumeroItem(nrItem);
		registro54.setQuantidade(ConversionUtils.fromFloat(item.getQuantidade()));
		registro54.setSerieDocumento(serie);
		registro54.setValorBrutoItem(ConversionUtils.fromFloat(item.getQuantidade() * item.getValorUnitario()));
		registro54.setValorDesconto(descontoItem);
		if(item.getIpi() != null)
			registro54.setValorIpi(ConversionUtils.fromFloat(item.getIpi().getValor()));
		
		return registro54;
	}
	
	private void gerarRegistro50(Sintegra sintegra, NotaConsumo nota) {
		
		HeaderRegistro50 header = new HeaderRegistro50(nota.getStatus(), nota.getTerceiroRemetente(), nota.getTerceiroDestinatario(), nota.getTipo());
		
		Registro50 registro50 = new Registro50();
		
		registro50.setAliquotaIcms(ConversionUtils.fromFloat(nota.getIcms().getAliquota()));
		registro50.setBaseDeCalculoIcms(ConversionUtils.fromFloat(nota.getIcms().getBase()));
		registro50.setCfop(Integer.parseInt(nota.getCfop().toString()));
		registro50.setEmitente(header.getEmitente());
		registro50.setCpfCnpj(header.getCnpj());
		registro50.setUf(header.getUf());	
		registro50.setIe(header.getIe()); 
		registro50.setModeloDocumento(header.getModeloDocumentoFiscal());
		
		registro50.setDataDocumento(nota.getData());
		registro50.setNumeroDocumento(nota.getNumero());
		registro50.setSerieDocumento(header.getSerie());
		registro50.setSituacaoDocumento(header.getSituacao());
		registro50.setValorIcms(ConversionUtils.fromFloat(nota.getIcms().getValor()));
		registro50.setValorIsentas(ConversionUtils.fromFloat(icmsBusiness.getValorIsentoNaoTributado(nota.getIcms(), nota.getValorTotal())));
		registro50.setValorOutras(ConversionUtils.fromFloat(icmsBusiness.getValorOutros(nota.getIcms(), nota.getValorTotal())));
		
		registro50.setValorTotal(ConversionUtils.fromFloat(nota.getValorTotal()));
		
		sintegra.getRegistros50().add(registro50);
		
	}
	
	private Double getDescontoItem(Float descontoNota, Float valorNota, Float valorItem) {
		return ConversionUtils.fromFloat((valorItem/valorNota * descontoNota));
	}
}
