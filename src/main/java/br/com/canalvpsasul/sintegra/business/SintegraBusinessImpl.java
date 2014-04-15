package br.com.canalvpsasul.sintegra.business;

import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.canalvpsasul.sintegra.business.Imposto.IpiBusiness;
import br.com.canalvpsasul.sintegra.business.Registros.Registro10Business;
import br.com.canalvpsasul.sintegra.business.Registros.Registro11Business;
import br.com.canalvpsasul.sintegra.business.Registros.Registro50Business;
import br.com.canalvpsasul.sintegra.business.Registros.Registro51Business;
import br.com.canalvpsasul.sintegra.business.Registros.Registro53Business;
import br.com.canalvpsasul.sintegra.business.Registros.Registro54Business;
import br.com.canalvpsasul.sintegra.business.Registros.Registro60Business;
import br.com.canalvpsasul.sintegra.business.Registros.Registro70Business;
import br.com.canalvpsasul.sintegra.business.Registros.Registro74Business;
import br.com.canalvpsasul.sintegra.business.Registros.Registro75Business;
import br.com.canalvpsasul.sintegra.entities.Configuracao;
import br.com.canalvpsasul.sintegra.entities.Informante;
import br.com.canalvpsasul.sintegra.entities.SintegraParametros;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.TerceiroBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.UserBusiness;
import br.com.canalvpsasul.vpsabusiness.business.fiscal.ConhecimentoTransporteBusiness;
import br.com.canalvpsasul.vpsabusiness.business.fiscal.CupomFiscalBusiness;
import br.com.canalvpsasul.vpsabusiness.business.fiscal.NotaConsumoBusiness;
import br.com.canalvpsasul.vpsabusiness.business.fiscal.NotaMercadoriaBusiness;
import br.com.canalvpsasul.vpsabusiness.business.fiscal.ReducaoZBusiness;
import br.com.canalvpsasul.vpsabusiness.business.geral.SyncControlBusiness;
import br.com.canalvpsasul.vpsabusiness.business.operacional.ProdutoBusiness;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Entidade;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.User;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ConhecimentoTransporte;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.CupomFiscal;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ItemNota;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaConsumo;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ReducaoZ;
import br.com.canalvpsasul.vpsabusiness.entities.operacional.Produto;
import br.com.canalvpsasul.vpsapi.entity.fiscal.StatusNota;
import br.com.canalvpsasul.vpsapi.entity.fiscal.TipoNota;
import coffeepot.br.sintegra.Sintegra;
import coffeepot.br.sintegra.registros.Registro50;
import coffeepot.br.sintegra.registros.Registro51;
import coffeepot.br.sintegra.registros.Registro53;
import coffeepot.br.sintegra.registros.Registro54;
import coffeepot.br.sintegra.registros.Registro60A;
import coffeepot.br.sintegra.registros.Registro60M;
import coffeepot.br.sintegra.registros.Registro60R;
import coffeepot.br.sintegra.registros.Registro70;
import coffeepot.br.sintegra.registros.Registro74;
import coffeepot.br.sintegra.registros.Registro75;
import coffeepot.br.sintegra.writer.SintegraWriter;

@Service
@Transactional
public class SintegraBusinessImpl implements SintegraBusiness {

	@Autowired
	private InformanteBusiness informanteBusiness;

	@Autowired
	private ConfiguracaoBusiness configuracaoBusiness;

	@Autowired
	private ArmazenamentoSintegra armazenamentoSintegra;

	@Autowired
	private UserBusiness userBusiness;

	@Autowired
	private ProdutoBusiness produtoBusiness;

	@Autowired
	private IpiBusiness ipiBusiness;

	@Autowired
	private TerceiroBusiness terceiroBusiness;

	@Autowired
	private NotaMercadoriaBusiness notaMercadoriaBusiness;

	@Autowired
	private NotaConsumoBusiness notaConsumoBusiness;

	@Autowired
	private ReducaoZBusiness reducaoZBusiness;

	@Autowired
	private CupomFiscalBusiness cupomFiscalBusiness;
	
	@Autowired
	private ConhecimentoTransporteBusiness conhecimentoTransporteBusiness;

	@Autowired
	private SyncControlBusiness syncControlBusiness;

	@Autowired
	private Registro10Business registro10Business;

	@Autowired
	private Registro11Business registro11Business;

	@Autowired
	private Registro50Business registro50Business;

	@Autowired
	private Registro51Business registro51Business;

	@Autowired
	private Registro53Business registro53Business;

	@Autowired
	private Registro54Business registro54Business;

	@Autowired
	private Registro60Business registro60Business;

	@Autowired
	private Registro70Business registro70Business;
	
	@Autowired
	private Registro74Business registro74Business;

	@Autowired
	private Registro75Business registro75Business;

