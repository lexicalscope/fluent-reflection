package com.lexicalscope.fluentreflection;

import ch.lambdaj.function.convert.Converter;

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

class ConvertReflectedMethodToBoundReflectedMethod implements Converter<FluentMethod, FluentMethod> {
    private final FluentObject<?> instance;
    private final ReflectedTypeFactory reflectedTypeFactory;

    public ConvertReflectedMethodToBoundReflectedMethod(final ReflectedTypeFactory reflectedTypeFactory, final FluentObject<?> instance) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.instance = instance;
    }

    @Override
    public FluentMethod convert(final FluentMethod from) {
        return new BoundFluentMethodImpl(reflectedTypeFactory, from, instance);
    }
}
