package com.lexicalscope.fluentreflection;

/*
 * Copyright 2012 Tim Wood
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

abstract class AbstractCall<S> implements FluentCall<S> {
    private final ReflectedTypeFactory reflectedTypeFactory;

    public AbstractCall(final ReflectedTypeFactory reflectedTypeFactory) {
        this.reflectedTypeFactory = reflectedTypeFactory;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" }) @Override public FluentObject<S> call(final Object... args) {
        final S result = callRaw(args);
        if(result == null) {
            return null;
        }
        return reflectedTypeFactory.reflect((Class) result.getClass(), result);
    }
}
