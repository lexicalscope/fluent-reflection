package com.lexicalscope.fluentreflection.bean.endtoend;

import static org.hamcrest.Matchers.*;

import java.util.Map.Entry;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

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

class MapEntryMatcher<K, V> extends TypeSafeMatcher<Entry<K, V>> {
    private final Matcher<? super K> key;
    private final Matcher<? super V> value;

    private MapEntryMatcher(final Matcher<? super K> key, final Matcher<? super V> value) {
        this.key = key;
        this.value = value;
    }

    @Override protected boolean matchesSafely(final Entry<K, V> item) {
        return key.matches(item.getKey()) && value.matches(item.getValue());
    }

    @Override public void describeTo(final Description description) {
        description.
                appendText("Map.Entry ").
                appendDescriptionOf(key).
                appendText(" -> ").
                appendDescriptionOf(value);
    }

    public static <K, V> MapEntryMatcher<K, V> mapEntry(final K key, final V value) {
        return new MapEntryMatcher<K, V>(equalTo(key), equalTo(value));
    }

    public static <K, V> MapEntryMatcher<K, V> mapEntry(final K key) {
        return new MapEntryMatcher<K, V>(equalTo(key), anything());
    }
}
