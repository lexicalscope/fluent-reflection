package com.lexicalscope.javabeanhelpers.reflection;

import java.util.ArrayList;
import java.util.List;

final class ListBuilder<T> {
	static <T> ListBuilder<T> list(final T firstItem) {
		final ListBuilder<T> listBuilder = new ListBuilder<T>();
		listBuilder.add(firstItem);
		return listBuilder;
	}

	private final List<T> list = new ArrayList<T>();

	ListBuilder<T> add(final T item) {
		list.add(item);
		return this;
	}

	List<T> build() {
		return list;
	}
}
