package br.jus.trt23.nucleo.enums;

public enum TipoPessoa
{
	F("Pessoa Física"), J("Pessoa Jurídica");
	
	private String descricao;

    private TipoPessoa(String descricao) 
    {
    	this.descricao = descricao;
    }
    
    public String getDescricao()
    {
    	return descricao;
    }
}