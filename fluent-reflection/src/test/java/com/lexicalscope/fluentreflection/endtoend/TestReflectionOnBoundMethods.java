package com.lexicalscope.fluentreflection.endtoend;

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

import static com.lexicalscope.fluentreflection.FluentReflection.boundMethod;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class TestReflectionOnBoundMethods {
   public interface ExampleInterface {
      int method();

   }

   public static class ExampleImpl1 implements ExampleInterface {
      @Override public int method() {
         return 42;
      }
   }

   public static class ExampleImpl2 implements ExampleInterface {
      @Override public int method() {
         return 36;
      }
   }

   @Test public void boundMethodCanBeCalled() throws SecurityException, NoSuchMethodException {
      assertThat(boundMethod(new ExampleImpl1(),
                             ExampleImpl1.class.getMethod("method"))
                    .call().value(),
                 equalTo((Object) 42));
   }

   @Test public void boundMethodCanBeRebound() throws SecurityException, NoSuchMethodException {
      assertThat(boundMethod(new ExampleImpl1(),
                             ExampleInterface.class.getMethod("method"))
                    .rebind(new ExampleImpl2()).call().value(),
                 equalTo((Object) 36));
   }

   @Test public void boundMethodCanBeReboundToParentType() throws SecurityException, NoSuchMethodException {
      assertThat(boundMethod(new ExampleImpl1(),
                             ExampleImpl1.class.getMethod("method"))
                    .rebind(new ExampleImpl2()).call().value(),
                 equalTo((Object) 36));
   }
}
