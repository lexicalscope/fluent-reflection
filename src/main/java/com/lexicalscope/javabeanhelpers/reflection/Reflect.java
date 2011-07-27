package com.lexicalscope.javabeanhelpers.reflection;

/**
 * Hello world!
 * 
 */
public class Reflect {
	public static <T> ReflectedType<T> type(final Class<T> klass) {
		return new ReflectedTypeImpl<T>(klass);
	}
}
