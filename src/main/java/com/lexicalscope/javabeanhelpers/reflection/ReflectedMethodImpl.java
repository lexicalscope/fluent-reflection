package com.lexicalscope.javabeanhelpers.reflection;

import java.lang.reflect.Method;
import java.util.List;

import ch.lambdaj.Lambda;

class ReflectedMethodImpl implements ReflectedMethod {
	private final Method method;

	public ReflectedMethodImpl(final Method method) {
		this.method = method;
	}

	@Override
	public String getName() {
		return method.getName();
	}

	@Override
	public List<ReflectedType<?>> getArgumentTypes() {
		return Lambda.convert(method.getParameterTypes(), new Class2ReflectedTypeConvertor());
	}

	@Override
	public String toString() {
		return method.toString();
	}
}
