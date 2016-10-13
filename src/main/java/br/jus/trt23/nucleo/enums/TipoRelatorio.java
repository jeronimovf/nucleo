package br.jus.trt23.nucleo.enums;

public enum TipoRelatorio
{
	ANALITICO("Analítico"), SINTETICO("Sintético");
	
	private String descricao;

    private TipoRelatorio(String descricao) 
    {
    	this.descricao = descricao;
    }
    
    public String getDescricao()
    {
    	return descricao;
    }
}