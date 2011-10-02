package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.convert;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.google.inject.TypeLiteral;

class ReflectedConstructorImpl<T> extends AbstractReflectedCallable implements ReflectedConstructor<T> {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final TypeLiteral<T> typeLiteral;
    private final Constructor<T> constructor;

    public ReflectedConstructorImpl(
            final ReflectedTypeFactory reflectedTypeFactory,
            final TypeLiteral<T> typeLiteral,
            final Constructor<T> constructor) {
        super(reflectedTypeFactory, constructor);
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.typeLiteral = typeLiteral;
        this.constructor = constructor;
    }

    @Override public int argumentCount() {
        return constructor.getParameterTypes().length;
    }

    @Override public List<ReflectedClass<?>> argumentTypes() {
        return convert(
                typeLiteral.getParameterTypes(constructor),
                new ConvertTypeLiteralToReflectedType(reflectedTypeFactory));
    }

    @Override public Constructor<T> constructorUnderReflection() {
        return constructor;
    }

    @Override public T call(final Object... args) {
        try {
            return constructor.newInstance(args);
        } catch (final InstantiationException e) {
            throw new InstantiationRuntimeException(e, constructor);
        } catch (final IllegalAccessException e) {
            throw new IllegalAccessRuntimeException(e, constructor);
        } catch (final InvocationTargetException e) {
            throw new InvocationTargetRuntimeException(e, constructor);
        }
    }

    @Override public ReflectedClass<?> declaringClass() {
        return reflectedTypeFactory.reflect(constructor.getDeclaringClass());
    }

    @Override public String getName() {
        return constructor.getName();
    }

    @Override public ReflectedClass<?> returnType() {
        return declaringClass();
    }
}
