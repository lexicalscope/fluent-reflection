package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.*;

import java.util.List;

import org.hamcrest.Matcher;

class ReflectedMembersImpl<T> implements ReflectedMembers<T> {
    private final ReflectedSuperclassesAndInterfaces<T> superclassesAndInterfaces;
    private final ReflectedMethods<T> methods;
    private final ReflectedConstructors<T> constructors;
    private final Class<T> klass;

    public ReflectedMembersImpl(final ReflectedTypeFactory reflectedTypeFactory, final Class<T> klass) {
        this.klass = klass;
        this.superclassesAndInterfaces = new ReflectedSuperclassesAndInterfacesImpl<T>(reflectedTypeFactory, klass);
        this.methods = new ReflectedMethodsImpl<T>(reflectedTypeFactory, klass, superclassesAndInterfaces);
        this.constructors = new ReflectedConstructorsImpl<T>(reflectedTypeFactory, klass);
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
    public List<ReflectedClass<?>> superclassesAndInterfaces() {
        return superclassesAndInterfaces.superclassesAndInterfaces();
    }

    @Override
    public List<ReflectedMethod> methods(final Matcher<? super ReflectedMethod> methodMatcher) {
        return select(methods(), methodMatcher);
    }

    @Override
    public ReflectedMethod method(final Matcher<? super ReflectedMethod> methodMatcher) {
        final ReflectedMethod selectedMethod = selectFirst(methods(), methodMatcher);
        if (selectedMethod == null) {
            throw new MethodNotFoundException(klass, methodMatcher);
        }
        return selectedMethod;
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
    public List<ReflectedClass<?>> superclassesAndInterfaces(final Matcher<? super ReflectedClass<?>> supertypeMatcher) {
        return select(superclassesAndInterfaces(), supertypeMatcher);
    }
}
