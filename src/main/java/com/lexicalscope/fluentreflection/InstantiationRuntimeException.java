package com.lexicalscope.fluentreflection;

import java.lang.reflect.Constructor;

public class InstantiationRuntimeException extends ReflectionRuntimeException {
    private static final long serialVersionUID = 2839141085488904858L;

    public InstantiationRuntimeException() {
        super();
    }

    public InstantiationRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InstantiationRuntimeException(final String message) {
        super(message);
    }

    public InstantiationRuntimeException(final Throwable cause) {
        super(cause);
    }

    public InstantiationRuntimeException(final InstantiationException e, final Constructor<?> constructor) {
        // TODO Auto-generated constructor stub
    }
}
