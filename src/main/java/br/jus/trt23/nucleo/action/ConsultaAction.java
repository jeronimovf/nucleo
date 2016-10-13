package br.jus.trt23.nucleo.action;

import java.util.List;

import javax.inject.Named;

@Named
public abstract class ConsultaAction<T>
{
	protected boolean habilitaPesquisa;
	
	public ConsultaAction()
	{
	}

	public String voltar()
	{		
		return "/menu.xhtml?facesRedirect=true";
	}

	public boolean isHabilitaPesquisa()
	{
		return habilitaPesquisa;
	}

	public void setHabilitaPesquisa(boolean habilitaPesquisa)
	{
		this.habilitaPesquisa = habilitaPesquisa;
	}

	public abstract List<T> getLista();
	
	public abstract void pesquisar();
}