package com.crossnetcorp.banking.partyreferencedata.infrastructure.util;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.validation.ObjectError;

import com.crossnetcorp.banking.partyreferencedata.infrastructure.ValidationException;
import com.crossnetcorp.banking.partyreferencedata.infrastructure.annotation.SearchParam;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class JPAUtil {

    /**
     * Simple select por parametros
     *
     * @param entityManager Entity Manager
     * @param clazz         Clase de referencia
     * @param params        Key-Velue
     * @return result
     */
    public static Query select(EntityManager entityManager, Class<?> clazz, Map<String, String> params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<?> query = builder.createQuery(clazz);
        Root<?> root = query.from(clazz);

        if (params != null) {
            query.where(params.entrySet().stream()
                    .filter(p -> p.getValue() != null && !"".equals(p.getValue()))
                    .map(f -> builder.equal(root.get(f.getKey()), f.getValue()))
                    .collect(Collectors.toList())
                    .toArray(new Predicate[]{})
            );
        }

        return entityManager.createQuery(query);
    }

    /**
     * Simple update
     *
     * @param entityManager Entity Manager
     * @param clazz         Clase de referencia
     * @param object        objecto
     * @return result
     */
    public static <T> int update(EntityManager entityManager, Class<T> clazz, T object) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<T> query = builder.createCriteriaUpdate(clazz);
        Root<T> root = query.from(clazz);

        var predicates = new ArrayList<Predicate>();

        Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> {
                    try {
                        f.setAccessible(true);
                        return f.get(object) != null;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }).forEach(f -> {
                    try {
                        Object fieldValue = f.get(object);

                        if (f.isAnnotationPresent(Id.class)) {

                            predicates.add(builder.equal(root.get(f.getName()).as(String.class), fieldValue));

                        } else if (fieldValue.getClass().isAnnotationPresent(Resource.class)) {

                            Object instance = fieldValue.getClass().getDeclaredConstructor().newInstance();

                            if (!Objects.equals(fieldValue, instance) && f.isAnnotationPresent(JoinColumn.class)) {

                                JoinColumn jc = f.getAnnotation(JoinColumn.class);
                                Object ov = ObjectUtil.getObjectValue(fieldValue, jc.referencedColumnName());
                                query.set(f.getName(), ov);

                            }

                        } else {

                            query.set(f.getName(), fieldValue);

                        }
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });

        query.where(predicates.toArray(new Predicate[]{}));

        return entityManager.createQuery(query).executeUpdate();
    }

    /**
     * @param entityManager Entity Manager
     * @param clazz         Clase de referencia
     * @param key           Key
     * @param value         value
     * @return Existe elemento en la DB
     */
    public static boolean exist(EntityManager entityManager, Class<?> clazz, String key, String value) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<?> root = query.from(clazz);
        query.select(builder.count(root));
        query.where(builder.equal(root.get(key), value));
        return entityManager.createQuery(query).getSingleResult().intValue() > 0;
    }

    /**
     * @param entityManager Entity Manager
     * @param clazz         Clase de referencia
     * @param builder       CriteriaBuilder
     * @param predicates    List Predicates
     * @return Cuenta elementos en DB
     */
    public static Long count(EntityManager entityManager, Class<?> clazz, CriteriaBuilder builder, List<Predicate> predicates) {
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<?> root = query.from(clazz);
        query.select(builder.count(root));
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[]{}));
        }
        return entityManager.createQuery(query).getSingleResult();
    }

    /**
     * @param clazz          Clase de referencia
     * @param builder        CriteriaBuilder
     * @param root           Root select
     * @param genericParam   Parametro general
     * @param specificParams Parametros especificos
     * @return Lista de Predicates(condiciones) para consulta en DB
     */
    public static <T> List<Predicate> resolvePredicates(
            Class<?> clazz,
            CriteriaBuilder builder,
            Root<?> root,
            String genericParam,
            T specificParams,
            List<Instant> datesRange
    ) {
        List<Predicate> predicates = new ArrayList<>();

        Map<Boolean, List<Field>> params = Arrays.stream(clazz.getDeclaredFields())
                //Filtrar solo los campos que posean la anotacion de SearchParam
                .filter(field -> field.isAnnotationPresent(SearchParam.class))
                .collect(Collectors.partitioningBy(f -> {
                    try {
                        f.setAccessible(true);
                        //solo aquellos que posean un valor en su campo
                        return f.get(specificParams) != null;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }));

        if (specificParams != null) {
            predicates.add(
                    builder.and(
                            params.get(true).stream()
                                    .map(f -> {
                                        try {
                                            return f.isAnnotationPresent(Enumerated.class) ?
                                                    builder.like(root.get(f.getName()).as(String.class), f.get(specificParams).toString()) :
                                                    builder.like(root.get(f.getName()).as(String.class), f.get(specificParams).toString() + "%");
                                        } catch (IllegalAccessException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }).collect(Collectors.toList())
                                    .toArray(new Predicate[]{})
                    )
            );
        }

        if (genericParam != null) {
            predicates.add(
                    builder.or(
                            params.get(false).stream()
                                    .map(f -> builder.like(root.get(f.getName()).as(String.class), "%" + genericParam + "%"))
                                    .collect(Collectors.toList())
                                    .toArray(new Predicate[]{})
                    )
            );
        }

        if (validDatesRange(datesRange)) {

            Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(CreationTimestamp.class))
                    .map(Field::getName)
                    .findFirst()
                    .ifPresent(creationField -> {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                .withZone(ZoneId.systemDefault());

                        var inicioNotNull = (datesRange.get(0) != null && datesRange.get(1) == null);
                        var finNotNull = (datesRange.get(0) == null && datesRange.get(1) != null);

                        if (!datesRange.contains(null)) {
                            predicates.add(
                                    builder.between(
                                            root.get(creationField).as(String.class),
                                            formatter.format(datesRange.get(0)),
                                            formatter.format(datesRange.get(1))
                                    )
                            );
                        } else if (inicioNotNull) {
                            predicates.add(
                                    builder.greaterThanOrEqualTo(
                                            root.get(creationField).as(String.class),
                                            formatter.format(datesRange.get(0))
                                    )
                            );
                        } else if (finNotNull) {
                            predicates.add(
                                    builder.lessThanOrEqualTo(
                                            root.get(creationField).as(String.class),
                                            formatter.format(datesRange.get(1))
                                    )
                            );
                        }
                    });
        }

        return predicates;
    }

    private static boolean validDatesRange(List<Instant> datesRange) {

        List<ObjectError> errors = new ArrayList<>();

        var message = "Debe ingresar el rango de fechas correctamente";

        if (datesRange == null) {
            return false;
        }
        if (!datesRange.contains(null) && (datesRange.get(0).isAfter(datesRange.get(1)))) {
            errors.add(new ObjectError(
                    "La fecha inicio es superior a la fecha fin",
                    null
            ));
            throw new ValidationException(message, errors);
        }

        return true;
    }
}
