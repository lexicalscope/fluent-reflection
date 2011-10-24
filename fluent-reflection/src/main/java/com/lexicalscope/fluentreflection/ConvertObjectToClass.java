package com.lexicalscope.fluentreflection;

import ch.lambdaj.function.convert.Converter;

class ConvertObjectToClass implements Converter<Object, Class<?>> {
    @Override
    public Class<?> convert(final Object from) {
        if (from == null) {
            return null;
        }
        return from.getClass();
    }
}
