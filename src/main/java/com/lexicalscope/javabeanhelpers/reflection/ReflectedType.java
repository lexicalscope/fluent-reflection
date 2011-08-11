package com.lexicalscope.javabeanhelpers.reflection;

import java.util.List;

import org.hamcrest.Matcher;

public interface ReflectedType<T> {

	/**
	 * @return the class being reflected
	 */
	Class<T> getClassUnderReflection();

	/**
	 * Find all methods matching the supplied matcher
	 * 
	 * @param methodMatcher
	 *            matches the methods
	 * 
	 * @return The methods matching the supplied matcher
	 */
	List<ReflectedMethod> methods(Matcher<? super ReflectedMethod> methodMatcher);

	/**
	 * All methods
	 * 
	 * @return all the methods
	 */
	List<ReflectedMethod> methods();
}
