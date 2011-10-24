package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.*;
import static java.lang.String.format;

class StringHelpers {
    static String argList(final Object[] args) {
        return join(convert(args, new ConvertObjectToClass()), ", ");
    }

    private static String constructor(final String name, final String args) {
        return format("%s(%s)", name, args);
    }

    static Object constructor(final Class<?> klass, final Object[] args) {
        return constructor(klass.getName(), argList(args));
    }
}
