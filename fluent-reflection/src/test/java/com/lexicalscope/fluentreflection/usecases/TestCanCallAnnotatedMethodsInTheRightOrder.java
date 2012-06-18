package com.lexicalscope.fluentreflection.usecases;

import static ch.lambdaj.Lambda.forEach;
import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.annotatedWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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

public class TestCanCallAnnotatedMethodsInTheRightOrder {
    interface TopInterface {
        void afterConstruction();

        void beforeDestruction();
    }

    static class AfterConstruction implements TopInterface {
        List<String> result = new ArrayList<String>();

        @Override
        @PostConstruct
        public void afterConstruction() {
            result.add("afterConstruction");
        }

        @Override
        @PreDestroy
        public void beforeDestruction() {
            result.add("beforeDestruction");
        }
    }

    static class AfterConstructionExtension extends AfterConstruction {
        @PostConstruct
        public void afterConstructionExtension() {
            result.add("afterConstructionExtension");
        }

        @PreDestroy
        public void beforeDestructionExtension() {
            result.add("beforeDestructionExtension");
        }
    }

    @Test
    public void canCallPostConstructMethodsInTheCorrectOrder() throws Exception {
        final AfterConstructionExtension subject = new AfterConstructionExtension();

        forEach(
                object(subject).methods(annotatedWith(PostConstruct.class)),
                ReflectedMethod.class).callRaw();

        assertThat(subject.result, contains("afterConstruction", "afterConstructionExtension"));
    }

    @Test
    public void canCallPreDestroyMethodsInTheCorrectOrder() throws Exception {
        final AfterConstructionExtension subject = new AfterConstructionExtension();

        forEach(
                reverse(object(subject).methods(annotatedWith(PreDestroy.class))),
                ReflectedMethod.class).callRaw();

        assertThat(subject.result, contains("beforeDestructionExtension", "beforeDestruction"));
    }

    private <T> List<T> reverse(final List<T> list) {
        final ArrayList<T> result = new ArrayList<T>(list);
        Collections.reverse(result);
        return result;
    }
}
