package com.lexicalscope.fluentreflection.bean;

import java.util.Map;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.FluentMember;
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

public interface BeanMapBuilder {
    Map<String, Object> build(Object bean);

    BeanMapBuilder keys(KeySetCalculation onlyReadWriteProperties);

    BeanMapBuilder getters(Matcher<FluentMember> getterMatcher);

    BeanMapBuilder setters(Matcher<FluentMember> setterMatcher);

    BeanMapBuilder propertyNames(PropertyNameConvertor propertyNameConvertor);
}
