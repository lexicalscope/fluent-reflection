package com.lexicalscope.fluentreflection;

import java.util.List;

import org.hamcrest.Matcher;

interface ReflectedMembers<T> {
    List<FluentClass<?>> superclassesAndInterfaces(Matcher<? super FluentClass<?>> supertypeMatcher);

    List<FluentMethod> declaredMethods();

    List<FluentMethod> methods();
    List<FluentMethod> methods(Matcher<? super FluentMethod> methodMatcher);
    FluentMethod method(Matcher<? super FluentMethod> methodMatcher);

    List<FluentClass<?>> superclassesAndInterfaces();

    List<FluentConstructor<T>> constructors();
    List<FluentConstructor<T>> constructors(Matcher<? super FluentConstructor<?>> constructorMatcher);
    FluentConstructor<T> constructor(Matcher<? super FluentConstructor<?>> constructorMatcher);

    List<ReflectedField> declaredFields();

    List<ReflectedField> fields();
    List<ReflectedField> fields(ReflectionMatcher<? super ReflectedField> fieldMatcher);
    ReflectedField field(Matcher<? super ReflectedField> fieldMatcher);
}
