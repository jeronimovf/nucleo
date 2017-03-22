package br.jus.trt23.nucleo.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MensagemUtil 
{
	public static void info(final String mensagem) 
	{
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, null, mensagem);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public static void info(final String titulo, final String mensagem) 
	{
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, mensagem);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public static void warning(final String mensagem) 
	{
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, null, mensagem);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public static void warning(final String titulo, final String mensagem) 
	{
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, mensagem);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public static void error(final String mensagem) 
	{
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, mensagem);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public static void error(final String titulo, final String mensagem) 
	{
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, mensagem);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}