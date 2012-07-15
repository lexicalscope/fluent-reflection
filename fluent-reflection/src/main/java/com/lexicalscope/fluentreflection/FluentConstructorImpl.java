package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.convert;
import static com.lexicalscope.fluentreflection.Visibility.visibilityFromModifiers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.google.inject.TypeLiteral;

final class FluentConstructorImpl<T> extends AbstractFluentAnnotated implements FluentConstructor<T> {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final TypeLiteral<T> typeLiteral;
    private final Constructor<T> constructor;

    public FluentConstructorImpl(
            final ReflectedTypeFactory reflectedTypeFactory,
            final TypeLiteral<T> typeLiteral,
            final Constructor<T> constructor) {
        super(reflectedTypeFactory, constructor);
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.typeLiteral = typeLiteral;
        this.constructor = constructor;
    }

    @Override public int argCount() {
        return constructor.getParameterTypes().length;
    }

    @Override public List<FluentClass<?>> args() {
        return convert(
                typeLiteral.getParameterTypes(constructor),
                new ConvertTypeLiteralToReflectedType(reflectedTypeFactory));
    }

    @Override public Constructor<T> member() {
        return constructor;
    }

    private T callRaw(final Object... args) {
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

    @SuppressWarnings({ "unchecked", "rawtypes" }) @Override public FluentObject<T> call(final Object... args) {
        final Object object = callRaw(args);
        if(object == null) {
            return null;
        }
        return reflectedTypeFactory.reflect((Class) object.getClass(), object);
    }

    @Override public FluentClass<?> declarer() {
        return reflectedTypeFactory.reflect(typeLiteral);
    }

    @Override public String name() {
        return constructor.getName();
    }

    @Override public String property() {
        return "<init>";
    }

    @Override public FluentClass<?> type() {
        return declarer();
    }

    @Override public Visibility visibility() {
        return visibilityFromModifiers(constructor.getModifiers());
    }

    @Override public boolean isStatic() {
        return true;
    }

    @Override public boolean isFinal() {
        return false;
    }
}
