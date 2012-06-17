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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.inject.TypeLiteral;

final class TypeHierarchyCalculation {
    private final List<TypeLiteral<?>> done = new ArrayList<TypeLiteral<?>>();
    private final List<ReflectedClass<?>> result = new ArrayList<ReflectedClass<?>>();
    private final List<TypeLiteral<?>> pending = new LinkedList<TypeLiteral<?>>();
    private final ReflectedTypeFactory reflectedTypeFactory;

    public TypeHierarchyCalculation(final ReflectedTypeFactory reflectedTypeFactory) {
        this.reflectedTypeFactory = reflectedTypeFactory;
    }

    List<ReflectedClass<?>> interfacesAndSuperClass(final TypeLiteral<?> typeLiteral) {
        queueSuperclassAndInterfaces(typeLiteral);
        processesPendingTypes();
        return result;
    }

    private void processesPendingTypes() {
        while (!pending.isEmpty()) {
            processClass(pending.remove(0));
        }
    }

    private void processClass(final TypeLiteral<?> klassToReflect) {
        queueSuperclassAndInterfaces(klassToReflect);
        if (done.contains(klassToReflect)) {
            return;
        }
        done.add(klassToReflect);
        result.add(reflectedTypeFactory.reflect(klassToReflect));
    }

    private void queueSuperclassAndInterfaces(final TypeLiteral<?> typeLiteralToReflect) {
        final Class<?> rawSuperclass = typeLiteralToReflect.getRawType().getSuperclass();
        if (rawSuperclass != null && !rawSuperclass.equals(Object.class)) {
            pending.add(typeLiteralToReflect.getSupertype(rawSuperclass));
        }
        final Class<?>[] interfaces = typeLiteralToReflect.getRawType().getInterfaces();
        for (final Class<?> interfac3 : interfaces) {
            pending.add(typeLiteralToReflect.getSupertype(interfac3));
        }
    }
}