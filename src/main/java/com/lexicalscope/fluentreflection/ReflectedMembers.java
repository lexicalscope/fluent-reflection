package com.lexicalscope.fluentreflection;

import java.util.List;

import org.hamcrest.Matcher;

interface ReflectedMembers<T> {
    List<ReflectedMethod> declaredMethods();
    List<ReflectedMethod> methods();

    List<ReflectedClass<?>> superclassesAndInterfaces();

    List<ReflectedConstructor<T>> constructors();

    List<ReflectedMethod> methods(Matcher<? super ReflectedMethod> methodMatcher);

    ReflectedMethod method(Matcher<? super ReflectedMethod> methodMatcher);

    List<ReflectedConstructor<T>> constructors(Matcher<? super ReflectedConstructor<?>> constructorMatcher);

    ReflectedConstructor<T> constructor(Matcher<? super ReflectedConstructor<?>> constructorMatcher);

    List<ReflectedClass<?>> superclassesAndInterfaces(Matcher<? super ReflectedClass<?>> supertypeMatcher);

}
