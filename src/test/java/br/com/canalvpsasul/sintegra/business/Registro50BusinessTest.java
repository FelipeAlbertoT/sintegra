package br.com.canalvpsasul.sintegra.business;

import java.io.IOException;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.canalvpsasul.sintegra.business.Registros.Registro50Business;
import br.com.canalvpsasul.sintegra.factory.NotaMercadoriaFactory;
import br.com.canalvpsasul.sintegra.utils.SintegraUtils;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;
import coffeepot.br.sintegra.Sintegra;
import coffeepot.br.sintegra.registros.Registro50;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:**/servlet-context.xml", "classpath:**/hibernate-context.xml", "classpath:**/root-context.xml", "classpath:**/spring-security.xml"})
public class Registro50BusinessTest {

	@Autowired 
	private Registro50Business registro50Business;
	
	@Test
	public void gerarRegistro50Saida() throws IOException {
		
	}
	
	@Test
	public void gerarRegistro50Entrada() throws IOException {
		
	}
	
	@Test
	public void gerarRegistro50SaidaComSt() throws IOException {
		
		NotaMercadoria nota = NotaMercadoriaFactory.gerarNotaSaidaMercadoriaStIpi();
		
		Sintegra sintegra = SintegraUtils.criarSintegra();
		ArrayList<Registro50> registros = new ArrayList<Registro50>();		
		sintegra.setRegistros50(registros);
		
		sintegra.getRegistros50().addAll(registro50Business.obterRegistro50(nota));
		
		String actual = SintegraUtils.gerarSintegra(sintegra);		
		String expected = "50999999999999999999999999999920130904RJ55   9999995401P0000000130960000000006800000000000000001800N";
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void gerarRegistro50EntradaComSt() throws IOException {
		
	}
	
	@Test
	public void gerarRegistro50NotaCancelada() throws IOException {
		
	}
	
	@Test
	public void gerarRegistro50NotaConsumo() throws IOException {
		
	}
	
	@Test
	public void gerarRegistro50NotaConsumoCancelada() throws IOException {
		
	}
}
