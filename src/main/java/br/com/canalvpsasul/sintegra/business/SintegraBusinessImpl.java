package br.com.canalvpsasul.sintegra.business;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.canalvpsasul.sintegra.business.Imposto.IpiBusiness;
import br.com.canalvpsasul.sintegra.business.Registros.Registro10Business;
import br.com.canalvpsasul.sintegra.business.Registros.Registro11Business;
import br.com.canalvpsasul.sintegra.business.Registros.Registro50Business;
import br.com.canalvpsasul.sintegra.business.Registros.Registro51Business;
import br.com.canalvpsasul.sintegra.business.Registros.Registro53Business;
import br.com.canalvpsasul.sintegra.business.Registros.Registro54Business;
import br.com.canalvpsasul.sintegra.business.Registros.Registro74Business;
import br.com.canalvpsasul.sintegra.business.Registros.Registro75Business;
import br.com.canalvpsasul.sintegra.entities.Configuracao;
import br.com.canalvpsasul.sintegra.entities.Informante;
import br.com.canalvpsasul.sintegra.entities.SintegraParametros;
import br.com.canalvpsasul.vpsabusiness.business.SyncControlBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.TerceiroBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.UserBusiness;
import br.com.canalvpsasul.vpsabusiness.business.fiscal.NotaConsumoBusiness;
import br.com.canalvpsasul.vpsabusiness.business.fiscal.NotaMercadoriaBusiness;
import br.com.canalvpsasul.vpsabusiness.business.operacional.ProdutoBusiness;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Entidade;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Portal;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.User;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ItemNota;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaConsumo;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;
import br.com.canalvpsasul.vpsabusiness.entities.operacional.Produto;
import coffeepot.br.sintegra.Sintegra;
import coffeepot.br.sintegra.registros.Registro50;
import coffeepot.br.sintegra.registros.Registro51;
import coffeepot.br.sintegra.registros.Registro53;
import coffeepot.br.sintegra.registros.Registro54;
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
	private Registro74Business registro74Business;
	
	@Autowired
	private Registro75Business registro75Business;
	
	@Override
	public br.com.canalvpsasul.sintegra.entities.Sintegra gerarSintegra(
			SintegraParametros parametros) throws Exception {

		User user = userBusiness.getCurrent();

		Configuracao configuracaoEmpresa = configuracaoBusiness.getConfiguracaoPorEmpresa(parametros.getEmpresa());
		if(configuracaoEmpresa == null){
			throw new Exception("É necessario configurar a empresa "+parametros.getEmpresa().getTerceiro().getNomeFantasia());
		}

		StringWriter sw = new StringWriter();
		SintegraWriter sintegraWriter = new SintegraWriter(sw);

		Sintegra sintegra = new Sintegra();
		
		sintegra.setRegistros50(new ArrayList<Registro50>());
		sintegra.setRegistros51(new ArrayList<Registro51>());
		sintegra.setRegistros53(new ArrayList<Registro53>());
		sintegra.setRegistros54(new ArrayList<Registro54>());
		sintegra.setRegistros75(new ArrayList<Registro75>());

		sintegra.setRegistro10(registro10Business.obterRegistro10(parametros.getEmpresa(),
				parametros.getDataInicial(), parametros.getDataFinal(),
				parametros.getFinalidadeArquivo(),
				parametros.getNaturezaOperacao()));
		
		Informante informante = informanteBusiness.getInformantePorEmpresa(parametros.getEmpresa());
		sintegra.setRegistro11(registro11Business.obterRegistro11(parametros.getEmpresa(), informante));

		gerarRegistrosNotas(parametros.getEmpresa(), sintegra, parametros.getDataInicial(),
				parametros.getDataFinal(), configuracaoEmpresa);

		if(parametros.getGerarRegistro74())
			gerarRegistrosInventario(sintegra, parametros.getEmpresa(), parametros.getDataInventario(), configuracaoEmpresa, user.getPortal(), configuracaoEmpresa.getEntidades());
		
		sintegra.gerarRegistros90();

		sintegraWriter.write(sintegra);
		sintegraWriter.writerFlush();
		sintegraWriter.writerClose();

		br.com.canalvpsasul.sintegra.entities.Sintegra sintegraNew = armazenamentoSintegra.armazenarSintegra(new br.com.canalvpsasul.sintegra.entities.Sintegra(user.getPortal(), parametros.getEmpresa(), sw.toString()));
		
		return sintegraNew;
	}
	
	@Override
	public br.com.canalvpsasul.sintegra.entities.Sintegra getSintegra(Long id)
			throws Exception {
		return armazenamentoSintegra.get(id);
	}

	private void gerarRegistrosNotas(Empresa empresa, Sintegra sintegra, Date dataInicial,
			Date dataFinal, Configuracao configuracaoEmpresa) {

		List<NotaConsumo> notasConsumo = notaConsumoBusiness.findByDate(empresa,
				dataInicial, dataFinal);
		
		List<NotaMercadoria> notasMercadoria = notaMercadoriaBusiness
				.findByDate(empresa, dataInicial, dataFinal);

		for (NotaConsumo nota : notasConsumo) {			
			sintegra.getRegistros50().add(registro50Business.obterRegistro50(nota));
		}

		for (NotaMercadoria nota : notasMercadoria) {
			sintegra.getRegistros50().addAll(registro50Business.obterRegistro50(nota));
			sintegra.getRegistros51().addAll(registro51Business.obterRegistro51(nota));
			sintegra.getRegistros53().addAll(registro53Business.obterRegistro53(nota));
			sintegra.getRegistros54().addAll(registro54Business.obterRegistro54(nota));
			
			for (ItemNota item : nota.getItens())
				registro75Business.addRegistro75(item.getProduto(), sintegra, empresa, configuracaoEmpresa);
		}
	}

	private void gerarRegistrosInventario(Sintegra sintegra, Empresa empresa, Date dataInventario, Configuracao configuracaoEmpresa, Portal portal, List<Entidade> entidades) { 

		sintegra.setRegistros74(new ArrayList<Registro74>());
		
		PageRequest pageRequest;		
		List<Produto> produtos;
		
		int pageNumber = 0, returnCount = 50;
		do {			
			pageRequest = new PageRequest(pageNumber++, returnCount);			
			produtos = produtoBusiness.getAll(portal, pageRequest);
			
			for(Produto produto : produtos) {
				sintegra.getRegistros74().add(registro74Business.obterRegistro74(produto, empresa, dataInventario, entidades));
				registro75Business.addRegistro75(produto, sintegra, empresa, configuracaoEmpresa);
			}
			
		}while(produtos.size() == returnCount);
	}
	
}
