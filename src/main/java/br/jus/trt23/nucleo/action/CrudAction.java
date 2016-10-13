package br.jus.trt23.nucleo.action;

import java.util.List;
import javax.faces.bean.ViewScoped;

@ViewScoped
public abstract class CrudAction<T>
{
	protected boolean botaoNovo;
	
	protected boolean botaoSalvar;
	
	protected boolean habilitaNovo;
	
	protected boolean habilitaPesquisa;
	
	public CrudAction()
	{
		botaoNovo = true;
		botaoSalvar = true;
	}

	public void novo()
	{
		habilitaNovo = true;
	}
	
	public void cancelar()
	{		
		habilitaNovo = false;
	}	
	
	public String voltar()
	{		
		return "/menu.xhtml?facesRedirect=true";
	}

	public boolean isBotaoNovo()
	{
		return botaoNovo;
	}

	public void setBotaoNovo(boolean botaoNovo)
	{
		this.botaoNovo = botaoNovo;
	}

	public boolean isBotaoSalvar()
	{
		return botaoSalvar;
	}

	public void setBotaoSalvar(boolean botaoSalvar)
	{
		this.botaoSalvar = botaoSalvar;
	}

	public boolean isHabilitaNovo()
	{
		return habilitaNovo;
	}

	public void setHabilitaNovo(boolean habilitaNovo)
	{
		this.habilitaNovo = habilitaNovo;
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
	
	public abstract void editar(T entidade);
	
	public abstract void salvar();
	
	public abstract void pesquisar();
}