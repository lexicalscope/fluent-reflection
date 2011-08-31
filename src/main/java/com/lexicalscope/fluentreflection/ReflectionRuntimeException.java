package com.lexicalscope.fluentreflection;

public class ReflectionRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -4053496996719723657L;

    public ReflectionRuntimeException() {
        super();
    }

    public ReflectionRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ReflectionRuntimeException(final String message) {
        super(message);
    }

    public ReflectionRuntimeException(final Throwable cause) {
        super(cause);
    }
}
