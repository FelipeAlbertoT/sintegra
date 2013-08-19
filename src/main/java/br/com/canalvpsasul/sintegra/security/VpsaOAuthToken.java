package br.com.canalvpsasul.sintegra.security;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class VpsaOAuthToken {

	private String access_token;
	
	private String cnpj_empresa;
	
	private String id_terceiro;
	
	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getCnpj_empresa() {
		return cnpj_empresa;
	}

	public void setCnpj_empresa(String cnpj_empresa) {
		this.cnpj_empresa = cnpj_empresa;
	}

	public String getId_terceiro() {
		return id_terceiro;
	}

	public void setId_terceiro(String id_terceiro) {
		this.id_terceiro = id_terceiro;
	}
}
