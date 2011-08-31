package com.lexicalscope.fluentreflection;

import ch.lambdaj.function.convert.Converter;

class ConvertObjectToClass implements Converter<Object, Object> {
    @Override
    public Object convert(final Object from) {
        if (from == null) {
            return null;
        }
        return from.getClass();
    }
}
