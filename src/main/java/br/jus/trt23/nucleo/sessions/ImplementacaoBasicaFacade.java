/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt23.nucleo.sessions;


import br.jus.trt23.nucleo.entities.EntidadeGenerica;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author j129-9
 */
@Stateless
public class ImplementacaoBasicaFacade extends AbstractFacade<EntidadeGenerica> {

    @Inject
    private EntityManager em;

    public ImplementacaoBasicaFacade() {
        super(EntidadeGenerica.class);
    }

    @Override
    public List<EntidadeGenerica> complete(String criteria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
