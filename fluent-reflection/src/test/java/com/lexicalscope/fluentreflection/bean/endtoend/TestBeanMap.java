package com.lexicalscope.fluentreflection.bean.endtoend;

import static ch.lambdaj.Lambda.selectFirst;
import static com.lexicalscope.fluentreflection.bean.BeanMap.map;
import static com.lexicalscope.fluentreflection.bean.endtoend.MapEntryMatcher.mapEntry;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
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

public class TestBeanMap {
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
    private final Map<String, Object> map = map(bean);

    @Test(expected = IllegalArgumentException.class) public void putNonExistentPropertyCausesException() {
        map.put("notAProperty", "anything");
    }

    @Test public void getNonExistentPropertyGivesNull() {
        assertThat(map.get("notAProperty"), equalTo(null));
    }

    @Test public void propertyIsPresent() {
        assertThat(map.containsKey("readWriteProperty"), equalTo(true));
    }

    @Test public void initalValueOfPropertyIsZero() {
        assertThat(map.get("readWriteProperty"), equalTo((Object) 0));
    }

    @Test public void settingValueOfPropertyUpdatesObject() {
        assertThat(map.put("readWriteProperty", 14), equalTo((Object) 0));
        assertThat(bean.getReadWriteProperty(), equalTo(14));
    }

    @Test public void settingValueOfObjectUpdatesMap() {
        bean.setReadWriteProperty(14);
        assertThat(map.get("readWriteProperty"), equalTo((Object) 14));
    }

    @Test public void settingValueOfMapReturnsOldValue() {
        bean.setReadWriteProperty(14);
        assertThat(map.put("readWriteProperty", 15), equalTo((Object) 14));
    }

    @Test public void readOnlyPropertyIsPresent() {
        assertThat(map.containsKey("readOnlyProperty"), equalTo(true));
    }

    @Test public void initalValueOfReadOnlyPropertyIsNull() {
        assertThat(map.get("readOnlyProperty"), equalTo((Object) null));
    }

    @Test public void settingValueOfReadOnlyPropertyIsIgnoredByObject() {
        assertThat(map.put("readOnlyProperty", "14"), equalTo((Object) null));
        assertThat(bean.getReadOnlyProperty(), equalTo(null));
    }

    @Test public void changesToReadOnlyPropertyAreReturnedByMap() {
        bean.readOnlyProperty = "new value";
        assertThat(map.get("readOnlyProperty"), equalTo((Object) "new value"));
    }

    @Test public void settingValueOfReadOnlyPropertyInMapReturnsOldValue() {
        bean.readOnlyProperty = "old value";
        assertThat(map.put("readOnlyProperty", "new value"), equalTo((Object) "old value"));
    }

    @Test public void writeOnlyPropertyIsPresent() {
        assertThat(map.containsKey("writeOnlyProperty"), equalTo(true));
    }

    @Test public void initalValueOfWriteOnlyPropertyIsNull() {
        assertThat(map.get("writeOnlyProperty"), equalTo((Object) null));
    }

    @Test public void settingValueOfWriteOnlyPropertyUpdatesObject() {
        assertThat(map.put("writeOnlyProperty", "new value"), equalTo((Object) null));
        assertThat(bean.writeOnlyProperty, equalTo((Object) "new value"));
    }

    @Test public void changesToWriteOnlyPropertyAreIgnoredByMap() {
        bean.setWriteOnlyProperty("new value");
        assertThat(map.get("writeOnlyProperty"), equalTo((Object) null));
    }

    @Test public void settingValueOfWriteOnlyPropertyInMapReturnsNullValue() {
        bean.setWriteOnlyProperty("old value");
        assertThat(map.put("writeOnlyProperty", "new value"), equalTo((Object) null));
    }

    @Test(expected = UnsupportedOperationException.class) public void clearIsUnsupported() {
        map.clear();
    }

    @Test(expected = UnsupportedOperationException.class) public void removeIsUnsupported() {
        map.remove("writeOnlyProperty");
    }

    @Test public void containsInitialReadableValues() {
        assertThat(map.containsValue(0), equalTo(true));
        assertThat(map.containsValue(null), equalTo(true));
    }

    @Test public void containsChangedReadableValues() {
        bean.setReadWriteProperty(14);
        assertThat(map.containsValue(14), equalTo(true));
        assertThat(map.containsValue(0), equalTo(false));
    }

    @Test public void doesNotContainsWriteOnlyValues() {
        bean.setWriteOnlyProperty("myValue");
        assertThat(map.containsValue("myValue"), equalTo(false));
    }

    @Test public void valuesContainsAllValues() {
        bean.setReadWriteProperty(14);
        bean.readOnlyProperty = "my value";
        assertThat(map.values(), containsInAnyOrder((Object) 14, "my value", null));
    }

    @Test public void keySetContainsAllProperties() {
        assertThat(map.keySet(), containsInAnyOrder("readWriteProperty", "readOnlyProperty", "writeOnlyProperty"));
    }

    @Test public void beanWithPropertiesGivesNotEmptyMap() {
        assertThat(map.isEmpty(), equalTo(false));
    }

    @Test public void beanWithoutPropertiesGivesNotEmptyMap() {
        class EmptyBean {}
        assertThat(map(new EmptyBean()).isEmpty(), equalTo(true));
    }

    @Test public void beanWithPropertiesHasCorrectSize() {
        assertThat(map.size(), equalTo(3));
    }

    @Test public void beanWithoutPropertiesHasZeroSize() {
        class EmptyBean {}
        assertThat(map(new EmptyBean()).size(), equalTo(0));
    }

    @Test public void putAllUpdatesWritableProperties() {
        final Map<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("readWriteProperty", 14);
        newMap.put("writeOnlyProperty", "My Value");

        map.putAll(newMap);

        assertThat(bean.getReadWriteProperty(), equalTo(14));
        assertThat(bean.writeOnlyProperty, equalTo((Object) "My Value"));
    }

    @Test public void putAllIgnoresReadOnlyProperties() {
        final Map<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("readOnlyProperty", "My Value");

        map.putAll(newMap);

        assertThat(bean.getReadOnlyProperty(), equalTo(null));
    }

    @Test(expected = IllegalArgumentException.class) public void putAllWithNonExistentPropertyThrowsException() {
        final Map<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("notAProperty", 14);

        map.putAll(newMap);
    }

    @Test public void entrySetContainsAllPropertiesAndReadableValues() {
        bean.setReadWriteProperty(14);
        bean.readOnlyProperty = "my value";
        assertThat(map.entrySet(), hasItem(mapEntry("readWriteProperty", (Object) 14)));
        assertThat(map.entrySet(), hasItem(mapEntry("readOnlyProperty", (Object) "my value")));
    }

    @Test public void entrySetAllowsValuesToBeChanged() {
        final Map.Entry<String, Object> entry = selectFirst(map.entrySet(), mapEntry("readWriteProperty"));
        entry.setValue(14);
        assertThat(bean.getReadWriteProperty(), equalTo(14));
    }

    @Test(expected = UnsupportedOperationException.class) public void cannotRemoveFromKeySet() {
        map.keySet().remove("readWriteProperty");
    }

    @Test(expected = UnsupportedOperationException.class) public void cannotRemoveFromKeySetIterator() {
        map.keySet().iterator().remove();
    }

    @Test(expected = UnsupportedOperationException.class) public void cannotRemoveFromEntrySet() {
        map.entrySet().remove(map.entrySet().iterator().next());
    }

    @Test(expected = UnsupportedOperationException.class) public void cannotRemoveFromEntrySetIterator() {
        map.entrySet().iterator().remove();
    }
}
