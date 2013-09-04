package br.com.canalvpsasul.sintegra.business;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Endereco;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Terceiro;
import br.com.canalvpsasul.vpsabusiness.entities.operacional.Produto;
import coffeepot.br.sintegra.Sintegra;
import coffeepot.br.sintegra.registros.Registro74;
import coffeepot.br.sintegra.writer.SintegraWriter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:**/servlet-context.xml", "classpath:**/hibernate-context.xml", "classpath:**/root-context.xml", "classpath:**/spring-security.xml"})
public class Registro74BusinessTest {
	
	@Autowired
	private Registro74Business registro74Business;
	
	@Test
	public void GerarRegistro74() throws IOException {
		
		Terceiro terceiroEmpresa = new Terceiro();
		ArrayList<Endereco> enderecosTerceiroEmpresa = new ArrayList<Endereco>();
		Endereco endereco = new Endereco();
		endereco.setSiglaEstado("SC");
		enderecosTerceiroEmpresa.add(endereco);
		terceiroEmpresa.setEnderecos(enderecosTerceiroEmpresa);
		
		Empresa empresa = new Empresa();
		empresa.setTerceiro(terceiroEmpresa);
		
		Produto produto = new Produto();
		produto.setVpsaId(Long.valueOf(1));
		produto.setQuantidadeEmEstoque(Long.valueOf(1));
		produto.setValorTotalEmEstoque(Float.valueOf(100));
		
		StringWriter sw = new StringWriter();
		SintegraWriter sintegraWriter = new SintegraWriter(sw);

		Sintegra sintegra = new Sintegra();
		
		ArrayList<Registro74> registros = new ArrayList<Registro74>();		
		sintegra.setRegistros74(registros);
		
		sintegra.getRegistros74().add(registro74Business.obterRegistro74(produto, empresa));
		
		sintegraWriter.write(sintegra);
		sintegraWriter.writerFlush();
		sintegraWriter.writerClose();
			
		Assert.assertEquals("74201309041             00000000010000000000010000100000000000000              SC                                             ", sw.toString().replace("\r\n", ""));
	}

}
