package br.com.canalvpsasul.sintegra.business;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.canalvpsasul.sintegra.business.Registros.Registro54Business;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:**/servlet-context.xml", "classpath:**/hibernate-context.xml", "classpath:**/root-context.xml", "classpath:**/spring-security.xml"})
public class Registro54BusinessTest {
	
	@Autowired
	private Registro54Business registro54Business;
	
	@Test
	public void gerarRegistro54NotaSaidaComFreteSeguroOutros() throws IOException {
		
		//Testar se estão sendo gerados os registros para frete, seguro e outros.		
		
	}

}
