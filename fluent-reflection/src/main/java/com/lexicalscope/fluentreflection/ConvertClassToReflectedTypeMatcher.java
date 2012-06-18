package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.reflectingOn;

import org.hamcrest.Matcher;

import ch.lambdaj.function.convert.Converter;

class ConvertClassToReflectedTypeMatcher implements Converter<Class<?>, Matcher<FluentClass<?>>> {
    @Override
    public Matcher<FluentClass<?>> convert(final Class<?> from) {
        return reflectingOn(from);
    }
}
