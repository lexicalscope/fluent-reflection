package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.*;

import java.util.List;

import org.hamcrest.Matcher;

import com.google.inject.TypeLiteral;

final class ReflectedMembersImpl<T> implements ReflectedMembers<T> {
    private final ReflectedSuperclassesAndInterfaces<T> superclassesAndInterfaces;
    private final ReflectedMethods<T> methods;
    private final ReflectedConstructors<T> constructors;
    private final ReflectedFields<T> fields;
    private final Class<T> klass;

    public ReflectedMembersImpl(final ReflectedTypeFactory reflectedTypeFactory, final TypeLiteral<T> typeLiteral) {
        this.klass = (Class<T>) typeLiteral.getRawType();
        this.superclassesAndInterfaces =
                new ReflectedSuperclassesAndInterfacesImpl<T>(reflectedTypeFactory, typeLiteral);
        this.methods = new ReflectedMethodsImpl<T>(reflectedTypeFactory, typeLiteral, superclassesAndInterfaces);
        this.constructors = new ReflectedConstructorsImpl<T>(reflectedTypeFactory, typeLiteral);
        this.fields = new ReflectedFieldsImpl<T>(reflectedTypeFactory, typeLiteral, superclassesAndInterfaces);
    }

    @Override public List<FluentConstructor<T>> constructors() {
        return constructors.constructors();
    }

    @Override public List<FluentMethod> methods() {
        return methods.methods();
    }

    @Override public List<FluentMethod> declaredMethods() {
        return methods.declaredMethods();
    }

    @Override public List<FluentClass<?>> superclassesAndInterfaces() {
        return superclassesAndInterfaces.superclassesAndInterfaces();
    }

    @Override public List<FluentMethod> methods(final Matcher<? super FluentMethod> methodMatcher) {
        return select(methods(), methodMatcher);
    }

    @Override public FluentMethod method(final Matcher<? super FluentMethod> methodMatcher) {
        final FluentMethod selectedMethod = selectFirst(methods(), methodMatcher);
        if (selectedMethod == null) {
            throw new MethodNotFoundException(klass, methodMatcher);
        }
        return selectedMethod;
    }

    @Override public List<FluentConstructor<T>> constructors(
            final Matcher<? super FluentConstructor<?>> constructorMatcher) {
        return select(constructors(), constructorMatcher);
    }

    @Override public FluentConstructor<T> constructor(
            final Matcher<? super FluentConstructor<?>> constructorMatcher) {
        return selectFirst(constructors(), constructorMatcher);
    }

    @Override public List<FluentClass<?>> superclassesAndInterfaces(
            final Matcher<? super FluentClass<?>> supertypeMatcher) {
        return select(superclassesAndInterfaces(), supertypeMatcher);
    }

    @Override public List<FluentField> declaredFields() {
        return fields.declaredFields();
    }

    @Override public List<FluentField> fields() {
        return fields.fields();
    }

    @Override public List<FluentField> fields(final ReflectionMatcher<? super FluentField> fieldMatcher) {
        return select(fields(), fieldMatcher);
    }

    @Override public FluentField field(final Matcher<? super FluentField> fieldMatcher) {
        final FluentField selectedMethod = selectFirst(fields(), fieldMatcher);
        if (selectedMethod == null) {
            throw new FieldNotFoundException(klass, fieldMatcher);
        }
        return selectedMethod;
    }
}
