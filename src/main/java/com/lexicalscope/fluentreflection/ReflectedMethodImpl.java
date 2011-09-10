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

import static java.lang.System.arraycopy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import ch.lambdaj.Lambda;

class ReflectedMethodImpl implements ReflectedMethod {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final Method method;

    public ReflectedMethodImpl(final ReflectedTypeFactory reflectedTypeFactory, final Method method) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.method = method;
    }

    @Override
    public String getName() {
        return method.getName();
    }

    @Override
    public List<ReflectedType<?>> argumentTypes() {
        return Lambda.convert(method.getParameterTypes(), new ConvertClassToReflectedType(reflectedTypeFactory));
    }

    @Override
    public int argumentCount() {
        return method.getParameterTypes().length;
    }

    @Override
    public ReflectedType<?> getDeclaringClass() {
        return reflectedTypeFactory.reflect(method.getDeclaringClass());
    }

    @Override
    public String toString() {
        return method.toString();
    }

    @Override
    public Object call(final Object... args) {
        if (isStatic()) {
            try {
                return method.invoke(null, args);
            } catch (final IllegalAccessException e) {
                throw new IllegalAccessRuntimeException(e, method);
            } catch (final InvocationTargetException e) {
                throw new InvocationTargetRuntimeException(e, method);
            }
        } else {
            if (args.length < 1) {
                throw new IllegalArgumentException("target instance must be specified as first argument when calling "
                        + method);
            }

            final Object[] remainingArguments = new Object[args.length - 1];
            arraycopy(args, 1, remainingArguments, 0, args.length - 1);
            try {
                return method.invoke(args[0], remainingArguments);
            } catch (final IllegalAccessException e) {
                throw new IllegalAccessRuntimeException(e, method);
            } catch (final InvocationTargetException e) {
                throw new InvocationTargetRuntimeException(e, method);
            }
        }
    }

    @Override
    public boolean isStatic() {
        return Modifier.isStatic(method.getModifiers());
    }
}
