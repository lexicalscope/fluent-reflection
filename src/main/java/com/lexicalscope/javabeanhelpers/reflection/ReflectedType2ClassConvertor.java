package com.lexicalscope.javabeanhelpers.reflection;

import ch.lambdaj.function.convert.Converter;

class ReflectedType2ClassConvertor implements Converter<ReflectedType<?>, Class<?>> {
	@Override
	public Class<?> convert(final ReflectedType<?> from) {
		return from.getClassUnderReflection();
	}
}
