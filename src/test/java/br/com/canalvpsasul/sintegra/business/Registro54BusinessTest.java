package br.com.canalvpsasul.sintegra.business;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Endereco;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Terceiro;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.Imposto;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.ItemNota;
import br.com.canalvpsasul.vpsabusiness.entities.fiscal.NotaMercadoria;
import br.com.canalvpsasul.vpsabusiness.entities.operacional.Produto;
import br.com.canalvpsasul.vpsapi.entity.fiscal.ModeloGerencial;
import br.com.canalvpsasul.vpsapi.entity.fiscal.StatusNota;
import br.com.canalvpsasul.vpsapi.entity.fiscal.TipoNota;
import coffeepot.br.sintegra.Sintegra;
import coffeepot.br.sintegra.registros.Registro54;
import coffeepot.br.sintegra.writer.SintegraWriter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:**/servlet-context.xml", "classpath:**/hibernate-context.xml", "classpath:**/root-context.xml", "classpath:**/spring-security.xml"})
public class Registro54BusinessTest {
	
	@Autowired
	private Registro54Business registro54Business;
	
	@Test
	public void gerarRegistro54NotaSaidaComFreteSeguroOutros() throws IOException {
				
		NotaMercadoria notaMercadoria = new NotaMercadoria();
		
		notaMercadoria.setData(new Date());
		notaMercadoria.setId(Long.valueOf(1));
		notaMercadoria.setModeloGerencial(ModeloGerencial.MERCADORIA);		
		notaMercadoria.setNumero(Long.valueOf(1));
		notaMercadoria.setStatus(StatusNota.EMITIDO);
		notaMercadoria.setTipo(TipoNota.SAIDA);
		notaMercadoria.setValorTotal(Float.valueOf(100));
		
		Terceiro terceiro = new Terceiro();
		terceiro.setIe("029/0177553");
		terceiro.setDocumento("42745615291736");
		Endereco endereco = new Endereco();
		endereco.setSiglaEstado("SC");
		ArrayList<Endereco> enderecos = new ArrayList<Endereco>();
		enderecos.add(endereco);
		terceiro.setEnderecos(enderecos);
		notaMercadoria.setTerceiroDestinatario(terceiro);
		
		terceiro = new Terceiro();
		terceiro.setIe("029/0177553");
		terceiro.setDocumento("42745615291736");
		endereco = new Endereco();
		endereco.setSiglaEstado("SC");
		enderecos = new ArrayList<Endereco>();
		enderecos.add(endereco);
		terceiro.setEnderecos(enderecos);
		notaMercadoria.setTerceiroRemetente(terceiro);
		
		ArrayList<ItemNota> itens = new ArrayList<ItemNota>();
		
		ItemNota itemNota = new ItemNota(); 
		itemNota.setCfop(Long.valueOf(5102));
		itemNota.setQuantidade(Float.valueOf(1));
		itemNota.setValorFrete(Float.valueOf(15));
		itemNota.setValorOutros(Float.valueOf(3));
		itemNota.setValorSeguro(Float.valueOf(5));
		itemNota.setValorUnitario(Float.valueOf(100));
		
		Produto produto = new Produto();
		produto.setVpsaId(Long.valueOf(1));
		itemNota.setProduto(produto);
		
		Imposto icms = new Imposto();
		icms.setCst("000");
		icms.setAliquota(Float.valueOf(12));
		icms.setValor(Float.valueOf(12));
		icms.setBase(Float.valueOf(100));
		itemNota.setIcms(icms);
		
		Imposto icmsSt = new Imposto();
		icmsSt.setAliquota(Float.valueOf(0));
		icmsSt.setValor(Float.valueOf(0));
		icmsSt.setBase(Float.valueOf(0));
		itemNota.setIcmsst(icmsSt);
		
		itens.add(itemNota);
		
		notaMercadoria.setItens(itens);
		
		StringWriter sw = new StringWriter();
		SintegraWriter sintegraWriter = new SintegraWriter(sw);

		Sintegra sintegra = new Sintegra();
		
		List<Registro54> registros = registro54Business.obterRegistro54(notaMercadoria);
		sintegra.setRegistros54(registros);
		
		/* Um registro do item mais um para frete, outro para seguro e um para outras despesas. */
		Assert.assertTrue(registros.size() == 4);
		
		sintegraWriter.write(sintegra);
		sintegraWriter.writerFlush();
		sintegraWriter.writerClose();			
	}

}
