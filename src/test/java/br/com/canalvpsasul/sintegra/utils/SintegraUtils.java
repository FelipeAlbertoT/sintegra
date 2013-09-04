package br.com.canalvpsasul.sintegra.utils;

import java.io.IOException;
import java.io.StringWriter;

import coffeepot.br.sintegra.Sintegra;
import coffeepot.br.sintegra.writer.SintegraWriter;

public class SintegraUtils {

	public static Sintegra criarSintegra() {
		
		return new Sintegra();		
	}
	
	public static String gerarSintegra(Sintegra sintegra) throws IOException {
		
		StringWriter sw = new StringWriter();
		SintegraWriter sintegraWriter = new SintegraWriter(sw);
		
		sintegraWriter.write(sintegra);
		sintegraWriter.writerFlush();
		sintegraWriter.writerClose();	
		
		return sw.toString();
		
	}
	
}
