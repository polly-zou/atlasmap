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
 * The model class for the <strong>Convert Mass Unit</strong> field action.
 */
public class ConvertMassUnit extends Action implements Serializable {

    private static final long serialVersionUID = 1L;

    /** mass unit to convert from */
    protected MassUnitType fromUnit;

    /** mass unit to convert to */
    protected MassUnitType toUnit;

    /**
     * Gets the value of the fromUnit property.
     * 
     * @return
     *     possible object is
     *     {@link MassUnitType }
     *     
     */
    public MassUnitType getFromUnit() {
        return fromUnit;
    }

    /**
     * Sets the value of the fromUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link MassUnitType }
     *     
     */
    @JsonPropertyDescription("The mass unit to convert from")
    @AtlasActionProperty(title = "From", type = FieldType.STRING)
    public void setFromUnit(MassUnitType value) {
        this.fromUnit = value;
    }

    /**
     * Gets the value of the toUnit property.
     * 
     * @return
     *     possible object is
     *     {@link MassUnitType }
     *     
     */
    public MassUnitType getToUnit() {
        return toUnit;
    }

    /**
     * Sets the value of the toUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link MassUnitType }
     *     
     */
    @JsonPropertyDescription("The mass unit to convert to")
    @AtlasActionProperty(title = "To", type = FieldType.STRING)
    public void setToUnit(MassUnitType value) {
        this.toUnit = value;
    }

}
