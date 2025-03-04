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

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * The model class for the <strong>Repeat</strong> field action.
 */
public class Repeat extends Action implements Serializable {

    private static final long serialVersionUID = 1L;

    /** count */
    private Integer count;

    /**
     * Gets the count.
     * @return count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Sets the count.
     * @param count count
     */
    @JsonPropertyDescription("count ")
    @AtlasActionProperty(title = "count", type = FieldType.INTEGER)
    public void setCount(Integer count) {
        this.count = count;
    }
}

