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
package io.atlasmap.kafkaconnect.v2;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The complex field type for Kafka Connect module.
 */
@JsonTypeInfo(include = JsonTypeInfo.As.PROPERTY, use = JsonTypeInfo.Id.CLASS, property = "jsonType")
public class KafkaConnectComplexType extends KafkaConnectField {

    private static final long serialVersionUID = 1L;

    /** children. */
    protected KafkaConnectFields kafkaConnectFields;
    /** enum fields. */
    protected KafkaConnectEnumFields kafkaConnectEnumFields;
    /** true if it's an enum. */
    protected Boolean enumeration;
    /** URI. */
    protected String uri;

    /**
     * Gets the value of the kafkaConnectFields property.
     * 
     * @return
     *     possible object is
     *     {@link KafkaConnectFields }
     *     
     */
    public KafkaConnectFields getKafkaConnectFields() {
        return kafkaConnectFields;
    }

    /**
     * Sets the value of the kafkaConnectFields property.
     * 
     * @param value
     *     allowed object is
     *     {@link KafkaConnectFields }
     *     
     */
    public void setKafkaConnectFields(KafkaConnectFields value) {
        this.kafkaConnectFields = value;
    }

    /**
     * Gets the value of the kafkaConnectEnumFields property.
     * 
     * @return
     *     possible object is
     *     {@link KafkaConnectEnumFields }
     *     
     */
    public KafkaConnectEnumFields getKafkaConnectEnumFields() {
        return this.kafkaConnectEnumFields;
    }

    /**
     * Sets the value of the kafkaConnectEnumFields property.
     * 
     * @param value
     *     allowed object is
     *     {@link KafkaConnectEnumFields }
     *     
     */
    public void setKafkaConnectEnumFields(KafkaConnectEnumFields value) {
        this.kafkaConnectEnumFields = value;
    }

    /**
     * Gets the value of the enumeration property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnumeration() {
        return enumeration;
    }

    /**
     * Sets the value of the enumeration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnumeration(Boolean value) {
        this.enumeration = value;
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
        final KafkaConnectComplexType that = ((KafkaConnectComplexType) object);
        {
            KafkaConnectFields leftKafkaConnectFields;
            leftKafkaConnectFields = this.getKafkaConnectFields();
            KafkaConnectFields rightKafkaConnectFields;
            rightKafkaConnectFields = that.getKafkaConnectFields();
            if (this.kafkaConnectFields!= null) {
                if (that.kafkaConnectFields!= null) {
                    if (!leftKafkaConnectFields.equals(rightKafkaConnectFields)) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                if (that.kafkaConnectFields!= null) {
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
            KafkaConnectFields theKafkaConnectFields;
            theKafkaConnectFields = this.getKafkaConnectFields();
            if (this.kafkaConnectFields!= null) {
                currentHashCode += theKafkaConnectFields.hashCode();
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
