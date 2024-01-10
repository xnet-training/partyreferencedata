package com.crossnetcorp.banking.partyreferencedata.infrastructure.annotation;
import java.lang.annotation.*;
/**
 *
 * @author ianache
 */


/**
 * Restriccion para identificar unicidad
 * de una propiedad de objeto
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmptyConstraint {

    /**
     * Mensaje para motrar en validacion
     *
     * @return Mensaje de validacion
     */
    String message() default "El campo no debe de estar vac\u00edo";
}
