package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.join;

import java.lang.reflect.Field;
import java.lang.reflect.Member;

public class IllegalArgumentRuntimeException extends ReflectionRuntimeException {
    private static final long serialVersionUID = -2532532215028572886L;

    public IllegalArgumentRuntimeException() {
        super();
    }

    public IllegalArgumentRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public IllegalArgumentRuntimeException(final String message) {
        super(message);
    }

    public IllegalArgumentRuntimeException(final Throwable cause) {
        super(cause);
    }

    public IllegalArgumentRuntimeException(
            final IllegalArgumentException e,
            final Member member,
            final Object instance,
            final Object[] arguments) {
        this(String.format(
                "when calling %s on %s with %s : %s",
                member,
                instance,
                join(arguments, ", "),
                e.getMessage()), e);
    }

    public IllegalArgumentRuntimeException(final IllegalArgumentException e, final Field field, final Object instance) {
        this(String.format(
                "when reading %s on %s : %s",
                field,
                instance,
                e.getMessage()), e);
    }
}
