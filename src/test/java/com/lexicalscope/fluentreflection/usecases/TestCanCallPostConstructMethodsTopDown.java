package com.lexicalscope.fluentreflection.usecases;

import static ch.lambdaj.Lambda.forEach;
import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.callableAnnotatedWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.Test;

import com.lexicalscope.fluentreflection.ReflectedMethod;

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

public class TestCanCallPostConstructMethodsTopDown {
    interface TopInterface {
        void afterConstruction();
    }

    static class AfterConstruction implements TopInterface {
        List<String> result = new ArrayList<String>();

        @Override
        @PostConstruct
        public void afterConstruction() {
            result.add("afterConstruction");
        }
    }

    static class AfterConstructionExtension extends AfterConstruction {
        @PostConstruct
        public void afterConstructionExtension() {
            result.add("afterConstructionExtension");
        }
    }

    @Test
    public void canCallPostConstructMethodsInTheCorrectOrder() throws Exception {
        final AfterConstructionExtension subject = new AfterConstructionExtension();

        forEach(
                object(subject).methods(callableAnnotatedWith(PostConstruct.class)),
                ReflectedMethod.class).call();

        assertThat(subject.result, contains("afterConstruction", "afterConstructionExtension"));
    }
}
