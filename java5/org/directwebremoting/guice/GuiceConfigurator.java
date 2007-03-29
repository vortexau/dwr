/*
 * Copyright 2007 Tim Peierls
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.directwebremoting.guice;

import org.directwebremoting.fluent.FluentConfigurator;

/**
 * Extends {@link FluentConfigurator} with type-safe methods to be more Guice-friendly.
 *
 * @todo Type-safe versions of withCreator/withCreatorType/withFilter/etc.
 */
public abstract class GuiceConfigurator extends FluentConfigurator {

    /**
     * Safer version of {@link FluentConfigurator#withConverterType withConverterType}
     * taking a class instead of a class name.
     */
    public GuiceConfigurator withConverterType(String id, Class<?> type)
    {
        withConverterType(id, type.getName());
        return this;
    }

    /**
     * Safer version of {@link FluentConfigurator#withConverter withConverter}
     * taking a class instead of a class name.
     */
    public GuiceConfigurator withConverter(String id, Class<?> type)
    {
        withConverter(id, type.getName());
        return this;
    }

    /**
     * Safer version of {@link FluentConfigurator#withCreatorType withCreatorType}
     * taking a class instead of a class name.
     */
    public GuiceConfigurator withCreatorType(String id, Class<?> type)
    {
        withCreatorType(id, type.getName());
        return this;
    }

    /**
     * Safer version of {@link FluentConfigurator#withFilter withFilter}
     * taking a class instead of a class name.
     */
    public GuiceConfigurator withFilter(Class<?> type)
    {
        withFilter(type.getName());
        return this;
    }

    /**
     * Safer version of {@link FluentConfigurator#addFilter addFilter}
     * taking a class instead of a class name.
     */
    public GuiceConfigurator addFilter(Class<?> type)
    {
        addFilter(type.getName());
        return this;
    }
}
