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

    private ReflectedMembersImpl(
            final ReflectedSuperclassesAndInterfaces<T> superclassesAndInterfaces,
            final ReflectedMethods<T> methods,
            final ReflectedConstructors<T> constructors,
            final ReflectedFields<T> fields,
            final Class<T> klass) {
        this.superclassesAndInterfaces = superclassesAndInterfaces;
        this.methods = methods;
        this.constructors = constructors;
        this.fields = fields;
        this.klass = klass;
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
        final FluentMethod selectedMethod = selectFirstMethod(methodMatcher);
        if (selectedMethod == null) {
            throw new MethodNotFoundException(klass, methodMatcher);
        }
        return selectedMethod;
    }

    private FluentMethod selectFirstMethod(final Matcher<? super FluentMethod> methodMatcher) {
        return selectFirst(methods(), methodMatcher);
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

    @Override public List<FluentField> fields(final Matcher<? super FluentField> fieldMatcher) {
        return select(fields(), fieldMatcher);
    }

    @Override public FluentField field(final Matcher<? super FluentField> fieldMatcher) {
        final FluentField selectedMethod = selectFirstField(fieldMatcher);
        if (selectedMethod == null) {
            throw new FieldNotFoundException(klass, fieldMatcher);
        }
        return selectedMethod;
    }

    private FluentField selectFirstField(final Matcher<? super FluentField> fieldMatcher) {
        return selectFirst(fields(), fieldMatcher);
    }

    @Override public FluentMember member(final Matcher<? super FluentMember> memberMatcher) {
        final FluentMethod method = selectFirstMethod(memberMatcher);
        if(method == null) {
            final FluentField field = selectFirstField(memberMatcher);
            if(field == null) {
                throw new MemberNotFoundException(klass, memberMatcher);
            }
            return field;
        }
        return method;
    }

    @SuppressWarnings("unchecked") public static <T> ReflectedMembersImpl<T> createReflectedMembers(
            final ReflectedTypeFactory reflectedTypeFactory,
            final TypeLiteral<T> typeLiteral) {
        final ReflectedSuperclassesAndInterfacesImpl<T> superclassesAndInterfaces
            = new ReflectedSuperclassesAndInterfacesImpl<T>(reflectedTypeFactory, typeLiteral);

        return new ReflectedMembersImpl<T>(
              superclassesAndInterfaces,
              new ReflectedMethodsImpl<T>(reflectedTypeFactory, typeLiteral, superclassesAndInterfaces),
              new ReflectedConstructorsImpl<T>(reflectedTypeFactory, typeLiteral),
              new ReflectedFieldsImpl<T>(reflectedTypeFactory, typeLiteral, superclassesAndInterfaces),
              (Class<T>) typeLiteral.getRawType());
    }

    @SuppressWarnings("unchecked") public static <T> ReflectedMembersImpl<T> createBoundReflectedMembers(
            final ReflectedTypeFactory reflectedTypeFactory,
            final TypeLiteral<T> typeLiteral,
            final FluentObject<T> instance) {
        final ReflectedSuperclassesAndInterfacesImpl<T> superclassesAndInterfaces
            = new ReflectedSuperclassesAndInterfacesImpl<T>(reflectedTypeFactory, typeLiteral);

        return new ReflectedMembersImpl<T>(
              superclassesAndInterfaces,
              new ReflectedMethodsBinder<T>(new ReflectedMethodsImpl<T>(reflectedTypeFactory, typeLiteral, superclassesAndInterfaces), instance),
              new ReflectedConstructorsImpl<T>(reflectedTypeFactory, typeLiteral),
              new ReflectedFieldsImpl<T>(reflectedTypeFactory, typeLiteral, superclassesAndInterfaces),
              (Class<T>) typeLiteral.getRawType());
    }
}
