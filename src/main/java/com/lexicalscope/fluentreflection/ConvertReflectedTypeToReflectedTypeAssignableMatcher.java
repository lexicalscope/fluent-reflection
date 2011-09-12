package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;

import org.hamcrest.Matcher;

import ch.lambdaj.function.convert.Converter;

class ConvertReflectedTypeToReflectedTypeAssignableMatcher
        implements Converter<ReflectedClass<?>, Matcher<ReflectedClass<?>>> {
    @Override
    public Matcher<ReflectedClass<?>> convert(final ReflectedClass<?> from) {
        if (from == null) {
            return anyReflectedType();
        }
        return reflectedTypeReflectingOnAssignableFrom(from);
    }
}
