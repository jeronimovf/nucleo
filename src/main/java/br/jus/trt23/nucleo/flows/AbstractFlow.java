/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt23.nucleo.flows;

import br.jus.trt23.nucleo.controllers.AbstractController;
import br.jus.trt23.nucleo.entities.EntidadeGenerica;
import java.io.Serializable;
import javax.inject.Inject;
import lombok.Getter;
import org.primefaces.component.tabview.Tab;

/**
 *
 * @author j129-9
 */
public abstract class AbstractFlow<T extends EntidadeGenerica> implements Serializable{
    @Getter
    @Inject    
    protected AbstractController<T> controller;  
    private Tab activeTab;

    public Tab getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(Tab activeTab) {
        this.activeTab = activeTab;
    }
}
