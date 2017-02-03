/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt23.nucleo.constraints;

import br.jus.trt23.nucleo.validators.VigenciaValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.ANNOTATION_TYPE,ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = { VigenciaValidator.class })
@Documented
public @interface VigenciaValida {

	String message() default "{vigencia.valida}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}