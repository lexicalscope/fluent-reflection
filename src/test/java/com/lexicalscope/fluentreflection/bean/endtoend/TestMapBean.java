package com.lexicalscope.fluentreflection.bean.endtoend;

import static com.lexicalscope.fluentreflection.bean.MapBean.bean;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

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

public class TestMapBean {
    interface Bean {
        String getProperty();
        Boolean hasProperty();
        Boolean isProperty();
        void setProperty(String value);

        int getInt();
        void setInt(int integer);

        char getChar();
        void setChar(char character);

        Integer getInteger();
        void setInteger(Integer integer);

        Iterable<Integer> getIterable();
        void setIterable(Iterable<Integer> integer);

        Collection<Integer> getCollection();
        void setCollection(Collection<Integer> integer);

        Set<Integer> getSet();
        void setCollection(Set<Integer> integer);

        List<Integer> getList();
        void setCollection(List<Integer> integer);
    }

    private final Map<String, Object> map = new HashMap<String, Object>();
    private final Bean bean = bean(Bean.class, map);

    @Test public void mapCanBeQueriedViaInterface() throws Exception {
        assertThat(bean.isProperty(), equalTo(false));

        bean.setProperty("my value");

        assertThat(bean.isProperty(), equalTo(true));
    }

    @Test public void mapCanBeReadViaInterface() throws Exception {
        map.put("property", "my value");
        assertThat(bean.getProperty(), equalTo("my value"));
    }

    @Test public void mapCanBeSetViaInterface() throws Exception {
        bean.setProperty("my value");

        assertThat(map.get("property"), equalTo((Object) "my value"));
    }

    @Test public void argumentConversionTakesPlaceOnGet() throws Exception {
        map.put("integer", "14");

        assertThat(bean.getInteger(), equalTo(14));
    }

    @Test public void argumentConversionCanConvertFromStringToPrimitive() throws Exception {
        map.put("int", "14");

        assertThat(bean.getInt(), equalTo(14));
    }

    @Test public void argumentConversionCanConvertFromStringToChar() throws Exception {
        map.put("char", "c");

        assertThat(bean.getChar(), equalTo('c'));
    }

    @Test public void argumentConversionTakesPlaceOnGetOfIterable() throws Exception {
        map.put("iterable", Arrays.asList("14", "15", "16"));

        assertThat(bean.getIterable(), contains(14, 15, 16));
    }

    @Test public void argumentConversionTakesPlaceOnGetOfCollection() throws Exception {
        map.put("collection", Arrays.asList("14", "15", "16"));

        assertThat(bean.getCollection(), contains(14, 15, 16));
    }

    @Test public void argumentConversionTakesPlaceOnGetOfList() throws Exception {
        map.put("list", Arrays.asList("14", "15", "16"));

        assertThat(bean.getList(), contains(14, 15, 16));
    }

    @Test public void argumentConversionTakesPlaceOnGetOfSet() throws Exception {
        map.put("set", Sets.newHashSet("14", "15", "16"));

        assertThat(bean.getSet(), contains(14, 15, 16));
    }

    @Test public void mapBeanEqualToItself() throws Exception {
        assertThat(bean, equalTo(bean));
        assertThat(bean.hashCode(), equalTo(bean.hashCode()));
    }

    @Test public void mapBeanNotEqualToSecondBeanOnTheSameMap() throws Exception {
        assertThat(bean, not(equalTo(bean(Bean.class, map))));
    }
}
