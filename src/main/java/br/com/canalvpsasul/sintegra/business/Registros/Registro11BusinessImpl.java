package br.com.canalvpsasul.sintegra.business.Registros;

import org.springframework.stereotype.Service;

import br.com.canalvpsasul.sintegra.entities.Informante;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Empresa;
import br.com.canalvpsasul.vpsabusiness.utils.StringUtils;
import coffeepot.br.sintegra.registros.Registro11;

@Service
public class Registro11BusinessImpl implements Registro11Business {

	@Override
	public Registro11 obterRegistro11(Empresa empresa, Informante informante) {
		
		Registro11 registro11 = new Registro11();

		if (informante == null)
			return registro11;

		registro11.setBairro(StringUtils.removeAccents(informante.getBairro()));
		registro11.setCep(informante.getCep());
		registro11.setComplemento(StringUtils.removeAccents(informante.getComplemento()));
		registro11.setLogradouro(StringUtils.removeAccents(informante.getLogradouro()));
		registro11.setNumero(informante.getNumero());
		registro11.setResponsavel(StringUtils.removeAccents(informante.getNome()));
		registro11.setTelefone(informante.getTelefone());

		return registro11;
	}

}