	@Override
	public br.com.canalvpsasul.sintegra.entities.Sintegra gerarSintegra(
			SintegraParametros parametros) throws Exception {

		User user = userBusiness.getCurrent();

		Configuracao configuracaoEmpresa = configuracaoBusiness
				.getConfiguracaoPorEmpresa(parametros.getEmpresa());
		if (configuracaoEmpresa == null) {
			throw new Exception("É necessario configurar a empresa "
					+ parametros.getEmpresa().getTerceiro().getNomeFantasia());
		}

		StringWriter sw = new StringWriter();
		SintegraWriter sintegraWriter = new SintegraWriter(sw);

		Sintegra sintegra = new Sintegra();

		sintegra.setRegistros50(new ArrayList<Registro50>());
		sintegra.setRegistros51(new ArrayList<Registro51>());
		sintegra.setRegistros53(new ArrayList<Registro53>());
		sintegra.setRegistros54(new ArrayList<Registro54>());
		sintegra.setRegistros60M(new ArrayList<Registro60M>());
		sintegra.setRegistros60R(new ArrayList<Registro60R>());
		sintegra.setRegistros70(new ArrayList<Registro70>());
		sintegra.setRegistros75(new ArrayList<Registro75>());

		sintegra.setRegistro10(registro10Business.obterRegistro10(
				parametros.getEmpresa(), parametros.getDataInicial(),
				parametros.getDataFinal(), parametros.getFinalidadeArquivo(),
				parametros.getNaturezaOperacao()));

		Informante informante = informanteBusiness
				.getInformantePorEmpresa(parametros.getEmpresa());
		sintegra.setRegistro11(registro11Business.obterRegistro11(
				parametros.getEmpresa(), informante));

		gerarRegistrosNotas(sintegra, configuracaoEmpresa, parametros);
		gerarRegistrosReducoes(sintegra, configuracaoEmpresa, parametros);
		gerarRegistrosInventario(sintegra, configuracaoEmpresa,
				configuracaoEmpresa.getEntidades(), parametros);
		gerarRegistrosConhecimento(sintegra, configuracaoEmpresa, parametros);

		Registro75 registroTemp = null;
		for (int i = 0; i < sintegra.getRegistros75().size() - 1; i++) {
			for (int j = i + 1; j < sintegra.getRegistros75().size(); j++) {
				if (new Long(sintegra.getRegistros75().get(i)
						.getCodigoProduto()) > new Long(sintegra
						.getRegistros75().get(j).getCodigoProduto())) {
					registroTemp = sintegra.getRegistros75().get(i);
					sintegra.getRegistros75().set(i,
							sintegra.getRegistros75().get(j));
					sintegra.getRegistros75().set(j, registroTemp);
				}
			}
		}

		sintegra.gerarRegistros90();

		sintegraWriter.write(sintegra);
		sintegraWriter.writerFlush();
		sintegraWriter.writerClose();

		br.com.canalvpsasul.sintegra.entities.Sintegra sintegraNew = armazenamentoSintegra
				.armazenarSintegra(new br.com.canalvpsasul.sintegra.entities.Sintegra(
						user.getPortal(), parametros.getEmpresa(), sw
								.toString()));

		return sintegraNew;
	}

	@Override
	public br.com.canalvpsasul.sintegra.entities.Sintegra getSintegra(Long id)
			throws Exception {
		return armazenamentoSintegra.get(id);
	}

	private void gerarRegistrosNotas(Sintegra sintegra,
			Configuracao configuracaoEmpresa, SintegraParametros parametros) {

		if (!parametros.getGerarRegistro50()
				&& !parametros.getGerarRegistro51()
				&& !parametros.getGerarRegistro53()
				&& !parametros.getGerarRegistro54()
				&& !parametros.getGerarRegistro75())
			return;

		List<NotaConsumo> notasConsumo = notaConsumoBusiness.findByDate(
				parametros.getEmpresa(), parametros.getDataInicial(),
				parametros.getDataFinal());
		List<NotaMercadoria> notasMercadoria = notaMercadoriaBusiness
				.findByDate(parametros.getEmpresa(),
						parametros.getDataInicial(), parametros.getDataFinal());

		for (NotaConsumo nota : notasConsumo) {
			sintegra.getRegistros50().add(
					registro50Business.obterRegistro50(nota));
		}

		for (NotaMercadoria nota : notasMercadoria) {

			/*
			 * Notas de entrada emitidas por terceiros com status cancelado não
			 * devem entrar no sintegra.
			 */
			if (nota.getTipo() == TipoNota.ENTRADA
					&& nota.getStatus() == StatusNota.CANCELADO
					&& nota.getTerceiroDestinatario() == parametros
							.getEmpresa().getTerceiro())
				continue;

			if (parametros.getGerarRegistro50())
				sintegra.getRegistros50().addAll(
						registro50Business.obterRegistro50(nota));
			if (parametros.getGerarRegistro51())
				sintegra.getRegistros51().addAll(
						registro51Business.obterRegistro51(nota));
			if (parametros.getGerarRegistro53())
				sintegra.getRegistros53().addAll(
						registro53Business.obterRegistro53(nota));
			if (parametros.getGerarRegistro54())
				sintegra.getRegistros54().addAll(
						registro54Business.obterRegistro54(nota));

			if (parametros.getGerarRegistro75()) {
				for (ItemNota item : nota.getItens()) {
					registro75Business.addRegistro75(item.getProduto(),
							sintegra, parametros.getEmpresa(),
							configuracaoEmpresa);
				}
			}
		}
	}

