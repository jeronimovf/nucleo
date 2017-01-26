package br.jus.trt23.nucleo.constantes;

import java.text.SimpleDateFormat;

public interface Constantes 
{	
	public static final SimpleDateFormat formataMesAno = new SimpleDateFormat("MM/yyyy");
	
	public static final SimpleDateFormat formataMesExtensoAno = new SimpleDateFormat("MMMM/yyyy");
	
	public static final SimpleDateFormat formataAnoMesDia = new SimpleDateFormat("yyyyMMdd");
	
	public static final SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
	
	public static final SimpleDateFormat formataDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	public static final SimpleDateFormat formataHoraMinuto = new SimpleDateFormat("HH:mm");
	
	public static final String CAS_LOGOUT_URL = "CAS_LOGOUT_URL";
	
	public static final String INTRANET_URL = "INTRANET_URL";
	
	public static final String CODIGO_SISTEMA = "CODIGO_SISTEMA";
	
	public static final String AMBIENTE = "AMBIENTE";
	
	public static final String VIEW_SESSAO_EXPIRADA = "/sessaoExpirada.xhtml";
	
	public static final String VIEW_ERRO = "/erro.xhtml";	
}