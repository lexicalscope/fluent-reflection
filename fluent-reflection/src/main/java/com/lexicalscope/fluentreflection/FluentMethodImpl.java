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

import static ch.lambdaj.Lambda.*;
import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.compatibleWith;
import static com.lexicalscope.fluentreflection.Visibility.visibilityFromModifiers;
import static java.lang.String.format;
import static java.lang.System.arraycopy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hamcrest.Matcher;

import com.google.inject.TypeLiteral;

final class FluentMethodImpl extends AbstractFluentAnnotated implements FluentMethod {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final TypeLiteral<?> typeLiteral;
    private final Method method;

    public FluentMethodImpl(
            final ReflectedTypeFactory reflectedTypeFactory,
            final TypeLiteral<?> typeLiteral,
            final Method method) {
        super(reflectedTypeFactory, method);
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.typeLiteral = typeLiteral;
        this.method = method;
    }

    @Override public String name() {
        return method.getName();
    }

    @Override public List<FluentClass<?>> args() {
        final List<FluentClass<?>> result = new ArrayList<FluentClass<?>>();
        result.addAll(convert(
                typeLiteral.getParameterTypes(method),
                new ConvertTypeLiteralToReflectedType(reflectedTypeFactory)));
        return result;
    }

    @Override public int indexOfArg(final Matcher<? super FluentClass<?>> matcher) {
       final List<FluentClass<?>> args = args();
       for (int i = 0; i < args.size(); i++) {
          if(matcher.matches(args.get(i))) {
            return i;
         }
       }
       throw new IndexOutOfBoundsException("No argment matching " + matcher);
    }

    @Override public int argCount() {
        return method.getParameterTypes().length;
    }

    @Override public FluentClass<?> declarer() {
        return reflectedTypeFactory.reflect(typeLiteral);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" }) @Override public FluentObject<?> call(final Object... args) {
        final Object object = callRaw(args);
        if(object == null && typeLiteral.getReturnType(method) != null) {
            return reflectedTypeFactory.reflect(typeLiteral.getReturnType(method), null);
        }
        return reflectedTypeFactory.reflect((Class) object.getClass(), object);
    }

    private Object callRaw(final Object... args) {
        if (isStatic()) {
            return invokeMethod(null, args);
        } else {
            if (args.length < 1) {
                throw new IllegalArgumentException("target instance must be specified as first argument when calling "
                        + method);
            } else if (args[0] == null) {
                return null;
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
        } catch (final IllegalArgumentException e) {
            throw new IllegalArgumentRuntimeException(e, method, instance, arguments);
        } catch (final IllegalAccessException e) {
            throw new IllegalAccessRuntimeException(e, method);
        } catch (final InvocationTargetException e) {
            throw new InvocationTargetRuntimeException(e, method);
        }
    }

    @Override public boolean isStatic() {
        return Modifier.isStatic(method.getModifiers());
    }

    @Override public boolean isFinal() {
        return Modifier.isFinal(method.getModifiers());
    }

    @Override public FluentClass<?> type() {
        final TypeLiteral<?> returnType = typeLiteral.getReturnType(method);
        if (returnType == null) {
            return null;
        }
        return reflectedTypeFactory.reflect(returnType);
    }

    @Override public String property() {
        final String name = name();

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

    @Override public Method member() {
        return method;
    }

    @Override public Visibility visibility() {
        return visibilityFromModifiers(method.getModifiers());
    }

    private List<TypeVariable<Method>> typeParameters()
    {
        return Arrays.asList(method.getTypeParameters());
    }

    @Override public String toString() {
        final String visibility;
        if (visibility().toString().isEmpty())
        {
            visibility = visibility().toString();
        }
        else
        {
            visibility = visibility().toString() + " ";
        }

        final String staticModifier;
        if (isStatic())
        {
            staticModifier = "static ";
        }
        else
        {
            staticModifier = "";
        }

        final String finalModifier;
        if (isFinal())
        {
            finalModifier = "final ";
        }
        else
        {
            finalModifier = "";
        }

        final String typeParameters;
        if (typeParameters().isEmpty())
        {
            typeParameters = "";
        }
        else
        {
            typeParameters = "<" + joinFrom(typeParameters(), ", ").toString() + "> ";
        }

        final String arguments;
        if (argCount() > 0) {
            arguments = joinFrom(args(), ", ").toString();
        } else {
            arguments = "";
        }

        return format(
                "%s%s%s%s%s %s(%s)",
                visibility,
                staticModifier,
                finalModifier,
                typeParameters,
                type(),
                method.getName(),
                arguments);
    }

    @Override public boolean equals(final Object obj) {
        if (obj != null && this.getClass().equals(obj.getClass())) {
            final FluentMethodImpl that = (FluentMethodImpl) obj;
            return new EqualsBuilder()
                    .append(this.method, that.method)
                    .append(this.typeLiteral, that.typeLiteral)
                    .isEquals();
        }
        return false;
    }

    @Override public int hashCode() {
        return new HashCodeBuilder().append(method).append(typeLiteral).toHashCode();
    }

   @Override public FluentMethod rebind(final Object receiver) {
      return object(receiver).method(compatibleWith(this));
   }
}
