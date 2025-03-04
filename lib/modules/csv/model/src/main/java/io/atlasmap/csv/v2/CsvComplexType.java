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
package io.atlasmap.csv.v2;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.atlasmap.v2.ComplexType;
import io.atlasmap.v2.Field;

/**
 * The CSV complex field.
 */
@JsonTypeInfo(include = JsonTypeInfo.As.PROPERTY, use = JsonTypeInfo.Id.CLASS, property = "jsonType")
public class CsvComplexType extends CsvField implements Serializable, ComplexType {

    private static final long serialVersionUID = 1L;

    /** Children. */
    protected CsvFields csvFields;
    /** URI. */
    protected String uri;

    /**
     * Gets the value of the csvFields property.
     *
     * @return
     *     possible object is
     *     {@link CsvFields }
     *
     */
    public CsvFields getCsvFields() {
        return csvFields;
    }

    /**
     * Sets the value of the csvFields property.
     *
     * @param value
     *     allowed object is
     *     {@link CsvFields }
     *
     */
    public void setCsvFields(CsvFields value) {
        this.csvFields = value;
    }

    /**
     * Gets the value of the uri property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUri() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUri(String value) {
        this.uri = value;
    }

    @JsonIgnore
    @Override
    public List<? extends Field> getChildFields() {
        return csvFields.getCsvField();
    }

    @Override
    public boolean equals(Object object) {
        if ((object == null)||(this.getClass()!= object.getClass())) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (!super.equals(object)) {
            return false;
        }
        final CsvComplexType that = ((CsvComplexType) object);
        {
            CsvFields leftCsvFields;
            leftCsvFields = this.getCsvFields();
            CsvFields rightCsvFields;
            rightCsvFields = that.getCsvFields();
            if (this.csvFields!= null) {
                if (that.csvFields!= null) {
                    if (!leftCsvFields.equals(rightCsvFields)) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                if (that.csvFields!= null) {
                    return false;
                }
            }
        }
        {
            String leftUri;
            leftUri = this.getUri();
            String rightUri;
            rightUri = that.getUri();
            if (this.uri!= null) {
                if (that.uri!= null) {
                    if (!leftUri.equals(rightUri)) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                if (that.uri!= null) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int currentHashCode = 1;
        currentHashCode = ((currentHashCode* 31)+ super.hashCode());
        {
            currentHashCode = (currentHashCode* 31);
            CsvFields theCsvFields;
            theCsvFields = this.getCsvFields();
            if (this.csvFields!= null) {
                currentHashCode += theCsvFields.hashCode();
            }
        }
        {
            currentHashCode = (currentHashCode* 31);
            String theUri;
            theUri = this.getUri();
            if (this.uri!= null) {
                currentHashCode += theUri.hashCode();
            }
        }
        return currentHashCode;
    }

}
