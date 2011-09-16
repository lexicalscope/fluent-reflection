package com.lexicalscope.fluentreflection;

import ch.lambdaj.function.convert.Converter;

class ConvertClassToSimpleName implements Converter<Class<?>, String> {
    @Override
    public String convert(final Class<?> from) {
        if (from == null) {
            return null;
        }
        return from.getSimpleName();
    }
}
