/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt23.nucleo.controllers;

import br.jus.trt23.nucleo.entities.Proad;
import br.jus.trt23.nucleo.handlers.CnpjCpf;
import br.jus.trt23.nucleo.handlers.Jsf;
import br.jus.trt23.nucleo.handlers.Mensagem;
import br.jus.trt23.nucleo.handlers.Tempo;
import br.jus.trt23.nucleo.handlers.Texto;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author j129-9
 */
@Named
@ApplicationScoped
@RequiredArgsConstructor
@Getter
public class Util implements Serializable {

    @Inject
    private Texto texto;
    @Inject
    private Tempo tempo;
    @Inject
    private Proad proad;
    @Inject
    private Mensagem mensagem;
    @Inject
    private CnpjCpf cnpjCpf;
    @Inject
    private Jsf jsf;

}
