package br.jus.trt23.nucleo.enums;

public enum TipoSimNao
{
	S("Sim"), N("Não");
	
	private String descricao;

    private TipoSimNao(String descricao) 
    {
    	this.descricao = descricao;
    }
    
    public String getDescricao()
    {
    	return descricao;
    }
}