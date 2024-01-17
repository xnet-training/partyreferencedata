package com.crossnetcorp.banking.partyreferencedata.presentation;

import com.crossnetcorp.banking.partyreferencedata.application.ApplicationException;
import com.crossnetcorp.banking.partyreferencedata.infrastructure.ValidationException;
import com.crossnetcorp.banking.partyreferencedata.presentation.views.APIError;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author ccolome
 */
@ControllerAdvice
@RestController
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(APIExceptionHandler.class);

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        APIError response = new APIError();
        response.setCode(1000);
        response.setMessage(ex.getBindingResult().toString());
        response.setErrors(new ArrayList<>());

        ex.getBindingResult().getAllErrors().stream().forEach(error -> {

            APIError err = new APIError();
            err.setMessage(error.getDefaultMessage());
            err.setReason(error.getCode());
            err.setLocation(error.getObjectName());

            response.getErrors().add(err);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException ex
    ) {
        APIError response = new APIError();
        response.setCode(1000);
        response.setMessage(ex.getMessage());
        response.setErrors(new ArrayList<>());

        ex.getConstraintViolations().stream().forEach(error -> {
            APIError err = new APIError();
            err.setMessage(error.getMessage());
            err.setLocation(error.getPropertyPath().toString());
            response.getErrors().add(err);
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<Object> handleMissingRequestHeaderException(
            MissingRequestHeaderException ex
    ) {

        APIError response = new APIError();
        response.setCode(1001);
        response.setMessage(ex.getMessage());
        response.setReason(ex.getHeaderName());
        response.setErrors(new ArrayList<>());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex
    ) {

        APIError response = new APIError();

        if (ex.getCause() != null) {
            response.setCode(1002);
            response.setMessage(ex.getMessage());
            response.setLocation(ex.getCause().getMessage());
            response.setErrors(new ArrayList<>());
        } else {
            response.setCode(1002);
            response.setMessage(ex.getMessage());
            response.setErrors(new ArrayList<>());
        }

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidationException(
            ValidationException ex
    ) {

        APIError response = new APIError();

        if (ex.getCause() != null) {
            response.setCode(ex.getCode());
            response.setMessage(ex.getMessage());
            response.setLocation(ex.getCause().getMessage());
            
            Stream<APIError> errors = ex.getErrors().stream().map(e -> {
                APIError error = new APIError();
                error.setMessage( e.getDefaultMessage());
                return error;
            });
            
            response.setErrors(errors.collect(Collectors.toList()));
        } else {
            response.setCode(1002);
            response.setMessage(ex.getMessage());
            response.setErrors(new ArrayList<>());
        }

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<Object> handleApplicationException(ApplicationException ex) {

        APIError response = new APIError();

        response.setCode(ex.getCode());
        response.setMessage(ex.getMessage());
        response.setLocation(ex.getCause() != null ? ex.getCause().getMessage() : null);

        /*Stream<APIError> errors = ex.getErrors().stream().map(e -> {
            APIError error = new APIError();
            error.setMessage( e.getDefaultMessage());
            return error;
        });*/

        // response.setErrors(errors.collect(Collectors.toList()));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
