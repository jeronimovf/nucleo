package br.jus.trt23.nucleo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "maiusculoConverter")
public class MaiusculoConverter implements Converter
{

	public Object getAsObject(FacesContext facesContext, UIComponent component, String stringValue)
	{
		if (stringValue != null)
		{
			return stringValue.trim().toUpperCase();

		}
		return stringValue;
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object objectValue)
	{
		return objectValue.toString();
	}
}