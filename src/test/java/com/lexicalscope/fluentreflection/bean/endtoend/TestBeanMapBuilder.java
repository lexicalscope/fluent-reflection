package com.lexicalscope.fluentreflection.bean.endtoend;

import static com.lexicalscope.fluentreflection.bean.BeanMap.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Map;

import org.junit.Test;

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

public class TestBeanMapBuilder {
    static class Bean {
        private int readWriteProperty;
        private String readOnlyProperty;
        private Object writeOnlyProperty;

        String getReadOnlyProperty() {
            return readOnlyProperty;
        }

        int getReadWriteProperty() {
            return readWriteProperty;
        }

        void setReadWriteProperty(final int readWriteProperty) {
            this.readWriteProperty = readWriteProperty;
        }

        void setWriteOnlyProperty(final Object writeOnlyProperty) {
            this.writeOnlyProperty = writeOnlyProperty;
        }
    }

    private final Bean bean = new Bean();

    @Test public void canSelectOnlyReadWriteProperties() {
        final Map<String, Object> map = beanMap().keys(onlyReadWriteProperties()).build(bean);

        assertThat(map.keySet(), containsInAnyOrder("readWriteProperty"));
    }

    @Test public void canSelectAllReadableProperties() {
        final Map<String, Object> map = beanMap().keys(allReadableProperties()).build(bean);

        assertThat(map.keySet(), containsInAnyOrder("readWriteProperty", "readOnlyProperty"));
    }

    @Test public void canSelectAllWritableProperties() {
        final Map<String, Object> map = beanMap().keys(allWriteableProperties()).build(bean);

        assertThat(map.keySet(), containsInAnyOrder("readWriteProperty", "writeOnlyProperty"));
    }

    @Test public void canSelectWritableOnlyProperties() {
        final Map<String, Object> map = beanMap().keys(writeablePropertiesOnly()).build(bean);

        assertThat(map.keySet(), containsInAnyOrder("writeOnlyProperty"));
    }

    @Test public void canSelectReadableOnlyProperties() {
        final Map<String, Object> map = beanMap().keys(readablePropertiesOnly()).build(bean);

        assertThat(map.keySet(), containsInAnyOrder("readOnlyProperty"));
    }

    @Test public void canLowercaseProperties() {
        final Map<String, Object> map = beanMap().propertyNames(lowercasePropertyName()).build(bean);

        assertThat(map.keySet(), containsInAnyOrder("readonlyproperty", "readwriteproperty", "writeonlyproperty"));

        map.put("writeonlyproperty", "my value");
        assertThat(bean.writeOnlyProperty, equalTo((Object) "my value"));

        map.put("readwriteproperty", 14);
        assertThat(map.get("readwriteproperty"), equalTo((Object) 14));
    }
}
