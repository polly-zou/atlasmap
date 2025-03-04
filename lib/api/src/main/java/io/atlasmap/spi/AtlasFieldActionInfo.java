/*
 * Copyright (C) 2017 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.atlasmap.spi;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.atlasmap.v2.CollectionType;
import io.atlasmap.v2.FieldType;

/**
 * AtlasFieldAction.
 * @deprecated Use {@link io.atlasmap.spi.AtlasActionProcessor} instead
 */
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
public @interface AtlasFieldActionInfo {

    /**
     * Gets the name.
     * @return name
     */
    String name();

    /**
     * Gets the source type.
     * @return source type
     */
    FieldType sourceType();

    /**
     * Gets the target type.
     * @return target type
     */
    FieldType targetType();

    /**
     * Gets the source collection type.
     * @return source collection type
     */
    CollectionType sourceCollectionType();

    /**
     * Gets the target collection type.
     * @return target collection type
     */
    CollectionType targetCollectionType();
}
