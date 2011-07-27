package com.lexicalscope.javabeanhelpers.reflection;

import static ch.lambdaj.Lambda.select;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;

/**
 * Not thread safe
 * 
 * @author tim
 * 
 * @param <T>
 */
class ReflectedTypeImpl<T> implements ReflectedType<T> {
	private final Class<T> klass;
	private List<ReflectedMethod> reflectedMethods;

	public ReflectedTypeImpl(final Class<T> klass) {
		this.klass = klass;
	}

	private List<ReflectedMethod> reflectedMethods() {
		if (reflectedMethods == null) {
			final List<ReflectedMethod> result = new ArrayList<ReflectedMethod>();
			final Method[] declaredMethods = klass.getDeclaredMethods();
			for (final Method method : declaredMethods) {
				result.add(new ReflectedMethodImpl(method));
			}
			reflectedMethods = result;
		}
		return reflectedMethods;
	}

	@Override
	public Class<T> getClassUnderReflection() {
		return klass;
	}

	@Override
	public List<ReflectedMethod> methods(final Matcher<? super ReflectedMethod> methodMatcher) {
		return select(reflectedMethods(), methodMatcher);
	}

	static <T> ReflectedType<?> create(final Class<T> from) {
		return new ReflectedTypeImpl<T>(from);
	}
}
