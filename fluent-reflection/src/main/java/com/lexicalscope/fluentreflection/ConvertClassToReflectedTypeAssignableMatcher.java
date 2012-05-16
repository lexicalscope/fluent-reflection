package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;

import org.hamcrest.Matcher;

import ch.lambdaj.function.convert.Converter;

class ConvertClassToReflectedTypeAssignableMatcher implements Converter<Class<?>, Matcher<ReflectedClass<?>>> {
    @Override
    public Matcher<ReflectedClass<?>> convert(final Class<?> from) {
        if (from == null) {
            return anyReflectedType();
        }
        return assignableFrom(from);
    }
}
