package com.lexicalscope.fluentreflection.usecases;

import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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

public class TestNullHandling {
    public static class LinkedList {
        LinkedList _next;
        Object _node;

        public LinkedList(final String value) {
            _node = value;
        }

        LinkedList next() {
            return _next;
        }

        Object node() {
            return _node;
        }

        public LinkedList append(final String value) {
            _next = new LinkedList(value);
            return this;
        }
    }

    private final LinkedList list = new LinkedList("value0").append("value1");

    @Test public void methodChainHasNoNulls()
    {
        assertThat(object(list).
                        call("next").
                        call("node").
                        as(String.class),
                   equalTo("value1"));
    }

    @Test public void methodChainHasNulls()
    {
        assertThat(object(list).
                call("next").
                call("next").
                call("next").
                call("node").
                as(String.class),
           equalTo(null));
    }

    @Test public void fieldChainHasNoNulls()
    {
        assertThat(object(list).
                        call("_next").
                        call("_node").
                        as(String.class),
                   equalTo("value1"));
    }

    @Test public void fieldChainHasNulls()
    {
        assertThat(object(list).
                call("_next").
                call("_next").
                call("_next").
                call("_node").
                as(String.class),
           equalTo(null));
    }
}
