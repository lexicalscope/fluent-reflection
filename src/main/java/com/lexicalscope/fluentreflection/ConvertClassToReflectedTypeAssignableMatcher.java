package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;

import org.hamcrest.Matcher;

import ch.lambdaj.function.convert.Converter;

class ConvertClassToReflectedTypeAssignableMatcher implements Converter<Class<?>, Matcher<ReflectedType<?>>> {
    @Override
    public Matcher<ReflectedType<?>> convert(final Class<?> from) {
        if (from == null) {
            return anyReflectedType();
        }
        return reflectedTypeReflectingOnAssignableFrom(from);
    }
}
