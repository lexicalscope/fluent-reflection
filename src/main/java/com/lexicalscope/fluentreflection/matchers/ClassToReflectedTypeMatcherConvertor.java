package com.lexicalscope.fluentreflection.matchers;

import static com.lexicalscope.fluentreflection.matchers.ReflectionMatchers.reflectedTypeReflectingOn;

import org.hamcrest.Matcher;

import ch.lambdaj.function.convert.Converter;

import com.lexicalscope.fluentreflection.ReflectedType;

class ClassToReflectedTypeMatcherConvertor implements Converter<Class<?>, Matcher<ReflectedType<?>>> {
    @Override
    public Matcher<ReflectedType<?>> convert(final Class<?> from) {
        return reflectedTypeReflectingOn(from);
    }
}
