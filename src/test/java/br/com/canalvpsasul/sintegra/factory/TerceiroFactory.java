package br.com.canalvpsasul.sintegra.factory;

import java.util.ArrayList;

import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Endereco;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Terceiro;

public class TerceiroFactory {

	public static Terceiro gerarTerceiroRemetente() {
	
		Terceiro terceiro = new Terceiro();
		
		terceiro.setIe("11111111111111");
		terceiro.setDocumento("11111111111111");
		
		ArrayList<Endereco> enderecos = new ArrayList<Endereco>();
		Endereco endereco = new Endereco();
		endereco.setSiglaEstado("RJ");		
		enderecos.add(endereco);
		
		terceiro.setEnderecos(enderecos);
		
		return terceiro;		
	}
	
	public static Terceiro gerarTerceiro() {
		
		Terceiro terceiro = new Terceiro();
		
		terceiro.setIe("22222222222222");
		terceiro.setDocumento("22222222222222");
		
		ArrayList<Endereco> enderecos = new ArrayList<Endereco>();
		Endereco endereco = new Endereco();
		endereco.setSiglaEstado("RJ");		
		enderecos.add(endereco);
		
		terceiro.setEnderecos(enderecos);
		
		return terceiro;		
	}
	
	public static Terceiro gerarTerceiroDestinatario() {
		
		Terceiro terceiro = new Terceiro();
		
		terceiro.setIe("99999999999999");
		terceiro.setDocumento("99999999999999");
		
		ArrayList<Endereco> enderecos = new ArrayList<Endereco>();
		Endereco endereco = new Endereco();
		endereco.setSiglaEstado("RJ");		
		enderecos.add(endereco);
		
		terceiro.setEnderecos(enderecos);
		
		return terceiro;		
	}
}
