package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.convert;
import static com.lexicalscope.fluentreflection.Visibility.visibilityFromModifiers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.google.inject.TypeLiteral;

final class ReflectedConstructorImpl<T> extends AbstractReflectedAnnotated implements ReflectedConstructor<T> {
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

    @Override public Constructor<T> memberUnderReflection() {
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
        return reflectedTypeFactory.reflect(typeLiteral);
    }

    @Override public String getName() {
        return constructor.getName();
    }

    @Override public String propertyName() {
        return getName();
    }

    @Override public ReflectedClass<?> type() {
        return declaringClass();
    }

    @Override public Visibility visibility() {
        return visibilityFromModifiers(constructor.getModifiers());
    }

    @Override public <S> ReflectedQuery<S> castResultTo(final Class<S> returnType) {
        return new ReflectedQuery<S>() {
            @Override public S call(final Object... args) {
                return returnType.cast(ReflectedConstructorImpl.this.call(args));
            }
        };
    }

    @Override public boolean isStatic() {
        return true;
    }

    @Override public boolean isFinal() {
        return false;
    }
}
