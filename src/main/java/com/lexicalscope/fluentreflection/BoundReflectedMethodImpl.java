package com.lexicalscope.fluentreflection;

import static java.lang.System.arraycopy;

import java.util.ArrayList;
import java.util.List;

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

class BoundReflectedMethodImpl implements ReflectedMethod {
    private final ReflectedMethod method;
    private final Object instance;

    public BoundReflectedMethodImpl(
            final ReflectedMethod method,
            final Object instance) {
        if (method.isStatic()) {
            throw new IllegalArgumentException("cannot bind static method " + method);
        }
        this.method = method;
        this.instance = instance;
    }

    @Override
    public int argumentCount() {
        return method.argumentCount() - 1;
    }

    @Override
    public List<ReflectedClass<?>> argumentTypes() {
        return new ArrayList<ReflectedClass<?>>(method.argumentTypes().subList(1, argumentCount() + 1));
    }

    @Override
    public Object call(final Object... args) {
        final Object[] argsWithInstance = new Object[args.length + 1];
        argsWithInstance[0] = instance;
        arraycopy(args, 0, argsWithInstance, 1, args.length);
        return method.call(argsWithInstance);
    }

    @Override
    public ReflectedClass<?> declaringClass() {
        return method.declaringClass();
    }

    @Override
    public String getName() {
        return method.getName();
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public <T> ReflectedQuery<T> returning(final Class<T> returnType) {
        return new ReflectedQuery<T>() {
            @Override
            public T call(final Object... args) {
                return returnType.cast(BoundReflectedMethodImpl.this.call(args));
            }
        };
    }

    @Override
    public String toString() {
        return String.format("%s in %s", method, instance);
    }
}