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

import static com.lexicalscope.fluentreflection.Visibility.visibilityFromModifiers;
import static java.lang.String.format;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.inject.TypeLiteral;

final class FluentFieldImpl extends AbstractFluentAnnotated implements FluentField {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final FluentClass<?> reflectedClass;
    private final TypeLiteral<?> typeLiteral;
    private final Field field;

    public FluentFieldImpl(
            final ReflectedTypeFactory reflectedTypeFactory,
            final FluentClass<?> reflectedClass,
            final TypeLiteral<?> typeLiteral,
            final Field field) {
        super(reflectedTypeFactory, field);
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.reflectedClass = reflectedClass;
        this.typeLiteral = typeLiteral;
        this.field = field;

        try { field.setAccessible(true); } catch(final SecurityException e)
        { /* ignore, if we can set the field as accessible we get much improved performance */ }
    }

    @Override public String name() {
        return field.getName();
    }

    @Override public FluentClass<?> declarer() {
        return reflectedTypeFactory.reflect(typeLiteral);
    }

    @Override public boolean isStatic() {
        return Modifier.isStatic(field.getModifiers());
    }

    @Override public boolean isFinal() {
        return Modifier.isFinal(field.getModifiers());
    }

    @Override public String property() {
        return field.getName();
    }

    @Override public Field member() {
        return field;
    }

    @Override public Visibility visibility() {
        return visibilityFromModifiers(field.getModifiers());
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

        return format(
                "%s%s%s%s %s",
                visibility,
                staticModifier,
                finalModifier,
                type(),
                field.getName());
    }

    @Override public boolean equals(final Object obj) {
        if (obj != null && this.getClass().equals(obj.getClass())) {
            final FluentFieldImpl that = (FluentFieldImpl) obj;
            return new EqualsBuilder()
                    .append(this.field, that.field)
                    .append(this.typeLiteral, that.typeLiteral)
                    .isEquals();
        }
        return false;
    }

    @Override public int hashCode() {
        return new HashCodeBuilder().append(field).append(typeLiteral).toHashCode();
    }

    @Override public int argCount() {
        return 0;
    }

    @Override public List<FluentClass<?>> args() {
        return new ArrayList<FluentClass<?>>();
    }

    @Override public FluentClass<?> type() {
        final TypeLiteral<?> returnType = typeLiteral.getFieldType(field);
        if (returnType == null) {
            return null;
        }
        return reflectedTypeFactory.reflect(returnType);
    }

    private Object callRaw(final Object... args) {
        if(args == null || args.length == 0) {
            throw new ReflectionRuntimeException("reading a field requires an instance argument");
        } else if (args.length > 2) {
            throw new ReflectionRuntimeException("reading a field requires one argument, writing a field requires two arguments. Got " + args.length + " arguments");
        } else if (args[0] == null) {
            return null;
        }

        try {
            final Object fieldValue = field.get(args[0]);
            if(args.length == 2)
            {
                field.set(args[0], args[1]);
            }
            return fieldValue;
        } catch (final IllegalArgumentException e) {
            throw new IllegalArgumentRuntimeException(e, field, args[0]);
        } catch (final IllegalAccessException e) {
            throw new IllegalAccessRuntimeException(e, field);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" }) @Override public FluentObject<?> call(final Object... args) {
        final Object object = callRaw(args);
        if(object == null) {
            return reflectedTypeFactory.reflect(typeLiteral.getFieldType(field), null);
        }
        return reflectedTypeFactory.reflect((Class) object.getClass(), object);
    }
}
