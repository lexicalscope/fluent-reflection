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

import ch.lambdaj.function.convert.Converter;

class ConvertClassToReflectedType implements Converter<Class<?>, ReflectedClass<?>> {
    private final ReflectedTypeFactory reflectedTypeFactory;

    public ConvertClassToReflectedType(final ReflectedTypeFactory reflectedTypeFactory) {
        this.reflectedTypeFactory = reflectedTypeFactory;
    }

    @Override
    public ReflectedClass<?> convert(final Class<?> from) {
        return reflectedTypeFactory.reflect(from);
    }
}
