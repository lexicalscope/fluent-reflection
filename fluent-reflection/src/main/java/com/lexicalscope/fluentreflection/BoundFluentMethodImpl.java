package com.lexicalscope.fluentreflection;

import java.lang.reflect.Method;

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

class BoundFluentMethodImpl extends BoundFluentMemberImpl implements FluentMethod {
    private final FluentMethod method;
    private final FluentObject<?> instance;

    public BoundFluentMethodImpl(
            final FluentMethod method,
            final FluentObject<?> instance) {
        super(method, instance);
        this.method = method;
        this.instance = instance;
    }

    @Override public Method member() {
        return method.member();
    }

    @Override public String toString() {
        return String.format("%s in %s", method, instance);
    }
}
