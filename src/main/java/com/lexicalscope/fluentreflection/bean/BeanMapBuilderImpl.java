package com.lexicalscope.fluentreflection.bean;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;
import static com.lexicalscope.fluentreflection.bean.BeanMap.map;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ch.lambdaj.function.convert.Converter;

import com.lexicalscope.fluentreflection.ReflectedCallable;
import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectionMatcher;
import com.lexicalscope.fluentreflection.bean.BeanMap.KeySetCalculation;

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

public class BeanMapBuilderImpl implements BeanMapBuilder {
    private final Converter<ReflectedMethod, String> propertyNameConvertor = new Converter<ReflectedMethod, String>() {
        @Override public String convert(final ReflectedMethod from) {
            return from.propertyName();
        }
    };
    private final ReflectionMatcher<ReflectedCallable> getterMatcher = isGetter();
    private final ReflectionMatcher<ReflectedCallable> setterMatcher = isSetter();
    private KeySetCalculation keySetCalculation = new GettersAndSettersKeySet();

    @Override public Map<String, Object> build(final Object bean) {
        return map(bean, propertyNameConvertor, getterMatcher, setterMatcher, keySetCalculation);
    }

    @Override public BeanMapBuilder keys(final KeySetCalculation keySetCalculation) {
        this.keySetCalculation = keySetCalculation;
        return this;
    }

    private static final class GettersAndSettersKeySet implements KeySetCalculation {
        @Override public Set<String> supportedKeys(
                final Map<String, ReflectedMethod> getters,
                final Map<String, ReflectedMethod> setters) {
            final HashSet<String> keySet = new HashSet<String>(getters.keySet());
            keySet.addAll(setters.keySet());
            return keySet;
        }
    }
}
