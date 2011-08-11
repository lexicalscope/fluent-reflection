package com.lexicalscope.javabeanhelpers.reflection;

import java.util.List;

public interface ReflectedMethod {
	String getName();

	List<ReflectedType<?>> getArgumentTypes();

	ReflectedType<?> getDeclaringClass();
}
