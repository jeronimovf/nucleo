/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt23.nucleo.validators;

import br.jus.trt23.nucleo.jsf.util.JsfUtil;
import java.util.Collection;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 *
 * @author j129-9
 */
public class ValidadorUtil{

    //valida a entidade e injeta as constraints violadas em mensagens do JSF
    //returna true se não tiverem violações ou false se o contrário
    //efetua todas as validações do grupo default
    public static <T> boolean validate(T entidade) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> constraintViolations
                = validator.validate(entidade);
        return injectValidationsInJsf(constraintViolations);
    }

    //valida a entidade e injeta as constraints violadas em mensagens do JSF
    //returna true se não tiverem violações ou false se o contrário
    //efetua todas as validações nos grupos    
    public static <T> boolean validate(T entidade, Class[] groups) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> constraintViolations
                = validator.validate(entidade, groups);
        return injectValidationsInJsf(constraintViolations);
    }

    private static <T> boolean injectValidationsInJsf(Collection<ConstraintViolation<T>> cvs) {
        if (!cvs.isEmpty()) {
            JsfUtil.addErrorMessagesFromConstraintViolations(cvs);
            return false;
        }
        return true;
    }
}
