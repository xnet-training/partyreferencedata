package com.crossnetcorp.banking.partyreferencedata.infrastructure.annotation;

/**
 *
 * @author ianache
 */
import java.lang.annotation.*;

/**
 * Annotacion para identificar parametro de busqueda
 * de una propiedad de objeto
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchParam {

}
