package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.callableHasArguments;
import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

import com.google.inject.TypeLiteral;

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

public class TestReflectedTypeImpl {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    private final ReflectedTypeFactory reflectedTypeFactory = context.mock(ReflectedTypeFactory.class);
    private final ReflectedMembers<ExampleClass> members =
            context.mock(new TypeLiteral<ReflectedMembers<ExampleClass>>() {
            });

    private final ReflectedConstructor<ExampleClass> reflectedConstructor =
            context.mock(new TypeLiteral<ReflectedConstructor<ExampleClass>>() {
            });

    private final ReflectedInstance<ExampleClass> reflectedInstance =
            context.mock(new TypeLiteral<ReflectedInstance<ExampleClass>>() {
            });

    class ExampleClass {
        int method() {
            return 42;
        }
    }

    private final ExampleClass instance = new ExampleClass();

    @Test
    public void constructFindsConstructorAndBindsReflectedResult() throws Exception {
        context.checking(new Expectations() {
            {
                oneOf(members).constructor(callableHasArguments());
                will(returnValue(reflectedConstructor));

                oneOf(reflectedConstructor).call();
                will(returnValue(instance));

                oneOf(reflectedTypeFactory).reflect(ExampleClass.class, instance);
                will(returnValue(reflectedInstance));
            }
        });

        final ReflectedTypeImpl<ExampleClass> reflectedTypeImpl =
                new ReflectedTypeImpl<ExampleClass>(reflectedTypeFactory, ExampleClass.class, members);

        assertThat(reflectedTypeImpl.construct(), Matchers.equalTo(reflectedInstance));
    }
}
