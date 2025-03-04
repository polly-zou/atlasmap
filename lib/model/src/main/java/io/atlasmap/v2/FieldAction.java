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
package io.atlasmap.v2;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The base interface of field action implementation class.
 */
public interface FieldAction {

    /**
     * Gets the display name.
     * @return display name
     */
    @JsonIgnore
    default String getDisplayName() {
        // TODO display name should be more human readable one instead of class name
        return this.getClass().getSimpleName();
    }
}
