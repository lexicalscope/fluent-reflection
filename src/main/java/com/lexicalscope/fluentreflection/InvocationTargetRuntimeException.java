package com.lexicalscope.fluentreflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;

public class InvocationTargetRuntimeException extends ReflectionRuntimeException {
    private static final long serialVersionUID = -4623735792822350866L;

    public InvocationTargetRuntimeException() {
        super();
    }

    public InvocationTargetRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvocationTargetRuntimeException(final String message) {
        super(message);
    }

    public InvocationTargetRuntimeException(final Throwable cause) {
        super(cause);
    }

    public InvocationTargetRuntimeException(final InvocationTargetException e, final Member member) {
        // TODO Auto-generated constructor stub
    }
}
