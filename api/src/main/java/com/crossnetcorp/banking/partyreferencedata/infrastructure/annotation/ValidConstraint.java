package com.crossnetcorp.banking.partyreferencedata.infrastructure.annotation;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author ianache
 */
@Documented
@Constraint(validatedBy = ValidConstraintImpl.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidConstraint {

    /**
     * Mensaje para motrar en validacion
     *
     * @return Mensaje de validacion
     */
    String message() default "Se han incumplido las restricciones";

    /**
     * Grupo de annotationes para validar
     *
     * @return Class<?>[]
     */
    Class<?>[] groups() default {};

    /**
     * Datos para el proceso de validacion
     *
     * @return Class<? extends Payload>[]
     */
    Class<? extends Payload>[] payload() default {};
}