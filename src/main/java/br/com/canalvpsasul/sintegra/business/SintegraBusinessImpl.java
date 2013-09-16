package br.com.canalvpsasul.sintegra.business;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.canalvpsasul.sintegra.entities.Informante;
import br.com.canalvpsasul.sintegra.entities.SintegraParametros;
import br.com.canalvpsasul.sintegra.repository.SintegraRepository;
import br.com.canalvpsasul.vpsabusiness.business.SyncControlBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.TerceiroBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.UserBusiness;
import br.com.canalvpsasul.vpsabusiness.business.fiscal.NotaConsumoBusiness;
import br.com.canalvpsasul.vpsabusiness.business.fiscal.NotaMercadoriaBusiness;
import br.com.canalvpsasul.vpsabusiness.business.operacional.ProdutoBusiness;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Portal;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Terceiro;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.User;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ItemNota;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaConsumo;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;
import br.com.canalvpsasul.vpsabusiness.entities.operacional.Produto;
import coffeepot.br.sintegra.Sintegra;
import coffeepot.br.sintegra.registros.Registro10;
import coffeepot.br.sintegra.registros.Registro11;
import coffeepot.br.sintegra.registros.Registro50;
import coffeepot.br.sintegra.registros.Registro51;
import coffeepot.br.sintegra.registros.Registro53;
import coffeepot.br.sintegra.registros.Registro54;
import coffeepot.br.sintegra.registros.Registro74;
import coffeepot.br.sintegra.registros.Registro75;
import coffeepot.br.sintegra.tipos.Convenio;
import coffeepot.br.sintegra.tipos.FinalidadeArquivo;
import coffeepot.br.sintegra.tipos.NaturezaOperacao;
import coffeepot.br.sintegra.writer.SintegraWriter;

@Service
@Transactional
public class SintegraBusinessImpl implements SintegraBusiness {

	@Autowired
	private InformanteBusiness informanteBusiness;

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
	private SintegraRepository sintegraRepository;

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

		/*
		 * Nessa primeira versão mantemos apenas o último arquivo gerado na
		 * base.
		 */
		br.com.canalvpsasul.sintegra.entities.Sintegra sintegraOld = sintegraRepository
				.findByTerceiro(user.getTerceiro());
		if (sintegraOld != null)
			sintegraRepository.delete(sintegraOld);

		StringWriter sw = new StringWriter();
		SintegraWriter sintegraWriter = new SintegraWriter(sw);

		Sintegra sintegra = new Sintegra();
		
		sintegra.setRegistros50(new ArrayList<Registro50>());
		sintegra.setRegistros51(new ArrayList<Registro51>());
		sintegra.setRegistros53(new ArrayList<Registro53>());
		sintegra.setRegistros54(new ArrayList<Registro54>());
		sintegra.setRegistros75(new ArrayList<Registro75>());

		sintegra.setRegistro10(gerarRegistro10(parametros.getEmpresa(),
				parametros.getDataInicial(), parametros.getDataFinal(),
				parametros.getFinalidadeArquivo(),
				parametros.getNaturezaOperacao()));
		sintegra.setRegistro11(gerarRegistro11(parametros.getEmpresa()));

		gerarRegistrosNotas(parametros.getEmpresa(), sintegra, parametros.getDataInicial(),
				parametros.getDataFinal());

		if(parametros.getGerarRegistro74())
			gerarRegistrosInventario(sintegra, parametros.getEmpresa());
		
		sintegra.gerarRegistros90();

		sintegraWriter.write(sintegra);
		sintegraWriter.writerFlush();
		sintegraWriter.writerClose();

		br.com.canalvpsasul.sintegra.entities.Sintegra sintegraNew = new br.com.canalvpsasul.sintegra.entities.Sintegra();
		sintegraNew.setTerceiro(user.getTerceiro());
		sintegraNew.setSintegra(sw.toString());

		sintegraRepository.save(sintegraNew);

		return sintegraNew;
	}

	@Override
	public br.com.canalvpsasul.sintegra.entities.Sintegra getSintegra(Long id)
			throws Exception {
		return sintegraRepository.findOne(id);
	}

	private Registro10 gerarRegistro10(Empresa empresa, Date dataInicial,
			Date dataFinal, FinalidadeArquivo finalidadeArquivo,
			NaturezaOperacao naturezaOperacao) throws Exception {

		Portal portal = userBusiness.getCurrent().getPortal();

		Registro10 registro10 = new Registro10();

		registro10.setCnpj(empresa.getDocumento());
		registro10.setRazaoSocial(empresa.getNome());
		registro10.setCodigoConvenio(Convenio.CONV_3_5795_7603);
		registro10.setDataInicial(dataInicial);
		registro10.setDataFinal(dataFinal);
		registro10.setFinalidadeArquivo(finalidadeArquivo);
		registro10.setNaturezaOperacao(naturezaOperacao);

		Terceiro empresaTerceiro = terceiroBusiness.findByDocumento(portal,
				empresa.getDocumento());
		if (empresaTerceiro == null)
			throw new Exception(
					"O usuário logado não tem privilégios suficientes para obter todas as informações necessárias na VPSA.");

		if (empresaTerceiro.getEnderecos().size() == 0)
			throw new Exception(
					"Não existe endereço configurado no VPSA para a empresa informada. Verifique o cadastro de terceiros da VPSA e atualize o registro.");

		registro10.setCidade(empresaTerceiro.getEnderecos().get(0).getCidade());
		registro10.setUf(empresaTerceiro.getEnderecos().get(0).getSiglaEstado());
		registro10.setIe(empresaTerceiro.getIe());
		registro10.setFax("");

		return registro10;
	}

	private Registro11 gerarRegistro11(Empresa empresa) {

		Informante informante = informanteBusiness
				.getInformantePorEmpresa(empresa);

		Registro11 registro11 = new Registro11();

		if (informante == null)
			return registro11;

		registro11.setBairro(informante.getBairro());
		registro11.setCep(informante.getCep());
		registro11.setComplemento(informante.getComplemento());
		registro11.setLogradouro(informante.getLogradouro());
		registro11.setNumero(informante.getNumero());
		registro11.setResponsavel(informante.getNome());
		registro11.setTelefone(informante.getTelefone());

		return registro11;
	}

	private void gerarRegistrosNotas(Empresa empresa, Sintegra sintegra, Date dataInicial,
			Date dataFinal) {

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
				registro75Business.addRegistro75(item.getProduto(), sintegra);
		}
	}

	private void gerarRegistrosInventario(Sintegra sintegra, Empresa empresa) { 

		sintegra.setRegistros74(new ArrayList<Registro74>());
		
		PageRequest pageRequest;		
		List<Produto> produtos;
		
		int pageNumber = 0, returnCount = 50;
		do {			
			pageRequest = new PageRequest(pageNumber++, returnCount);			
			produtos = produtoBusiness.getAll(userBusiness.getCurrent().getPortal(), pageRequest);
			
			for(Produto produto : produtos) {
				sintegra.getRegistros74().add(registro74Business.obterRegistro74(produto, empresa));
				registro75Business.addRegistro75(produto, sintegra);
			}
			
		}while(produtos.size() == returnCount);
	}
	
}
