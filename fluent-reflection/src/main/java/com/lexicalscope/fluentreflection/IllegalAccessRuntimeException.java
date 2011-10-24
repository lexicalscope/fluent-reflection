package com.lexicalscope.fluentreflection;

import java.lang.reflect.Member;

public class IllegalAccessRuntimeException extends ReflectionRuntimeException {
    private static final long serialVersionUID = -2532532215028572886L;

    public IllegalAccessRuntimeException() {
        super();
    }

    public IllegalAccessRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public IllegalAccessRuntimeException(final String message) {
        super(message);
    }

    public IllegalAccessRuntimeException(final Throwable cause) {
        super(cause);
    }

    public IllegalAccessRuntimeException(final IllegalAccessException e, final Member member) {
        // TODO Auto-generated constructor stub
    }
}
