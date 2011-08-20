/**
 * 
 */
package com.lexicalscope.javabeanhelpers.reflection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class TypeHierarchyCalculation {
	private final List<ReflectedType<?>> result = new ArrayList<ReflectedType<?>>();
	private final List<Class<?>> pending = new LinkedList<Class<?>>();

	List<ReflectedType<?>> interfacesAndSuperClass(final Class<?> klassToReflect) {
		pending.add(klassToReflect);
		while (!pending.isEmpty()) {
			processClass(pending.remove(0));
		}
		return result;
	}

	private void processClass(final Class<?> klassToReflect) {
		if (result.contains(klassToReflect)) {
			return;
		}

		if (klassToReflect.getSuperclass() != null && !klassToReflect.getSuperclass().equals(Object.class)) {
			pending.add(klassToReflect.getSuperclass());
		}
		final Class<?>[] interfaces = klassToReflect.getInterfaces();
		for (final Class<?> interfac3 : interfaces) {
			pending.add(interfac3);
		}
		result.add(ReflectedTypeImpl.create(klassToReflect));
	}
}