package com.lexicalscope.fluentreflection;

import java.util.ArrayList;
import java.util.List;

/**
 * Please do not use this class
 *
 * @author tim
 *
 * @param <T>
 */
public final class ListBuilder<T> {
    public static <T> ListBuilder<T> list(final T firstItem) {
        final ListBuilder<T> listBuilder = new ListBuilder<T>();
        listBuilder.add(firstItem);
        return listBuilder;
    }

    private final List<T> list = new ArrayList<T>();

    public ListBuilder<T> add(final T item) {
        list.add(item);
        return this;
    }

    public List<T> $() {
        return list;
    }
}
