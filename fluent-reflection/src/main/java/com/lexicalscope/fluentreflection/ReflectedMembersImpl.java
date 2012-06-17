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

    @Override public List<ReflectedConstructor<T>> constructors() {
        return constructors.constructors();
    }

    @Override public List<ReflectedMethod> methods() {
        return methods.methods();
    }

    @Override public List<ReflectedMethod> declaredMethods() {
        return methods.declaredMethods();
    }

    @Override public List<ReflectedClass<?>> superclassesAndInterfaces() {
        return superclassesAndInterfaces.superclassesAndInterfaces();
    }

    @Override public List<ReflectedMethod> methods(final Matcher<? super ReflectedMethod> methodMatcher) {
        return select(methods(), methodMatcher);
    }

    @Override public ReflectedMethod method(final Matcher<? super ReflectedMethod> methodMatcher) {
        final ReflectedMethod selectedMethod = selectFirst(methods(), methodMatcher);
        if (selectedMethod == null) {
            throw new MethodNotFoundException(klass, methodMatcher);
        }
        return selectedMethod;
    }

    @Override public List<ReflectedConstructor<T>> constructors(
            final Matcher<? super ReflectedConstructor<?>> constructorMatcher) {
        return select(constructors(), constructorMatcher);
    }

    @Override public ReflectedConstructor<T> constructor(
            final Matcher<? super ReflectedConstructor<?>> constructorMatcher) {
        return selectFirst(constructors(), constructorMatcher);
    }

    @Override public List<ReflectedClass<?>> superclassesAndInterfaces(
            final Matcher<? super ReflectedClass<?>> supertypeMatcher) {
        return select(superclassesAndInterfaces(), supertypeMatcher);
    }

    @Override public List<ReflectedField> declaredFields() {
        return fields.declaredFields();
    }

    @Override public List<ReflectedField> fields() {
        return fields.fields();
    }

    @Override public List<ReflectedField> fields(final ReflectionMatcher<? super ReflectedField> fieldMatcher) {
        return select(fields(), fieldMatcher);
    }

    @Override public ReflectedField field(final Matcher<? super ReflectedField> fieldMatcher) {
        final ReflectedField selectedMethod = selectFirst(fields(), fieldMatcher);
        if (selectedMethod == null) {
            throw new FieldNotFoundException(klass, fieldMatcher);
        }
        return selectedMethod;
    }
}
