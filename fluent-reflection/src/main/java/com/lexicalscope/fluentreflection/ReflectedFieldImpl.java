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

class ReflectedFieldImpl extends AbstractReflectedAnnotated implements ReflectedField {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final ReflectedClass<?> reflectedClass;
    private final TypeLiteral<?> typeLiteral;
    private final Field field;

    public ReflectedFieldImpl(final ReflectedTypeFactory reflectedTypeFactory,
            final ReflectedClass<?> reflectedClass,
            final TypeLiteral<?> typeLiteral,
            final Field field) {
        super(reflectedTypeFactory, field);
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.reflectedClass = reflectedClass;
        this.typeLiteral = typeLiteral;
        this.field = field;
    }

    @Override public String getName() {
        return field.getName();
    }

    @Override public ReflectedClass<?> declaringClass() {
        return reflectedTypeFactory.reflect(typeLiteral);
    }

    @Override public boolean isStatic() {
        return Modifier.isStatic(field.getModifiers());
    }

    @Override public boolean isFinal() {
        return Modifier.isFinal(field.getModifiers());
    }

    @Override public String propertyName() {
        return field.getName();
    }

    @Override public Field memberUnderReflection() {
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
                "%s %s %s %s %s",
                visibility,
                staticModifier,
                finalModifier,
                type(),
                field.getName());
    }

    @Override public boolean equals(final Object obj) {
        if (obj != null && this.getClass().equals(obj.getClass())) {
            final ReflectedFieldImpl that = (ReflectedFieldImpl) obj;
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

    @Override public int argumentCount() {
        return 0;
    }

    @Override public List<ReflectedClass<?>> argumentTypes() {
        return new ArrayList<ReflectedClass<?>>();
    }

    @Override public ReflectedClass<?> type() {
        final TypeLiteral<?> returnType = typeLiteral.getFieldType(field);
        if (returnType == null) {
            return null;
        }
        return reflectedTypeFactory.reflect(returnType);
    }

    @Override public Object call(final Object... args) {
        if(args == null || args.length != 1)
        {
            throw new ReflectionRuntimeException("reading a field require an instance argument");
        }
        try {
            return field.get(args[0]);
        } catch (final IllegalArgumentException e) {
            throw new IllegalArgumentRuntimeException(e, field, args[0]);
        } catch (final IllegalAccessException e) {
            throw new IllegalAccessRuntimeException(e, field);
        }
    }

    @Override public <T> ReflectedQuery<T> castResultTo(final Class<T> returnType) {
        return new ReflectedQuery<T>() {
            @Override public T call(final Object... args) {
                return returnType.cast(ReflectedFieldImpl.this.call(args));
            }
        };
    }
}
