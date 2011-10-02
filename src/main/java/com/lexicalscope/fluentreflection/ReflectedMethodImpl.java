package com.lexicalscope.fluentreflection;

/*
 * Copyright 2011 Tim Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

import static ch.lambdaj.Lambda.convert;
import static java.lang.String.format;
import static java.lang.System.arraycopy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.TypeLiteral;

class ReflectedMethodImpl extends AbstractReflectedCallable implements ReflectedMethod {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final ReflectedClass<?> reflectedClass;
    private final Method method;

    public ReflectedMethodImpl(final ReflectedTypeFactory reflectedTypeFactory,
                               final ReflectedClass<?> reflectedClass,
                               final Method method) {
        super(reflectedTypeFactory, method);
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.reflectedClass = reflectedClass;
        this.method = method;
    }

    @Override public String getName() {
        return method.getName();
    }

    @Override public List<ReflectedClass<?>> argumentTypes() {
        final List<ReflectedClass<?>> result = new ArrayList<ReflectedClass<?>>();
        if (!isStatic()) {
            result.add(reflectedClass);
        }
        result.addAll(convert(
                reflectedClass.typeLiteralUnderReflection().getParameterTypes(method),
                new ConvertTypeLiteralToReflectedType(reflectedTypeFactory)));
        return result;
    }

    @Override public int argumentCount() {
        final int parameterCount = method.getParameterTypes().length;
        if (isStatic()) {
            return parameterCount;
        }
        return parameterCount + 1;
    }

    @Override public ReflectedClass<?> declaringClass() {
        return reflectedTypeFactory.reflect(reflectedClass.typeLiteralUnderReflection());
    }

    @Override public Object call(final Object... args) {
        if (isStatic()) {
            return invokeMethod(null, args);
        } else {
            if (args.length < 1) {
                throw new IllegalArgumentException("target instance must be specified as first argument when calling "
                        + method);
            }

            final Object[] remainingArguments = new Object[args.length - 1];
            arraycopy(args, 1, remainingArguments, 0, args.length - 1);
            return invokeMethod(args[0], remainingArguments);
        }
    }

    private Object invokeMethod(final Object instance, final Object[] arguments) {
        try {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            return method.invoke(instance, arguments);
        } catch (final IllegalAccessException e) {
            throw new IllegalAccessRuntimeException(e, method);
        } catch (final InvocationTargetException e) {
            throw new InvocationTargetRuntimeException(e, method);
        }
    }

    @Override public boolean isStatic() {
        return Modifier.isStatic(method.getModifiers());
    }

    @Override public <T> ReflectedQuery<T> returning(final Class<T> returnType) {
        return new ReflectedQuery<T>() {
            @Override public T call(final Object... args) {
                return returnType.cast(ReflectedMethodImpl.this.call(args));
            }
        };
    }

    @Override public ReflectedClass<?> returnType() {
        final TypeLiteral<?> returnType = reflectedClass.typeLiteralUnderReflection().getReturnType(method);
        if (returnType == null) {
            return null;
        }
        return reflectedTypeFactory.reflect(returnType);
    }

    @Override public String propertyName() {
        final String name = getName();

        if (name.length() > 2) {
            if (name.length() > 3) {
                if (name.startsWith("get") || name.startsWith("set")) {
                    return initialLowerCase(name.substring(3));
                }
            }
            if (name.startsWith("is")) {
                return initialLowerCase(name.substring(2));
            }
        }
        return method.getName();
    }

    private String initialLowerCase(final String substring) {
        return substring.substring(0, 1).toLowerCase() + substring.substring(1);
    }

    @Override public String toString() {
        return format("%s %s(%s)", method.getReturnType().getSimpleName(), method.getName(), join(convert(
                method.getParameterTypes(),
                new ConvertClassToSimpleName())));
    }

    private static String join(final List<String> strings) {
        final StringBuilder result = new StringBuilder();
        String separator = "";
        for (final String string : strings) {
            result.append(separator);
            result.append(string);
            separator = ", ";
        }
        return result.toString();
    }
}
