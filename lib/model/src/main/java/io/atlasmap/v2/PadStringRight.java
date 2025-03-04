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
 * The model class for the <strong>Pad String Right</strong> field action.
 */
public class PadStringRight extends Action implements Serializable {

    private static final long serialVersionUID = 1L;

    /** pad character */
    protected String padCharacter;

    /** pad count */
    protected Integer padCount;

    /**
     * Gets the value of the padCharacter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPadCharacter() {
        return padCharacter;
    }

    /**
     * Sets the value of the padCharacter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @JsonPropertyDescription("The character to fill padding")
    @AtlasActionProperty(title = "Padding character", type = FieldType.STRING)
    public void setPadCharacter(String value) {
        this.padCharacter = value;
    }

    /**
     * Gets the value of the padCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPadCount() {
        return padCount;
    }

    /**
     * Sets the value of the padCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    @JsonPropertyDescription("The number of padding character to fill")
    @AtlasActionProperty(title = "Padding count", type = FieldType.STRING)
    public void setPadCount(Integer value) {
        this.padCount = value;
    }

}
