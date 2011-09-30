package com.lexicalscope.fluentreflection.bean.endtoend;

import static com.lexicalscope.fluentreflection.bean.MapBean.bean;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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

public class TestMapBean {
    interface Bean {
        String getProperty();

        void setProperty(String value);
    }

    private final Map<String, Object> map = new HashMap<String, Object>();
    private final Bean bean = bean(Bean.class, map);

    @Test public void mapCanBeReadViaInterface() throws Exception {
        map.put("property", "my value");
        assertThat(bean.getProperty(), equalTo("my value"));
    }

    @Test public void mapCanBeSetViaInterface() throws Exception {
        bean.setProperty("my value");
        assertThat(map.get("property"), equalTo((Object) "my value"));
    }
}
