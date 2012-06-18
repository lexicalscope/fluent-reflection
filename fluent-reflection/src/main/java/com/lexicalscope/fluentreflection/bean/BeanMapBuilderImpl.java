package com.lexicalscope.fluentreflection.bean;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;
import static com.lexicalscope.fluentreflection.bean.BeanMap.allProperties;

import java.util.Map;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.FluentMember;
import com.lexicalscope.fluentreflection.FluentMethod;
import com.lexicalscope.fluentreflection.bean.BeanMap.KeySetCalculation;
import com.lexicalscope.fluentreflection.bean.BeanMap.PropertyNameConvertor;

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

class BeanMapBuilderImpl implements BeanMapBuilder {
    private PropertyNameConvertor propertyNameConvertor = new PropertyNameConvertor() {
        @Override public String propertyName(final FluentMethod from) {
            return from.property();
        }
    };
    private Matcher<FluentMember> getterMatcher = isGetter();
    private Matcher<FluentMember> setterMatcher = isSetter();
    private KeySetCalculation keySetCalculation = allProperties();

    @Override public Map<String, Object> build(final Object bean) {
        return BeanMap.map(bean, propertyNameConvertor, getterMatcher, setterMatcher, keySetCalculation);
    }

    @Override public BeanMapBuilder keys(final KeySetCalculation keySetCalculation) {
        this.keySetCalculation = keySetCalculation;
        return this;
    }

    @Override public BeanMapBuilder getters(final Matcher<FluentMember> getterMatcher) {
        this.getterMatcher = getterMatcher;
        return this;
    }

    @Override public BeanMapBuilder setters(final Matcher<FluentMember> setterMatcher) {
        this.setterMatcher = setterMatcher;
        return this;
    }

    @Override public BeanMapBuilder propertyNames(final PropertyNameConvertor propertyNameConvertor) {
        this.propertyNameConvertor = propertyNameConvertor;
        return this;
    }
}
