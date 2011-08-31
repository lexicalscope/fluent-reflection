package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.StringHelpers.constructor;
import static java.lang.String.format;

public class ConstructorNotFoundRuntimeException extends ReflectionRuntimeException {
    private static final long serialVersionUID = -5339808693443391503L;

    public ConstructorNotFoundRuntimeException() {
        super();
    }

    public ConstructorNotFoundRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ConstructorNotFoundRuntimeException(final String message) {
        super(message);
    }

    public ConstructorNotFoundRuntimeException(final Throwable cause) {
        super(cause);
    }

    public ConstructorNotFoundRuntimeException(final Class<?> klass, final Object... args) {
        super(format("constructor %s not found", constructor(klass, args)));
    }
}
