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
package org.apache.camel.component.atlasmap;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;

import io.atlasmap.api.AtlasConversionException;
import io.atlasmap.api.AtlasSession;
import io.atlasmap.api.AtlasUnsupportedException;
import io.atlasmap.core.DefaultAtlasPropertyStrategy;
import io.atlasmap.v2.PropertyField;

/**
 * AtlasMap property strategy to map Camel message headers and exchange properties to/from
 * AtlasMap properties.
 *
 */
public class CamelAtlasPropertyStrategy extends DefaultAtlasPropertyStrategy {
    /** Exchange property scope. */
    public static final String SCOPE_EXCHANGE_PROPERTY = "camelExchangeProperty";
    /** Current message scope. */
    public static final String SCOPE_CURRENT_MESSAGE_HEADER = "current";

    private Exchange camelExchange;
    private Map<String, Message> sourceMessageMap = new HashMap<>();
    private Message camelTargetMessage;

    @Override
    public void readProperty(AtlasSession session, PropertyField propertyField)
            throws AtlasUnsupportedException, AtlasConversionException {

        String scope = propertyField.getScope();
        String key = propertyField.getName();
        Map<String, Object> target = null;
        if (scope == null && sourceMessageMap.containsKey(SCOPE_CURRENT_MESSAGE_HEADER)) {
            target = sourceMessageMap.get(SCOPE_CURRENT_MESSAGE_HEADER).getHeaders();
        } else if (SCOPE_EXCHANGE_PROPERTY.equals(scope)) {
            target = this.camelExchange.getProperties();
        } else if (sourceMessageMap.containsKey(scope)) {
            target = sourceMessageMap.get(scope).getHeaders();
        }
        if (target != null && target.containsKey(key)) {
            propertyField.setValue(target.get(key));
        } else {
            super.readProperty(session, propertyField);
        }
    }

    @Override
    public void writeProperty(AtlasSession session, PropertyField propertyField) {
        String scope = propertyField.getScope();
        String key = propertyField.getName();
        Object value = propertyField.getValue();
        if (SCOPE_EXCHANGE_PROPERTY.equals(scope)) {
            this.camelExchange.setProperty(key, value);
        } else {
            this.camelTargetMessage.setHeader(key, value);
        }
    }

    /**
     * Sets the camel Exchange.
     * @param ex exchange
     */
    public void setExchange(Exchange ex) {
        this.camelExchange = ex;
    }

    /**
     * Sets the source message.
     * @param documentId Document ID
     * @param msg message
     */
    public void setSourceMessage(String documentId, Message msg) {
        sourceMessageMap.put(documentId, msg);
    }

    /**
     * Sets the current source message.
     * @param msg message
     */
    public void setCurrentSourceMessage(Message msg) {
        sourceMessageMap.put(SCOPE_CURRENT_MESSAGE_HEADER, msg);
    }

    /**
     * Sets the target message.
     * @param msg message
     */
    public void setTargetMessage(Message msg) {
        this.camelTargetMessage = msg;
    }

}
