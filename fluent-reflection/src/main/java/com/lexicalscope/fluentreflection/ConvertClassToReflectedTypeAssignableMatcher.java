package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;

import org.hamcrest.Matcher;

import ch.lambdaj.function.convert.Converter;

class ConvertClassToReflectedTypeAssignableMatcher implements Converter<Class<?>, Matcher<FluentAccess<?>>> {
    @Override
    public Matcher<FluentAccess<?>> convert(final Class<?> from) {
        if (from == null) {
            return anyFluentType();
        }
        return assignableFrom(from);
    }
}
