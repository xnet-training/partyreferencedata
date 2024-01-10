package com.crossnetcorp.banking.partyreferencedata.infrastructure.annotation;
import java.lang.annotation.*;

/**
 *
 * @author ianache
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueConstraint {

    /**
     * Mensaje para motrar en validacion
     *
     * @return Mensaje de validacion
     */
    String message() default "El campo debe de ser unico";
}
