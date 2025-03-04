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

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The process mapping request model object.
 */
@JsonRootName("ProcessMappingRequest")
@JsonTypeInfo(include = JsonTypeInfo.As.PROPERTY, use = JsonTypeInfo.Id.CLASS, property = "jsonType")
public class ProcessMappingRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /** single {@link Mapping} entry */
    protected Mapping mapping;

    /** whole {@link AtlasMapping} */
    protected AtlasMapping atlasMapping;

    /**
     * Gets the value of the mapping property.
     * 
     * @return
     *     possible object is
     *     {@link Mapping }
     *     
     */
    public Mapping getMapping() {
        return mapping;
    }

    /**
     * Sets the value of the mapping property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mapping }
     *     
     */
    public void setMapping(Mapping value) {
        this.mapping = value;
    }

    /**
     * Gets the value of the atlasMapping property.
     * 
     * @return
     *     possible object is
     *     {@link AtlasMapping }
     *     
     */
    public AtlasMapping getAtlasMapping() {
        return atlasMapping;
    }

    /**
     * Sets the value of the atlasMapping property.
     * 
     * @param value
     *     allowed object is
     *     {@link AtlasMapping }
     *     
     */
    public void setAtlasMapping(AtlasMapping value) {
        this.atlasMapping = value;
    }

}
