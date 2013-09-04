package br.com.canalvpsasul.sintegra.entities;

public enum TipoItemRegistro54 {
	
	NONE(null, ""),
	FRETE("991", " Identifica o registro do frete"),
	SEGURO("992", "Identifica o registro do seguro"),
	PISCOFINS("993", "Pis/Cofins"),
	COMPLEMENTO("997", "Complemento de valor de Nota Fiscal e/ou ICMS"),
	SERVICOSNAOTRIBUTADOS("998", "Serviços não tributados"),
	OUTRASDESPESAS("999", "Identifica o registro de outras despesas acessórias");
	
	private String code;
    
    private String description;
    
    TipoItemRegistro54(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public String getDescription() {
        return this.description;
    }
	
}
