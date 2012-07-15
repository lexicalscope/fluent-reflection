package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.reflectingOn;

import org.hamcrest.Matcher;

import ch.lambdaj.function.convert.Converter;

class ConvertClassToReflectedTypeMatcher implements Converter<Class<?>, Matcher<FluentAccess<?>>> {
    @Override
    public Matcher<FluentAccess<?>> convert(final Class<?> from) {
        return reflectingOn(from);
    }
}
