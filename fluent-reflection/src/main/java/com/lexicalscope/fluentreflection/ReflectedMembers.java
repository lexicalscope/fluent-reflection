package com.lexicalscope.fluentreflection;

import java.util.List;

import org.hamcrest.Matcher;

interface ReflectedMembers<T> {
    List<ReflectedClass<?>> superclassesAndInterfaces(Matcher<? super ReflectedClass<?>> supertypeMatcher);

    List<ReflectedMethod> declaredMethods();

    List<ReflectedMethod> methods();
    List<ReflectedMethod> methods(Matcher<? super ReflectedMethod> methodMatcher);
    ReflectedMethod method(Matcher<? super ReflectedMethod> methodMatcher);

    List<ReflectedClass<?>> superclassesAndInterfaces();

    List<ReflectedConstructor<T>> constructors();
    List<ReflectedConstructor<T>> constructors(Matcher<? super ReflectedConstructor<?>> constructorMatcher);
    ReflectedConstructor<T> constructor(Matcher<? super ReflectedConstructor<?>> constructorMatcher);

    List<ReflectedField> declaredFields();

    List<ReflectedField> fields();
    List<ReflectedField> fields(ReflectionMatcher<? super ReflectedField> fieldMatcher);
}
