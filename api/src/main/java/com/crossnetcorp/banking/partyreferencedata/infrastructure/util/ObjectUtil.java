package com.crossnetcorp.banking.partyreferencedata.infrastructure.util;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Arrays;

public class ObjectUtil {

    /**
     * @param <T> Clase de referencia
     * @param source objeto fuente
     * @param destination objeto destino
     * @return objeto copiado
     */
    public static <T> T copy(T source, T destination) {
        try {
            for (Field sourceField : source.getClass().getDeclaredFields()) {

                sourceField.setAccessible(true);
                Object sourceFieldValue = sourceField.get(source);

                if (sourceFieldValue != null) {

                    Field destinationField = destination.getClass().getDeclaredField(
                            sourceField.getName()
                    );
                    destinationField.setAccessible(true);

                    if (!sourceFieldValue.getClass().isAnnotationPresent(Resource.class)) {
                        destinationField.set(destination, sourceField.get(source));
                    }
                }

            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        return destination;
    }

    /**
     * @param object<T> Clase de referencia
     * @return Nombre del recurso
     */
    public static <T> String getResourceName(T object) {
        return (object.getClass().isAnnotationPresent(Resource.class))
                ? object.getClass().getAnnotation(Resource.class).name()
                : object.getClass().getSimpleName();
    }

    /**
     * @param object<T> Clase de referencia
     * @param key Valor a obtener
     * @return Nombre del recurso
     */
    public static <T> Object getObjectValue(T object, String key) {

        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(f -> f.getName().equals(key))
                .map(f -> {
                    try {
                        f.setAccessible(true);
                        return f.get(object);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
                ).findFirst().orElse(null);
    }

}
