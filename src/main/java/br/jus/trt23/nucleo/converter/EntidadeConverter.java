package br.jus.trt23.nucleo.converter;

import br.jus.trt23.nucleo.entities.EntidadeGenerica;
import br.jus.trt23.nucleo.entities.EntidadeGenericaComId;
import java.io.Serializable;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "entidadeConverter")
public class EntidadeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        if (value != null) {
            return this.getAttributesFrom(component).get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent component, Object value) {
        if (value != null && !"".equals(value)) {
            Serializable codigo;
            if (value instanceof EntidadeGenerica) {
                EntidadeGenerica entity = (EntidadeGenerica) value;
                codigo = entity.getId();
            } else if (value instanceof EntidadeGenericaComId) {
                EntidadeGenericaComId entity = (EntidadeGenericaComId) value;
                codigo = entity.getId();
            } else {
                return "";
            }
            if (codigo != null) {
                // adiciona item como atributo do componente
                this.addAttribute(component, value);
                return String.valueOf(codigo);
            } else {
                return "";
            }
        }
        return "";
    }

    protected void addAttribute(UIComponent component, Object o) {
        Serializable codigo=null;
        if (o instanceof EntidadeGenerica) {
            EntidadeGenerica entity = (EntidadeGenerica) o;
            codigo = entity.getId();
        } else if (o instanceof EntidadeGenericaComId) {
            EntidadeGenericaComId entity = (EntidadeGenericaComId) o;
            codigo = entity.getId();
        }
        if (null != codigo) {
            this.getAttributesFrom(component).put(codigo.toString(), o);
        }
    }

    protected Map<String, Object> getAttributesFrom(UIComponent component) {
        return component.getAttributes();
    }
}
