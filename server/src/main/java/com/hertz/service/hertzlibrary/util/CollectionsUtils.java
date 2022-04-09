package com.hertz.service.hertzlibrary.util;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

public class CollectionsUtils {

    private CollectionsUtils() {}

    /**
     * Transforms a Collection<S> in a List<D> using the transformation function provided.
     */
    public static <S, D> List<D> convert(final Collection<S> source, final Function<? super S, ? extends D> mapper) {
        if (!isEmpty(source)) {
            return source
                    .stream()
                    .filter(Objects::nonNull)
                    .map(mapper)
                    .collect(toList());
        }

        return emptyList();
    }

}
