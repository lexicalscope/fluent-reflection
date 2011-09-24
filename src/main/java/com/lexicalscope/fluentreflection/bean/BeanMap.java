package com.lexicalscope.fluentreflection.bean;

import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.convert.Converter;

import com.lexicalscope.fluentreflection.ReflectedCallable;
import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectedObject;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

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

/**
 * Create a map by reflecting on bean properties
 * 
 * If you do not need the configurability of this class, consider using
 * {@link net.sf.cglib.beans.BeanMap} instead for performance reasons.
 * 
 * @author Tim Wood
 */
public class BeanMap {
    private static final class BeanMapImpl implements Map<String, Object> {
        private final Map<String, ReflectedMethod> getters;
        private final Map<String, ReflectedMethod> setters;
        private final Set<String> keySet;

        public BeanMapImpl(final ReflectedObject<Object> object) {
            getters = indexMethods(object, isGetter());
            setters = indexMethods(object, isSetter());
            final HashSet<String> keySet = new HashSet<String>(getters.keySet());
            keySet.addAll(setters.keySet());
            this.keySet = Collections.unmodifiableSet(keySet);
        }

        private Map<String, ReflectedMethod> indexMethods(
                final ReflectedObject<Object> object,
                final ReflectionMatcher<ReflectedCallable> matcher) {
            return Lambda.map(
                    object.methods(matcher),
                    new Converter<ReflectedMethod, String>() {
                        @Override public String convert(final ReflectedMethod from) {
                            return from.propertyName();
                        }
                    });
        }

        @Override public void clear() {
            throw new UnsupportedOperationException("clear is not supported on " + BeanMap.class.getSimpleName());
        }

        @Override public boolean containsKey(final Object key) {
            return getters.containsKey(key) || setters.containsKey(key);
        }

        @Override public boolean containsValue(final Object value) {
            for (final String string : getters.keySet()) {
                final Object containedValue = get(string);
                if (containedValue == value) {
                    return true;
                } else if (value != null && value.equals(containedValue)) {
                    return true;
                }
            }
            return false;
        }

        @Override public Set<Entry<String, Object>> entrySet() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override public Object get(final Object key) {
            final ReflectedMethod getter = getters.get(key);
            if (getter == null) {
                return null;
            }
            return getter.call();
        }

        @Override public boolean isEmpty() {
            return keySet.isEmpty();
        }

        @Override public Set<String> keySet() {
            return keySet;
        }

        @Override public Object put(final String key, final Object value) {
            if (!keySet.contains(key)) {
                throw new IllegalArgumentException("map does not allow key: " + key);
            }

            final Object oldValue = get(key);
            final ReflectedMethod setter = setters.get(key);
            if (setter != null) {
                setter.call(value);
            }
            return oldValue;
        }

        @Override public void putAll(final Map<? extends String, ? extends Object> m) {
            for (final Entry<? extends String, ? extends Object> entry : m.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }

        @Override public Object remove(final Object key) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override public int size() {
            return keySet.size();
        }

        @Override public Collection<Object> values() {
            // TODO Auto-generated method stub
            return null;
        }
    }

    /**
     * A map of the properties in the bean. Putting values into the map will
     * update the underlying bean. Getting write only properties will return
     * null. Setting read only properties is ignored.
     * 
     * Removing keys from the map (or any operation that implies removing one or
     * more keys) is not supported.
     * 
     * @param bean
     *            the bean to expose as a map
     * 
     * @return the bean wrapped in a map
     */
    public static Map<String, Object> map(final Object bean) {
        return new BeanMapImpl(object(bean));
    }
}
