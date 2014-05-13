package br.com.canalvpsasul.sintegra.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.canalvpsasul.sintegra.entities.SintegraTemplate;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.EntidadeBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.PortalBusiness;
import br.com.canalvpsasul.vpsabusiness.business.fiscal.ReducaoZBusiness;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Portal;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ReducaoZ;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.TotalizadorReducao;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SintegraImportadorBusinessImpl implements SintegraImportadorBusiness {

	private List<ReducaoZ> toSaveList = new ArrayList<ReducaoZ>();
	
	@Autowired
	private ReducaoZBusiness reducaoZBusiness;
	
	@Autowired
	private EntidadeBusiness entidadeBusiness;
	
	@Autowired
	private PortalBusiness portalBusiness;
	
	@Override
	public void processar(SintegraTemplate sintegraTemplate) throws Exception {

		toSaveList = new ArrayList<ReducaoZ>();
		
		String data = sintegraTemplate.getData();
		
		Portal portal = portalBusiness.get(2L);
		
		String[] registros = data.split("\r\n");
		
		for(int i = 0; i < registros.length; i++) {
			
			if(registros[i].trim().equals("")) 
				break;
			
			ReducaoZ reducaoZ = processarReducao(registros[i], portal);
			
			do {
				
				i++;
				
				if(i == registros.length) 
					break;
				
				String[] colunas = obterColunasRegistro(registros[i]);
				
				if(colunas.length == 2) {
					
					processarTotalizador(colunas, reducaoZ);
					
					continue;
				}
				
				break;
				
			} while(true);
				
			i--;
			toSaveList.add(reducaoZ);
		}
		
		saveDados();
	}
	
	private void saveDados() throws Exception {
		
		for(ReducaoZ reducaoZ : toSaveList) 
			reducaoZBusiness.salvar(reducaoZ);
	}
	
	private void processarTotalizador(String[] colunas, ReducaoZ reducaoZ) throws Exception {
		
		if(colunas.length != 2) 
			throw new Exception();

		colunas[0] = colunas[0].replace("%", "").replace(",", "");
		colunas[1] = colunas[1].replace(".", "").replace(",", ".");
		
		float total = Float.parseFloat(colunas[1]);
		
		TotalizadorReducao totalizadorReducao = new TotalizadorReducao(); 
		totalizadorReducao.setValorAcumulado(total);
		totalizadorReducao.setCodigo(colunas[0]);
		totalizadorReducao.setReducaoZ(reducaoZ);
				
		reducaoZ.getTotalizadores().add(totalizadorReducao);
	}
	
	private ReducaoZ processarReducao(String registro, Portal portal) throws Exception {
		
		String[] colunas = obterColunasRegistro(registro);
		
		if(colunas.length != 14) 
			throw new Exception();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		
		colunas[9] = colunas[9].replace(".", "").replace(",", ".");
		colunas[10] = colunas[10].replace(".", "").replace(",", ".");
		colunas[11] = colunas[11].replace(".", "").replace(",", ".");
		colunas[12] = colunas[12].replace(".", "").replace(",", ".");
		
		ReducaoZ reducaoZ = new ReducaoZ();
		
		reducaoZ.setCoo(Long.parseLong(colunas[0].replace("COO:", "")));
		reducaoZ.setCooInicial(Long.parseLong(colunas[1].replace("COO:", "")));
		reducaoZ.setCooFinal(Long.parseLong(colunas[2].replace("COO:", "")));
		reducaoZ.setCro(Long.parseLong(colunas[3]));
		reducaoZ.setCrz(Long.parseLong(colunas[4]));
		reducaoZ.setDataMovimento(dateFormat.parse(colunas[5]));
		reducaoZ.setDataReducaoZ(dateFormat.parse(colunas[6]));
		reducaoZ.setNumeroSequencialECF(Integer.parseInt(colunas[7].replace("ECF:", "")));
		reducaoZ.setNumeroSerieECF(colunas[8].replace("FAB:", ""));
		reducaoZ.setTotalizadorGeral(Double.parseDouble(colunas[9]));
		reducaoZ.setVendaBrutaDiaria(Float.parseFloat(colunas[10]));
		reducaoZ.setCancelamentoICMS(Float.parseFloat(colunas[11]));
		reducaoZ.setDescontoICMS(Float.parseFloat(colunas[12]));
		
		reducaoZ.setAtivo(true);
		reducaoZ.setDataAlteracao(new Date());
		reducaoZ.setDataCriacao(new Date());
		
		reducaoZ.setEntidade(entidadeBusiness.getByVpsaId(Long.parseLong(colunas[13]), portal));
		reducaoZ.setDataReducaoZ(reducaoZ.getDataMovimento());
		reducaoZ.setPortal(portal);
		
		return reducaoZ;
	}

	private String[] obterColunasRegistro(String registro) {
		
		String[] colunas = registro.split("\t");
		
		for(int i = 0; i < colunas.length; i++) 
			colunas[i] = colunas[i].trim(); 
		
		return colunas;	
	}
	
}