	private void gerarRegistrosReducoes(Sintegra sintegra,
			Configuracao configuracaoEmpresa, SintegraParametros parametros)
			throws ParseException {

		if (!parametros.getGerarRegistro60())
			return;

		List<ReducaoZ> reducoes = reducaoZBusiness.findByDate(
				parametros.getEmpresa(), parametros.getDataInicial(),
				parametros.getDataFinal());
		List<CupomFiscal> cuponsFiscais = new ArrayList<CupomFiscal>();

		for (ReducaoZ reducao : reducoes) {

			//TODO Após a correção do chamado da API de reduções Z da Gbaby, remover essa validação desnecessária. A api da vpsa está trazendo reduções duplicadas.
			Boolean crzRepetido = false;
			for(Registro60M registros60M : sintegra.getRegistros60M()) {
				if(registros60M.getCrz() == Integer.valueOf(reducao.getCrz().toString())) {
					crzRepetido = true;
					break;
				}	
			}			
			if(crzRepetido)
				continue;
			
			/*
			 * Somente vão ao sintegra as reduções que possuem movimentação.
			 */
			if (reducao.getVendaBrutaDiaria() <= 0)
				continue;

			Registro60M registro60m = registro60Business
					.obterRegistro60M(reducao);

			List<CupomFiscal> cuponsFiscaisTemp = cupomFiscalBusiness
					.getByDataEmissaoFromEcf(parametros.getEmpresa()
							.getPortal(), reducao.getNumeroSerieECF(), reducao
							.getDataMovimento());

			/*
			 * Como as melhorias na API de redução Z saíram no dia 30/01/2014,
			 * para geração de sintegra de antes dessa data, é necessário
			 * calcular os totalizadores de cancelamento, desconto e iss
			 * mediante avaliação dos cupons do período. Após essa data, o cálculo
			 * já é realizado pelo método Registro60Business.obterRegistro60M();
			 */
			SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			String dateInString = "31-01-2014 00:00:00";
			Date date = sdf.parse(dateInString);

			if (reducao.getDataReducaoZ().before(date)) {

				/*
				 * Adicionando totalizadores de cancelamentos e descontos.
				 */
				@SuppressWarnings("deprecation")
				List<Registro60A> registros60A = registro60Business
						.obterRegistro60A(cuponsFiscaisTemp);
				if (registros60A.size() > 0)
					registro60m.getRegistros60A().addAll(registros60A);
			}

			sintegra.getRegistros60M().add(registro60m);

			if (cuponsFiscaisTemp.size() > 0)
				cuponsFiscais.addAll(cuponsFiscaisTemp);
		}

		if (cuponsFiscais.size() > 0) {
			sintegra.getRegistros60R().addAll(
					registro60Business.obterRegistro60R(cuponsFiscais));

			for (CupomFiscal cFiscal : cuponsFiscais) {
				for (ItemNota itemNota : cFiscal.getItens()) {
					registro75Business.addRegistro75(itemNota.getProduto(),
							sintegra, parametros.getEmpresa(),
							configuracaoEmpresa);
				}
			}
		}
	}

	private void gerarRegistrosInventario(Sintegra sintegra,
			Configuracao configuracaoEmpresa, List<Entidade> entidades,
			SintegraParametros parametros) throws Exception {

		if (!parametros.getGerarRegistro74())
			return;

		sintegra.setRegistros74(new ArrayList<Registro74>());

		List<Produto> produtos = produtoBusiness.getAll(parametros.getEmpresa()
				.getPortal());

		for (Produto produto : produtos) {
			sintegra.getRegistros74().add(
					registro74Business.obterRegistro74(produto,
							parametros.getEmpresa(),
							parametros.getDataInventario(), entidades));
			registro75Business.addRegistro75(produto, sintegra,
					parametros.getEmpresa(), configuracaoEmpresa);
		}
	}
	
	private void gerarRegistrosConhecimento(Sintegra sintegra,
			Configuracao configuracaoEmpresa,
			SintegraParametros parametros) throws Exception {

		if (!parametros.getGerarRegistro70() && !parametros.getGerarRegistro71())
			return;

		sintegra.setRegistros70(new ArrayList<Registro70>());

		List<ConhecimentoTransporte> conhecimentos = conhecimentoTransporteBusiness.findByDate(configuracaoEmpresa.getEmpresa(), parametros.getDataInicial(), parametros.getDataFinal());

		for (ConhecimentoTransporte conhec : conhecimentos) {
			sintegra.getRegistros70().add(registro70Business.obterRegistro70(conhec));
		}
	}
}
