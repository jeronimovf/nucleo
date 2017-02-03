package br.jus.trt23.nucleo.handlers;

import java.util.ResourceBundle;

public class Proad {
    public String getProadUrlParaOProcesso(final br.jus.trt23.nucleo.entities.Proad processo) {
        ResourceBundle bundle = ResourceBundle.getBundle("configs");
        String urlBase = bundle.getString("proad_url");
        String protocoloNumeroFieldName
                = bundle.getString("proad_protocoloNumeroFieldName");
        String protocoloAnoFieldName
                = bundle.getString("proad_protocoloAnoFieldName");
        if (null != processo && null != processo.getProtocoloNumero()
                && null != processo.getProtocoloAno() && null != urlBase
                && null != protocoloAnoFieldName && null != protocoloNumeroFieldName) {
            StringBuilder sb = new StringBuilder();
            sb.append(urlBase);
            sb.append("?");
            sb.append(protocoloNumeroFieldName);
            sb.append("=");
            sb.append(processo.getProtocoloNumero());
            sb.append("&");
            sb.append(protocoloAnoFieldName);
            sb.append("=");
            sb.append(processo.getProtocoloAno());
            sb.append("&evento=y");
            return sb.toString();
        }
        return null;
    }   
}
