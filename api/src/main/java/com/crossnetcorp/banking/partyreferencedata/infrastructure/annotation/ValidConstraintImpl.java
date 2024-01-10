package com.crossnetcorp.banking.partyreferencedata.infrastructure.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;

import com.crossnetcorp.banking.partyreferencedata.infrastructure.ValidationException;
import com.crossnetcorp.banking.partyreferencedata.infrastructure.util.ObjectUtil;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.crossnetcorp.banking.partyreferencedata.infrastructure.util.JPAUtil.exist;
import static com.crossnetcorp.banking.partyreferencedata.infrastructure.util.ObjectUtil.getResourceName;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 *
 * @author ianache
 */
@Slf4j
public class ValidConstraintImpl implements ConstraintValidator<ValidConstraint, Object> {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String ERROR_LOCATION = "Registro nro: %s, " + " campo: %s";

    private static final String ERROR_EXIST_IN_DB = "%s: %s, ya existe en la base de datos";

    private static final String ERROR_NOT_EXIST_IN_DB = "%s: %s, no existe en la base de datos";
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        } else if (value instanceof Collection) {
            validList(new ArrayList<>((Collection) value));
        } else {
            validObject(value);
        }
        return true;
    }

    private void validList(List<?> values) {
        List<ObjectError> errors = new ArrayList<>();

        String listName = "List";
        String objectName = getResourceName(values.get(0));

        String message = new StringBuilder()
                .append(listName)
                .append(":")
                .append(objectName)
                .append(", ha incumplido las restricciones")
                .toString();

        Set<Map> uniqueElements = new HashSet<>();

        for (int i = 0; i < values.size(); i++) {
            validRequiredOrEmpty(i, values.get(i), errors);
            validDuplicate(uniqueElements, i, values.get(i), errors);
            validExistence(i, values.get(i), errors);

        }

        if (errors.size() > 0) {
            throw new ValidationException(message, errors);
        }
    }
    private void validObject(Object value) {
        List<ObjectError> errors = new ArrayList<>();

        String message = new StringBuilder()
                .append(getResourceName(value))
                .append(", ha incumplido las restricciones")
                .toString();

        validRequiredOrEmpty(0, value, errors);
        validExistence(0, value, errors);

        if (errors.size() > 0) {
            throw new ValidationException(message, errors);
        }
    }

    private void validDuplicate(
            Set<Map> uniqueElements,
            Integer index,
            Object value,
            List<ObjectError> errors) {

        String message = "Se encontró registro duplicado: %s";

        Arrays.stream(value.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(UniqueConstraint.class))
                .forEach(f -> {
                    try {
                        f.setAccessible(true);
                        if (f.get(value) != null) {
                            String k = f.getName();
                            String v = f.get(value).toString();
                            if (!uniqueElements.add(Map.of(k, v))) {
                                errors.add(new ObjectError(
                                        String.format(ERROR_LOCATION, index, k),
                                        String.format(message, v)
                                ));
                            }
                        }
                        f.setAccessible(false);
                    } catch (IllegalAccessException e) {
                        log.debug(e.getMessage());
                        throw new RuntimeException(e);
                    } catch (Exception e) {
                        log.debug(e.getMessage());
                        throw e;
                    }
                });
    }

    private void validExistence(Integer index, Object value, List<ObjectError> errors) {

        Arrays.stream(value.getClass().getDeclaredFields())
                .filter(f -> f.getAnnotations().length > 0)
                .forEach(f -> {
                            try {
                                f.setAccessible(true);
                                if (f.get(value) != null) {
                                    if (f.isAnnotationPresent(UniqueConstraint.class)) {
                                        validUniqueConstraint(index, f, value, errors);
                                    }
                                    if (f.isAnnotationPresent(ForeingKeyConstraint.class)) {
                                        validForeingKeyConstraint(index, f, value, errors);
                                    }
                                }
                                f.setAccessible(false);
                            } catch (IllegalAccessException e) {
                                log.error(e.getMessage());
                                throw new RuntimeException(e);
                            } catch (Exception e) {
                                log.debug(e.getMessage());
                                throw new RuntimeException(e);
                            }
                        }
                );
    }
    private void validUniqueConstraint(
            Integer index,
            Field f,
            Object value,
            List<ObjectError> errors
    ) throws IllegalAccessException {

        UniqueConstraint a = f.getAnnotation(UniqueConstraint.class);

        String k = f.getName();
        String v = f.get(value).toString();
        if (exist(entityManager, value.getClass(), k, v)) {
            errors.add(new ObjectError(
                    String.format(ERROR_LOCATION, index, k),
                    String.format(ERROR_EXIST_IN_DB, a.message(), v)
            ));
        }
    }

    private void validRequiredOrEmpty(
            Integer index,
            Object value,
            List<ObjectError> errors
    ) {
        Arrays.stream(value.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(RequiredConstraint.class)
                || f.isAnnotationPresent(EmptyConstraint.class))
                .forEach(f -> {
                    try {
                        f.setAccessible(true);
                        String k = f.getName();
                        if (f.isAnnotationPresent(RequiredConstraint.class)){
                            if (f.getType().equals(String.class)){
                                if (f.get(value) == null){
                                    RequiredConstraint r = f.getAnnotation(RequiredConstraint.class);
                                    errors.add(new ObjectError(
                                                String.format(ERROR_LOCATION, index, k),
                                                r.message()
                                            )
                                    );
                                }
                            }
                            /*else {
                                if (f.getType().getSimpleName().equals("ModeloTable")){
                                    ModeloTable modeloTable = (ModeloTable) f.get(value);
                                    if (modeloTable.getId() == null){
                                        RequiredConstraint r = f.getAnnotation(RequiredConstraint.class);
                                        errors.add(new ObjectError(
                                                        String.format(ERROR_LOCATION, index, k),
                                                        r.message()
                                                )
                                        );
                                    }
                                }
                            }*/
                        }

                        if (f.isAnnotationPresent(EmptyConstraint.class)){
                            EmptyConstraint e = f.getAnnotation(EmptyConstraint.class);
                            if (f.get(value) != null){
                                if (f.get(value).toString().equals("")) {
                                    errors.add(new ObjectError(
                                                    String.format(ERROR_LOCATION, index, k),
                                                    e.message()
                                            )
                                    );
                                }
                            }
                        }
                        f.setAccessible(false);
                    } catch (IllegalAccessException e) {
                        log.debug(e.getMessage());
                        throw new RuntimeException(e);
                    } catch (Exception e) {
                        log.debug(e.getMessage());
                        throw e;
                    }
                });

    }

    private void validForeingKeyConstraint(
            Integer index,
            Field f,
            Object value,
            List<ObjectError> errors
    ) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        ForeingKeyConstraint a = f.getAnnotation(ForeingKeyConstraint.class);

        Object fieldValue = f.get(value);

        String k = f.getName();
        String v = fieldValue.toString();

        if (fieldValue.getClass().isAnnotationPresent(Entity.class)) {
            Object fieldInstance = fieldValue.getClass().getDeclaredConstructor().newInstance();

            if (!fieldInstance.equals(fieldValue)) {

                v = Optional.ofNullable(
                        ObjectUtil.getObjectValue(fieldValue, a.field()).toString()
                ).orElse("");

                if (!exist(entityManager, a.entity(), a.field(), v)) {
                    errors.add(new ObjectError(
                            String.format(ERROR_LOCATION, index, k),
                            String.format(ERROR_NOT_EXIST_IN_DB, a.message(), v)
                    ));
                }
            }

        } else {
            if (!exist(entityManager, a.entity(), a.field(), v)) {
                errors.add(new ObjectError(
                        String.format(ERROR_LOCATION, index, k),
                        String.format(ERROR_NOT_EXIST_IN_DB, a.message(), v)
                ));
            }
        }
    }
}