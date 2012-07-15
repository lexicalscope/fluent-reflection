package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.lang.reflect.Field;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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

public class TestBoundReflectedFieldImpl {
    @Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock private FluentField member;
    private final FluentObject<?> instance = object(new Object());

    private Object field;
    private final Field fieldReference;

    private BoundFluentFieldImpl boundReflectedFieldImpl;

    public TestBoundReflectedFieldImpl() throws SecurityException, NoSuchFieldException {
        fieldReference = TestBoundReflectedFieldImpl.class.getDeclaredField("field");
    }

    @Before public void setUp() {
        context.checking(new Expectations() {
            {
                oneOf(member).isStatic(); will(returnValue(false));
            }
        });

        boundReflectedFieldImpl = new BoundFluentFieldImpl(null, member, instance);
    }

    @Test public void memberUnderReflectionIsDelegated() {
        context.checking(new Expectations() {
            {
                oneOf(member).member(); will(returnValue(fieldReference));
            }
        });
        assertThat(boundReflectedFieldImpl.member(), equalTo(fieldReference));
    }
}
