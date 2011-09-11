package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.reflectedTypeReflectingOn;

import org.hamcrest.Matcher;

import ch.lambdaj.function.convert.Converter;

class ConvertClassToReflectedTypeMatcher implements Converter<Class<?>, Matcher<ReflectedClass<?>>> {
    @Override
    public Matcher<ReflectedClass<?>> convert(final Class<?> from) {
        if (from == null) {
            return null;
        }
        return reflectedTypeReflectingOn(from);
    }
}
