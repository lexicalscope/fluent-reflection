package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static com.lexicalscope.fluentreflection.Visibility.PUBLIC;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.lang.reflect.Member;

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

public class TestBoundReflectedMemberImpl {
    @Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock private FluentMember member;
    private final FluentObject<?> instance = object(new Object());

    private BoundFluentMemberImpl boundReflectedMemberImpl;

    @Before public void setUp() {
        context.checking(new Expectations() {
            {
                oneOf(member).isStatic(); will(returnValue(false));
            }
        });

        boundReflectedMemberImpl = new BoundFluentMemberImpl(member, instance) {
            @Override public Member member() {
                return null;
            }
        };
    }

    @Test public void isStaticIsDelegated() {
        assertThat(boundReflectedMemberImpl.isStatic(), equalTo(false));
    }

    @Test public void isFinalIsDelegated() {
        context.checking(new Expectations() {
            {
                oneOf(member).isFinal(); will(returnValue(true));
            }
        });
        assertThat(boundReflectedMemberImpl.isFinal(), equalTo(true));
    }

    @Test public void visibilityIsDelegated() {
        context.checking(new Expectations() {
            {
                oneOf(member).visibility(); will(returnValue(PUBLIC));
            }
        });
        assertThat(boundReflectedMemberImpl.visibility(), equalTo(PUBLIC));
    }
}
