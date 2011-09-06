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

import static ch.lambdaj.Lambda.select;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.typeIsInterface;
import static org.hamcrest.Matchers.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.hamcrest.Matcher;

/**
 * Not thread safe
 * 
 * @author tim
 * 
 * @param <T>
 */
final class ReflectedTypeImpl<T> implements ReflectedType<T> {
    private final Class<T> klass;
    private final ReflectedSuperclassesAndInterfaces<T> members;
    private final ReflectedConstructors<T> constructors;

    public ReflectedTypeImpl(final Class<T> klass) {
        this.klass = klass;
        this.members = new ReflectedSuperclassesAndInterfaces<T>(klass);
        this.constructors = new ReflectedConstructors<T>(klass);
    }

    @Override
    public Class<T> classUnderReflection() {
        return klass;
    }

    @Override
    public List<ReflectedMethod> methods(final Matcher<? super ReflectedMethod> methodMatcher) {
        return select(members.methods(), methodMatcher);
    }

    @Override
    public List<ReflectedMethod> methods() {
        return methods(anything());
    }

    static <T> ReflectedTypeImpl<?> createReflectedType(final Class<T> from) {
        return new ReflectedTypeImpl<T>(from);
    }

    @Override
    public List<ReflectedType<?>> interfaces() {
        return select(members.interfacesAndSuperClasses(), typeIsInterface());
    }

    @Override
    public List<ReflectedType<?>> superclasses() {
        return select(members.interfacesAndSuperClasses(), not(typeIsInterface()));
    }

    @Override
    public boolean isInterface() {
        return klass.isInterface();
    }

    @Override
    public T construct() {
        final Constructor<?>[] constructors = klass.getConstructors();
        for (final Constructor<?> constructor : constructors) {
            if (constructor.getParameterTypes().length == 0) {
                try {
                    return klass.cast(constructor.newInstance());
                } catch (final IllegalArgumentException e) {
                    throw e;
                } catch (final InstantiationException e) {
                    throw new InstantiationRuntimeException(e, constructor);
                } catch (final IllegalAccessException e) {
                    throw new IllegalAccessRuntimeException(e, constructor);
                } catch (final InvocationTargetException e) {
                    throw new InvocationTargetRuntimeException(e, constructor);
                }
            }
        }
        throw new ConstructorNotFoundRuntimeException(klass);
    }

    @Override
    public List<ReflectedConstructor<T>> constructors(
            final ReflectionMatcher<? super ReflectedConstructor<?>> constructorMatcher) {
        return select(constructors.constructors(), constructorMatcher);
    }

    @Override
    public boolean equals(final Object that) {
        if (that != null && that.getClass().equals(this.getClass())) {
            return klass.equals(((ReflectedTypeImpl<?>) that).klass);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return klass.hashCode();
    }

    @Override
    public String toString() {
        return "ReflectedType<" + klass.getName() + ">";
    }
}
