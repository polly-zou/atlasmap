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
package io.atlasmap.json.v2;

import java.util.ArrayList;
import java.util.List;

import io.atlasmap.v2.AtlasModelFactory;
import io.atlasmap.v2.Field;
import io.atlasmap.v2.FieldGroup;
import io.atlasmap.v2.FieldType;
import io.atlasmap.v2.Fields;

/**
 * The model factory for the JSON Document.
 */
public class AtlasJsonModelFactory {
    /** URI format. */
    public static final String URI_FORMAT = "atlas:json";

    /**
     * Creates a JSON Document.
     * @return JSON Document
     */
    public static JsonDocument createJsonDocument() {
        JsonDocument jsonDocument = new JsonDocument();
        jsonDocument.setFields(new Fields());
        return jsonDocument;
    }

    /**
     * Creates a JSON Field.
     * @return JSON Field
     */
    public static JsonField createJsonField() {
        JsonField jsonField = new JsonField();
        return jsonField;
    }

    /**
     * Creates a JSON complex field.
     * @return JSON complex field
     */
    public static JsonComplexType createJsonComlexField() {
        JsonComplexType jsonComplexField = new JsonComplexType();
        jsonComplexField.setFieldType(FieldType.COMPLEX);
        return jsonComplexField;
    }

    /**
     * Gets the string representation of {@link JsonField}.
     * @param f field
     * @return string
     */
    public static String toString(JsonField f) {
        return "JsonField [name=" + f.getName() + ", primitive=" + f.isPrimitive() + ", typeName=" + f.getTypeName()
                + ", userCreated=" + f.isUserCreated() + ", actions=" + f.getActions() + ", value=" + f.getValue()
                + ", arrayDimensions=" + f.getArrayDimensions() + ", arraySize=" + f.getArraySize()
                + ", collectionType=" + f.getCollectionType() + ", docId=" + f.getDocId() + ", index=" + f.getIndex()
                + ", path=" + f.getPath() + ", required=" + f.isRequired() + ", status=" + f.getStatus()
                + ", fieldType=" + f.getFieldType() + "]";
    }

    /**
     * Clones the JSON Field.
     * @param field field
     * @param withActions true to also clone field actions
     * @return cloned
     */
    public static JsonField cloneField(JsonField field, boolean withActions) {
        JsonField clone = new JsonField();
        copyField(field, clone, withActions);
        return clone;
    }

    /**
     * Clones the FieldGroup.
     * @param group FieldGroup
     * @return cloned
     */
    public static FieldGroup cloneFieldGroup(FieldGroup group) {
        FieldGroup clone = AtlasModelFactory.copyFieldGroup(group);
        List<Field> newChildren = new ArrayList<>();
        for (Field child : group.getField()) {
            if (child instanceof FieldGroup) {
                newChildren.add(cloneFieldGroup((FieldGroup)child));
            } else {
                newChildren.add(cloneField((JsonField)child, true));
            }
        }
        clone.getField().addAll(newChildren);
        return clone;
    }

    /**
     * Copies the Field.
     * @param from from
     * @param to to
     * @param withActions true to also copy field actions
     */
    public static void copyField(Field from, Field to, boolean withActions) {
        AtlasModelFactory.copyField(from, to, withActions);

        // json specific
        if (from instanceof JsonField && to instanceof JsonField) {
            JsonField fromJson = (JsonField)from;
            JsonField toJson = (JsonField)to;
            toJson.setName(fromJson.getName());
            toJson.setPrimitive(fromJson.isPrimitive());
            toJson.setTypeName(fromJson.getTypeName());
            toJson.setUserCreated(fromJson.isUserCreated());
        }
    }

}
