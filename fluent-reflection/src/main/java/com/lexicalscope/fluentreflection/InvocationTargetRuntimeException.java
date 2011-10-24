package com.lexicalscope.fluentreflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;

public class InvocationTargetRuntimeException extends ReflectionRuntimeException {
    private static final long serialVersionUID = -4623735792822350866L;
    private Throwable exceptionThrownByInvocationTarget;

    private InvocationTargetRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvocationTargetRuntimeException(final InvocationTargetException cause, final Member member) {
        this("Exception while invoking " + member, cause);
        exceptionThrownByInvocationTarget = cause.getCause();
    }

    public Throwable getExceptionThrownByInvocationTarget() {
        return exceptionThrownByInvocationTarget;
    }
}
