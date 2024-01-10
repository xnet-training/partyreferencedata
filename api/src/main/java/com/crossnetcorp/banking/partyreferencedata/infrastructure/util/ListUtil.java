package com.crossnetcorp.banking.partyreferencedata.infrastructure.util;

/**
 *
 * @author ianache
 */
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListUtil {

    /**
     * @param <T>    Clase de referencia
     * @param source listado de datos a particionar
     * @param length cantidad de datos por batch
     * @return datos particionados en batch
     */
    public static <T> List<List<T>> partition(List<T> source, Integer length) {

        if (length <= 0) {
            throw new IllegalArgumentException("length = " + length);
        }

        Integer size = source.size();

        if (size <= 0) {
            return new ArrayList<>();
        }

        Integer parts = (size - 1) / length;

        return IntStream
                .range(0, parts + 1)
                .mapToObj(n
                        -> source.subList(n * length, n == parts ? size : (n + 1) * length)
                ).collect(Collectors.toList());
    }
}
