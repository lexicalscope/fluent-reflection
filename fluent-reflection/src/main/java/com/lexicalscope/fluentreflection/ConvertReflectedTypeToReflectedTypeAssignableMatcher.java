package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;

import org.hamcrest.Matcher;

import ch.lambdaj.function.convert.Converter;

class ConvertReflectedTypeToReflectedTypeAssignableMatcher
        implements Converter<FluentClass<?>, Matcher<FluentClass<?>>> {
    @Override
    public Matcher<FluentClass<?>> convert(final FluentClass<?> from) {
        if (from == null) {
            return anyReflectedType();
        }
        return assignableFrom(from);
    }
}
