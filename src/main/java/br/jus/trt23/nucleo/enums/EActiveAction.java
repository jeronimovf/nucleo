/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt23.nucleo.enums;

/**
 *
 * @author j129-9
 */
public enum EActiveAction {
    NEW("New"),
    VIEW("View"),
    EDIT("Edit"),
    SEARCH("Search");
    private final String nome;

    public String getNome() {
        return nome;
    }
    
    EActiveAction(String nome){
        this.nome = nome;
    }

    @Override
    public String toString(){
        return getNome();
    }    
    
}
