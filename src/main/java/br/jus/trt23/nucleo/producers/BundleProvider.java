/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt23.nucleo.producers;

import br.jus.trt23.nucleo.qualifiers.ConfigBundle;
import br.jus.trt23.nucleo.qualifiers.MessageBundle;
import br.jus.trt23.nucleo.qualifiers.Slf4jLogger;
import java.util.Enumeration;
import java.util.ResourceBundle;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.slf4j.Logger;

/**
 *
 * @author j129-9
 */
@ApplicationScoped
public class BundleProvider {

    private ResourceBundle msgBundle;
    private ResourceBundle cfgBundle;
    
    @Inject
    @Slf4jLogger
    Logger logger;

    @Produces
    @MessageBundle
    @Default
    public ResourceBundle getMsgBundle() {
        if (msgBundle == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            msgBundle = context.getApplication().getResourceBundle(context, "messages");
            logger.debug("Bundle: ".concat(msgBundle.getBaseBundleName()));
            logger.debug("Bundle entries: ");
            logger.debug(bundleToString(msgBundle));
        }
        return msgBundle;
    }

    @Produces
    @ConfigBundle
    public ResourceBundle getCfgBundle() {
        if (cfgBundle == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            cfgBundle = context.getApplication().getResourceBundle(context, "configs");
        }
        return cfgBundle;
    }
    
    public String bundleToString(ResourceBundle bundle){
        StringBuilder printSB = new StringBuilder();
        Enumeration bundleKeys = bundle.getKeys();
        
        while(bundleKeys.hasMoreElements()){
            String key = (String)bundleKeys.nextElement();            
            printSB.append("\t").append(key).append("=>").append(bundle.getString(key)).append("\n");            
        }
        
        return printSB.toString();
    }
}
