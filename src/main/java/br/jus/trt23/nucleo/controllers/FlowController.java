package br.jus.trt23.nucleo.controllers;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.flow.Flow;
import javax.faces.flow.FlowHandler;
import javax.inject.Named;

@Named
@RequestScoped
public class FlowController {

    public String flow(String flow) {
        FacesContext context = FacesContext.getCurrentInstance();
        FlowHandler handler = context.getApplication().getFlowHandler();
        Flow newFlow = handler.getFlow(context, "", flow);
        Flow currentFlow = handler.getCurrentFlow();
        handler.transition(context, currentFlow, newFlow , null, context.getViewRoot().getViewId());
        return "List";
    }
    
    public String flow(String flow, String step) {
        FacesContext context = FacesContext.getCurrentInstance();
        FlowHandler handler = context.getApplication().getFlowHandler();
        Flow newFlow = handler.getFlow(context, "", flow);
        Flow currentFlow = handler.getCurrentFlow();
        handler.transition(context, currentFlow, newFlow , null, context.getViewRoot().getViewId());
        if(null!=step && !step.isEmpty()){
            return step;
        }
        return "List";
    }    
}
