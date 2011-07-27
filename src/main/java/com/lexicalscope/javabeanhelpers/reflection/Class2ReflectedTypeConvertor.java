package com.lexicalscope.javabeanhelpers.reflection;

import ch.lambdaj.function.convert.Converter;

class Class2ReflectedTypeConvertor implements Converter<Class<?>, ReflectedType<?>> {
	@Override
	public ReflectedType<?> convert(final Class<?> from) {
		return ReflectedTypeImpl.create(from);
	}
}
