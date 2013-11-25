package br.com.canalvpsasul.sintegra.business;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.canalvpsasul.sintegra.business.Registros.Registro74Business;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:**/servlet-context.xml", "classpath:**/hibernate-context.xml", "classpath:**/root-context.xml", "classpath:**/spring-security.xml"})
public class Registro74BusinessTest {
	
	@Autowired
	private Registro74Business registro74Business;
	
	@Test
	public void GerarRegistro74() throws IOException {
		
		//TODO SINTEGRA Registro 74 - Implementar teste do registro 74
		
	}

}
