/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt23.nucleo.converters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author j129-9
 */
@FacesConverter(value = "localDateConverter")
public class LocalDateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.isEmpty()) {
            return null;
        }
        String formato;
        String newValue = value;
        formato = (String) component.getAttributes().get("patern");
        if (null != formato) {
            return LocalDate.parse(newValue, DateTimeFormatter.ofPattern(formato));
        }

        switch (value.length()) {
            //caso a string tenha 7 caracteres considera-se que
            //se trata da especificação de um mês, então completa-se
            //a string com 01 referindo-se ao primeiro dia
            case 7:
                newValue = "01/".concat(newValue);
                formato = "dd/MM/yyyy";
                break;
            //caso a string tenha 10 caracteres considera-se que 
            //se trata de uma data com o ano especificado com 2 dítigos
            case 8:
                formato = "dd/MM/yy";
                break;
            //caso a string tenha 10 caracteres considera-se que 
            //se trata de uma data em formato completo
            case 10:
                formato = "dd/MM/yyyy";
                break;
        }
        return LocalDate.parse(newValue, DateTimeFormatter.ofPattern(formato));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof LocalDate) {
            LocalDate dateValue = (LocalDate) value;
            String formato;
            formato = (String) component.getAttributes().get("pattern");
            if (null != formato) {
                return dateValue.format(DateTimeFormatter.ofPattern(formato));
            }
            return dateValue.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + LocalDate.class.getName());
        }
    }
}
