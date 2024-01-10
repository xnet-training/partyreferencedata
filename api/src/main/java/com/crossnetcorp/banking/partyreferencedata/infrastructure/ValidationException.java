package com.crossnetcorp.banking.partyreferencedata.infrastructure;

/**
 *
 * @author ianache
 */
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintDeclarationException;
import java.util.List;

public class ValidationException extends ConstraintDeclarationException {

    private Integer code;
    private List<ObjectError> errors;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(String message, Integer code, List<ObjectError> errors) {
        super(message);
        this.code = code;
        this.errors = errors;
    }

    public ValidationException(String message, List<ObjectError> errors) {
        super(message);
        this.errors = errors;
    }

    public Integer getCode() {
        return code;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }
}