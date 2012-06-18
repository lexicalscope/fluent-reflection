package com.lexicalscope.fluentreflection.bean;

import static com.google.common.collect.Sets.*;
import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static java.util.Collections.unmodifiableSet;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.hamcrest.Matcher;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.convert.Converter;

import com.lexicalscope.fluentreflection.ReflectedMember;
import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectedObject;

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

        BeanMapImpl(
                final ReflectedObject<Object> object,
                final PropertyNameConvertor propertyNameConvertor,
                final Matcher<ReflectedMember> getterMatcher,
                final Matcher<ReflectedMember> setterMatcher,
                final KeySetCalculation keySetCalculation) {
            this.getters = indexMethods(object, getterMatcher, propertyNameConvertor);
            this.setters = indexMethods(object, setterMatcher, propertyNameConvertor);
            this.keySet = unmodifiableSet(keySetCalculation.supportedKeys(getters, setters));
        }

        private Map<String, ReflectedMethod> indexMethods(
                final ReflectedObject<Object> object,
                final Matcher<ReflectedMember> matcher,
                final PropertyNameConvertor propertyNameConvertor) {
            return Lambda.map(
                    object.methods(matcher),
                    new Converter<ReflectedMethod, String>() {
                        @Override public String convert(final ReflectedMethod from) {
                            return propertyNameConvertor.propertyName(from);
                        }
                    });
        }

        @Override public void clear() {
            throw new UnsupportedOperationException("clear is not supported on " + BeanMap.class.getSimpleName());
        }

        @Override public boolean containsKey(final Object key) {
            return keySet.contains(key);
        }

        @Override public boolean containsValue(final Object value) {
            for (final String string : keySet) {
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
            return new BeanMapEntrySet();
        }

        @Override public Object get(final Object key) {
            final ReflectedMethod getter = getters.get(key);
            if (getter == null) {
                return null;
            }
            return getter.callRaw();
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
                setter.callRaw(value);
            }
            return oldValue;
        }

        @Override public void putAll(final Map<? extends String, ? extends Object> m) {
            for (final Entry<? extends String, ? extends Object> entry : m.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }

        @Override public Object remove(final Object key) {
            throw new UnsupportedOperationException("remove is not supported on " + BeanMap.class.getSimpleName());
        }

        @Override public int size() {
            return keySet.size();
        }

        @Override public Collection<Object> values() {
            final Collection<Object> result = new ArrayList<Object>(keySet.size());
            for (final String key : keySet) {
                result.add(get(key));
            }
            return result;
        }

        private final class BeanMapEntrySet extends AbstractSet<Entry<String, Object>> {
            @Override public Iterator<Entry<String, Object>> iterator() {
                return new Iterator<Entry<String, Object>>() {
                    private final Iterator<String> keyIterator = keySet.iterator();

                    @Override public boolean hasNext() {
                        return keyIterator.hasNext();
                    }

                    @Override public Entry<String, Object> next() {
                        return new Entry<String, Object>() {
                            private final String key = keyIterator.next();
                            private final Object value = get(key);

                            @Override public Object setValue(final Object value) {
                                return put(key, value);
                            }

                            @Override public Object getValue() {
                                return value;
                            }

                            @Override public String getKey() {
                                return key;
                            }

                            @Override public boolean equals(final Object that) {
                                if (that != null && that.getClass().equals(this.getClass())) {
                                    final Entry<?, ?> thatEntry = (Entry<?, ?>) that;

                                    return (key == null ? thatEntry.getKey() == null : key.equals(thatEntry.getKey()))
                                            &&
                                            (value == null ? thatEntry.getValue() == null : value.equals(thatEntry
                                                    .getValue()));
                                }
                                return false;
                            }

                            @Override public int hashCode() {
                                return key.hashCode() ^ (value == null ? 0 : value.hashCode());
                            }

                            @Override public String toString() {
                                return key + "=" + value;
                            }
                        };
                    }

                    @Override public void remove() {
                        throw new UnsupportedOperationException("remove is not supported on "
                                + BeanMap.class.getSimpleName());
                    }
                };
            }

            @Override public int size() {
                return keySet.size();
            }
        }
    }

    public static interface KeySetCalculation {
        Set<String> supportedKeys(Map<String, ReflectedMethod> getters, Map<String, ReflectedMethod> setters);
    }

    public static interface PropertyNameConvertor {
        String propertyName(ReflectedMethod method);
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
        return beanMap().build(bean);
    }

    public static BeanMapBuilder beanMap() {
        return new BeanMapBuilderImpl();
    }

    static Map<String, Object> map(
            final Object bean,
            final PropertyNameConvertor propertyNameConvertor,
            final Matcher<ReflectedMember> getterMatcher,
            final Matcher<ReflectedMember> setterMatcher,
            final KeySetCalculation keySetCalculation) {
        return new BeanMapImpl(
                object(bean),
                propertyNameConvertor,
                getterMatcher,
                setterMatcher,
                keySetCalculation);
    }

    public static KeySetCalculation onlyReadWriteProperties() {
        return new KeySetCalculation() {
            @Override public Set<String> supportedKeys(
                        final Map<String, ReflectedMethod> getters,
                        final Map<String, ReflectedMethod> setters) {
                return intersection(getters.keySet(), setters.keySet());
            }
        };
    }

    public static KeySetCalculation allReadableProperties() {
        return new KeySetCalculation() {
            @Override public Set<String> supportedKeys(
                        final Map<String, ReflectedMethod> getters,
                        final Map<String, ReflectedMethod> setters) {
                return getters.keySet();
            }
        };
    }

    public static KeySetCalculation allWriteableProperties() {
        return new KeySetCalculation() {
            @Override public Set<String> supportedKeys(
                        final Map<String, ReflectedMethod> getters,
                        final Map<String, ReflectedMethod> setters) {
                return setters.keySet();
            }
        };
    }

    public static KeySetCalculation writeablePropertiesOnly() {
        return new KeySetCalculation() {
            @Override public Set<String> supportedKeys(
                        final Map<String, ReflectedMethod> getters,
                        final Map<String, ReflectedMethod> setters) {
                return difference(setters.keySet(), getters.keySet());
            }
        };
    }

    public static KeySetCalculation readablePropertiesOnly() {
        return new KeySetCalculation() {
            @Override public Set<String> supportedKeys(
                        final Map<String, ReflectedMethod> getters,
                        final Map<String, ReflectedMethod> setters) {
                return difference(getters.keySet(), setters.keySet());
            }
        };
    }

    public static KeySetCalculation allProperties() {
        return new KeySetCalculation() {
            @Override public Set<String> supportedKeys(
                        final Map<String, ReflectedMethod> getters,
                        final Map<String, ReflectedMethod> setters) {
                return union(getters.keySet(), setters.keySet());
            }
        };
    }

    public static PropertyNameConvertor lowercasePropertyName() {
        return new PropertyNameConvertor() {
            @Override public String propertyName(final ReflectedMethod method) {
                return method.propertyName().toLowerCase();
            }
        };
    }
}
