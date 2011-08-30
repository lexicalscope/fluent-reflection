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

import static com.lexicalscope.fluentreflection.ReflectedTypeImpl.createReflectedType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class TypeHierarchyCalculation {
    private final List<ReflectedType<?>> result = new ArrayList<ReflectedType<?>>();
    private final List<Class<?>> pending = new LinkedList<Class<?>>();

    List<ReflectedType<?>> interfacesAndSuperClass(final Class<?> klassToReflect) {
        pending.add(klassToReflect);
        while (!pending.isEmpty()) {
            processClass(pending.remove(0));
        }
        return result;
    }

    private void processClass(final Class<?> klassToReflect) {
        if (result.contains(klassToReflect)) {
            return;
        }

        if (klassToReflect.getSuperclass() != null && !klassToReflect.getSuperclass().equals(Object.class)) {
            pending.add(klassToReflect.getSuperclass());
        }
        final Class<?>[] interfaces = klassToReflect.getInterfaces();
        for (final Class<?> interfac3 : interfaces) {
            pending.add(interfac3);
        }
        result.add(createReflectedType(klassToReflect));
    }
}