package com.lexicalscope.javabeanhelpers.reflection;

import static ch.lambdaj.Lambda.select;
import static org.hamcrest.Matchers.anything;

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

	private List<Class<?>> interfacesAndSuperClass(final Class<T> klassToReflect) {
		final List<Class<?>> result = new ArrayList<Class<?>>();
		if (klassToReflect.getSuperclass() != null && !klassToReflect.getSuperclass().equals(Object.class)) {
			result.add(klassToReflect.getSuperclass());
		}
		final Class<?>[] interfaces = klassToReflect.getInterfaces();
		for (final Class<?> interfac3 : interfaces) {
			result.add(interfac3);
		}
		result.add(klassToReflect);
		return result;
	}

	private List<ReflectedMethod> reflectedMethods() {
		if (reflectedMethods == null) {
			final List<ReflectedMethod> result = new ArrayList<ReflectedMethod>();

			final List<Class<?>> interfacesAndSuperClass = interfacesAndSuperClass(klass);
			for (final Class<?> klassToReflect : interfacesAndSuperClass) {
				result.addAll(getDeclaredMethodsOfClass(klassToReflect));
			}

			reflectedMethods = result;
		}
		return reflectedMethods;
	}

	private List<ReflectedMethod> getDeclaredMethodsOfClass(final Class<?> klassToReflect) {
		final List<ReflectedMethod> result = new ArrayList<ReflectedMethod>();
		final Method[] declaredMethods = klassToReflect.getDeclaredMethods();
		for (final Method method : declaredMethods) {
			result.add(new ReflectedMethodImpl(method));
		}
		return result;
	}

	@Override
	public Class<T> getClassUnderReflection() {
		return klass;
	}

	@Override
	public List<ReflectedMethod> methods(final Matcher<? super ReflectedMethod> methodMatcher) {
		return select(reflectedMethods(), methodMatcher);
	}

	@Override
	public List<ReflectedMethod> methods() {
		return methods(anything());
	}

	static <T> ReflectedType<?> create(final Class<T> from) {
		return new ReflectedTypeImpl<T>(from);
	}
}
