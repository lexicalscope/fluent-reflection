package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;

import org.hamcrest.Matcher;

import ch.lambdaj.function.convert.Converter;

class ConvertFluentTypeToFluentTypeAssignableMatcher
        implements Converter<FluentAccess<?>, Matcher<FluentAccess<?>>> {
    @Override
    public Matcher<FluentAccess<?>> convert(final FluentAccess<?> from) {
        if (from == null) {
            return anyFluentType();
        }
        return assignableFrom(from);
    }
}
