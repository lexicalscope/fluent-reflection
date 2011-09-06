package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.reflectedTypeReflectingOn;

import org.hamcrest.Matcher;

import ch.lambdaj.function.convert.Converter;


class ClassToReflectedTypeMatcherConvertor implements Converter<Class<?>, Matcher<ReflectedType<?>>> {
    @Override
    public Matcher<ReflectedType<?>> convert(final Class<?> from) {
        return reflectedTypeReflectingOn(from);
    }
}
