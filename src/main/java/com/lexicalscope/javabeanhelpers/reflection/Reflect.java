package com.lexicalscope.javabeanhelpers.reflection;

/**
 * Main entry point for the reflection library
 */
public class Reflect {
	public static <T> ReflectedType<T> type(final Class<T> klass) {
		return new ReflectedTypeImpl<T>(klass);
	}
}
