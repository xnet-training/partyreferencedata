package com.crossnetcorp.banking.partyreferencedata.infrastructure.annotation;
import java.lang.annotation.*;
/**
 *
 * @author ianache
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ForeingKeyConstraint {

    /**
     * Entidad Foreing para validacion
     *
     * @return Class<?>
     */
    Class<?> entity() default void.class;

    /**
     * Campo a validar
     *
     * @return String
     */
    String field() default "id";

    /**
     * Mensaje para motrar en validacion
     *
     * @return Mensaje de validacion
     */
    String message() default "Debe de existir una referancia valida";
}
