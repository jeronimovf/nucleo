/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt23.nucleo.interfaces;

import br.jus.trt23.nucleo.entities.EntidadeGenerica;
import java.util.AbstractMap.SimpleEntry;

/**
 *
 * @author j129-9
 */
public interface IEntidadeQueDefineOContexto {
    //o map retorna o campo e a entidade que serão usados para consulta
    public SimpleEntry<String,EntidadeGenerica> getContexto();
}
