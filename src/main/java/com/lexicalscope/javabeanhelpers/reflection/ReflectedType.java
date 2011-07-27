package com.lexicalscope.javabeanhelpers.reflection;

import java.util.List;

import org.hamcrest.Matcher;

public interface ReflectedType<T> {
	Class<T> getClassUnderReflection();

	List<ReflectedMethod> methods(Matcher<? super ReflectedMethod> methodMatcher);
}
