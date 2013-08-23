package br.com.canalvpsasul.sintegra.business;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.canalvpsasul.sintegra.entities.Informante;
import br.com.canalvpsasul.vpsabusiness.business.EmpresaBusiness;
import br.com.canalvpsasul.vpsabusiness.entities.Empresa;
import coffeepot.br.sintegra.Sintegra;
import coffeepot.br.sintegra.registros.Registro10;
import coffeepot.br.sintegra.registros.Registro11;
import coffeepot.br.sintegra.tipos.Convenio;
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
	
	@Override
	public String gerarSintegra(Empresa empresa, Date dataInicial, Date dataFinal, FinalidadeArquivo finalidadeArquivo, NaturezaOperacao naturezaOperacao) throws Exception {
		
		OutputStream oStream = generateOutputStream();
	    Writer fw = new BufferedWriter(new OutputStreamWriter(oStream, "ISO-8859-1"));       
	    
	    SintegraWriter sintegraWriter = new SintegraWriter(fw);

		Sintegra sintegra = new Sintegra();
		
		sintegra.setRegistro10(gerarRegistro10(empresa, dataInicial, dataFinal, finalidadeArquivo, naturezaOperacao));
		sintegra.setRegistro11(gerarRegistro11(empresa));
		
		sintegra.gerarRegistros90();
		
	    sintegraWriter.write(sintegra);  
	    sintegraWriter.writerFlush();
	    sintegraWriter.writerClose();
	    
	    return oStream.toString();
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
		
		Registro10 registro10 = new Registro10();
		
		registro10.setCnpj(empresa.getDocumento());
		registro10.setRazaoSocial(empresa.getNome());
		registro10.setCodigoConvenio(Convenio.CONV_1_5795_3002);
		registro10.setDataInicial(dataInicial);
		registro10.setDataFinal(dataFinal);
		registro10.setFinalidadeArquivo(finalidadeArquivo);
		registro10.setNaturezaOperacao(naturezaOperacao);
		
		//TODO Campos faltantes na API Empresa
		registro10.setCidade("Cidade");
		registro10.setFax("");
		registro10.setIe("");
		registro10.setUf(""); 
		
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

}
