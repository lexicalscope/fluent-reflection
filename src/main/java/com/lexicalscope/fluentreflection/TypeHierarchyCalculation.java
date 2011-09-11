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

class TypeHierarchyCalculation {
    private final List<Class<?>> done = new ArrayList<Class<?>>();
    private final List<ReflectedClass<?>> result = new ArrayList<ReflectedClass<?>>();
    private final List<Class<?>> pending = new LinkedList<Class<?>>();
    private final ReflectedTypeFactory reflectedTypeFactory;

    public TypeHierarchyCalculation(final ReflectedTypeFactory reflectedTypeFactory) {
        this.reflectedTypeFactory = reflectedTypeFactory;
    }

    List<ReflectedClass<?>> interfacesAndSuperClass(final Class<?> klassToReflect) {
        queueSuperclassAndInterfaces(klassToReflect);
        processesPendingTypes();
        return result;
    }

    private void processesPendingTypes() {
        while (!pending.isEmpty()) {
            processClass(pending.remove(0));
        }
    }

    private void processClass(final Class<?> klassToReflect) {
        queueSuperclassAndInterfaces(klassToReflect);
        if (done.contains(klassToReflect)) {
            return;
        }
        done.add(klassToReflect);
        result.add(reflectedTypeFactory.reflect(klassToReflect));
    }

    private void queueSuperclassAndInterfaces(final Class<?> klassToReflect) {
        if (klassToReflect.getSuperclass() != null && !klassToReflect.getSuperclass().equals(Object.class)) {
            pending.add(klassToReflect.getSuperclass());
        }
        final Class<?>[] interfaces = klassToReflect.getInterfaces();
        for (final Class<?> interfac3 : interfaces) {
            pending.add(interfac3);
        }
    }
}