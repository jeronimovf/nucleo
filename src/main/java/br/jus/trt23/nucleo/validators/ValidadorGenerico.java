/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt23.nucleo.validators;

import br.jus.trt23.nucleo.entities.EntidadeGenerica;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author j129-9
 * @param <A>
 * @param <T>
 */
public abstract class ValidadorGenerico<A extends Annotation, T extends EntidadeGenerica> implements ConstraintValidator<A, T> {

    protected A constraintAnnotation;
    protected T value;

    public abstract boolean passedCustomValidation();
    
    @Override
    public void initialize(A constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    private String getConstraintMessage() {
        Class aClass = constraintAnnotation.getClass();
        try {
            Method mMessage = aClass.getMethod("message");
            Object oMessage = mMessage.invoke(constraintAnnotation);
            if (oMessage instanceof String) {
                return (String) oMessage;
            }
        } catch (IllegalAccessException | IllegalArgumentException |
                InvocationTargetException | NoSuchMethodException |
                SecurityException ex) {
            Logger.getLogger(ValidadorGenerico.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {
        this.value = value;

        Boolean resultado = passedCustomValidation();

        return resultado;
    }

}
