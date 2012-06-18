package com.lexicalscope.fluentreflection;

import java.lang.reflect.Field;

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

class BoundReflectedFieldImpl extends BoundReflectedMemberImpl implements ReflectedField {
    private final ReflectedField field;
    private final Object instance;

    public BoundReflectedFieldImpl(
            final ReflectedTypeFactory reflectedTypeFactory,
            final ReflectedField field,
            final Object instance) {
        super(reflectedTypeFactory, field, instance);
        this.field = field;
        this.instance = instance;
    }

    @Override public Field memberUnderReflection() {
        return field.memberUnderReflection();
    }

    @Override public String toString() {
        return String.format("%s in %s", field, instance);
    }
}
