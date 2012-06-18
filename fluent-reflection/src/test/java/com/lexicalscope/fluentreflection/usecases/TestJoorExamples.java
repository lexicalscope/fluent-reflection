package com.lexicalscope.fluentreflection.usecases;

import static com.lexicalscope.fluentreflection.FluentReflection.*;
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

public class TestJoorExamples {
    /**
     * String world = on("java.lang.String")  // Like Class.forName()
     *                 .create("Hello World") // Call the most specific matching constructor
     *                 .call("substring", 6)  // Call the most specific matching substring() method
     *                 .call("toString")      // Call toString()
     *                 .get();                 // Get the wrapped object, in this case a String
     */
    @Test public void joorStringExample()
    {
        assertThat(type("java.lang.String").
                        construct("Hello World").
                        call("substring", 6).
                        call("toString").
                        as(String.class),
                   equalTo("World"));
    }

    /**
     * Employee[] employees = on(department).call("getEmployees").get();
     * for (Employee employee : employees) {
     *     Street street = on(employee).call("getAddress").call("getStreet").get();
     *     System.out.println(street);
     * }
     */
    @Test @SuppressWarnings("unused") public void joorArrayExample() {
        class Address {
            private final String street;
            public Address(final String street) { this.street = street; }
            String getStreet() { return street; }
        }
        class Employee {
            private final String street;
            public Employee(final String street) { this.street = street; }
            Address getAddress() { return new Address(street); }
        }
        class Department {
            Employee[] getEmployees() { return new Employee[]{new Employee("street0"), new Employee("street1")}; }
        }

        final Employee[] employees = object(new Department()).call("getEmployees").as(Employee[].class);
        for (int i = 0; i < employees.length; i++) {
            assertThat(
                    object(employees[i]).call("getAddress").call("getStreet").as(String.class),
                    equalTo("street" + i));
        }
    }
}
