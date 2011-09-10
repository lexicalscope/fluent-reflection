package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.*;

import java.util.List;

import org.hamcrest.Matcher;

class ReflectedMembersImpl<T> implements ReflectedMembers<T> {
    private final ReflectedSuperclassesAndInterfaces<T> superclassesAndInterfaces;
    private final ReflectedMethods<T> methods;
    private final ReflectedConstructors<T> constructors;

    public ReflectedMembersImpl(final Class<T> klass) {
        this.superclassesAndInterfaces = new ReflectedSuperclassesAndInterfacesImpl<T>(klass);
        this.methods = new ReflectedMethodsImpl<T>(klass, superclassesAndInterfaces);
        this.constructors = new ReflectedConstructorsImpl<T>(klass);
    }

    @Override
    public List<ReflectedConstructor<T>> constructors() {
        return constructors.constructors();
    }

    @Override
    public List<ReflectedMethod> methods() {
        return methods.methods();
    }

    @Override
    public List<ReflectedType<?>> superclassesAndInterfaces() {
        return superclassesAndInterfaces.superclassesAndInterfaces();
    }

    @Override
    public List<ReflectedMethod> methods(final Matcher<? super ReflectedMethod> methodMatcher) {
        return select(methods(), methodMatcher);
    }

    @Override
    public ReflectedMethod method(final Matcher<? super ReflectedMethod> methodMatcher) {
        return selectFirst(methods(), methodMatcher);
    }

    @Override
    public List<ReflectedConstructor<T>> constructors(
            final Matcher<? super ReflectedConstructor<?>> constructorMatcher) {
        return select(constructors(), constructorMatcher);
    }

    @Override
    public ReflectedConstructor<T> constructor(
            final Matcher<? super ReflectedConstructor<?>> constructorMatcher) {
        return selectFirst(constructors(), constructorMatcher);
    }

    @Override
    public List<ReflectedType<?>> superclassesAndInterfaces(final Matcher<? super ReflectedType<?>> supertypeMatcher) {
        return select(superclassesAndInterfaces(), supertypeMatcher);
    }
}
