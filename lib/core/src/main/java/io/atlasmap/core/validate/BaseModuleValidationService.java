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
package io.atlasmap.core.validate;

import java.util.ArrayList;
import java.util.List;

import io.atlasmap.api.AtlasValidationService;
import io.atlasmap.core.DefaultAtlasCollectionHelper;
import io.atlasmap.core.DefaultAtlasConversionService;
import io.atlasmap.core.DefaultAtlasFieldActionService;
import io.atlasmap.spi.AtlasConversionService;
import io.atlasmap.spi.AtlasFieldActionService;
import io.atlasmap.spi.AtlasModuleDetail;
import io.atlasmap.spi.AtlasModuleMode;
import io.atlasmap.spi.FieldDirection;
import io.atlasmap.v2.AtlasMapping;
import io.atlasmap.v2.BaseMapping;
import io.atlasmap.v2.CustomMapping;
import io.atlasmap.v2.DataSource;
import io.atlasmap.v2.Field;
import io.atlasmap.v2.FieldGroup;
import io.atlasmap.v2.FieldType;
import io.atlasmap.v2.Mapping;
import io.atlasmap.v2.MappingType;
import io.atlasmap.v2.Validation;
import io.atlasmap.v2.ValidationScope;
import io.atlasmap.v2.ValidationStatus;

/**
 * The base implementation of the module validation service.
 * @param <T> type of the field specific to the module
 */
public abstract class BaseModuleValidationService<T extends Field> implements AtlasValidationService {

    private AtlasConversionService conversionService;
    private AtlasFieldActionService fieldActionService;
    private DefaultAtlasCollectionHelper collectionHelper;
    private AtlasModuleMode mode;
    private String docId;
    private MappingFieldPairValidator mappingFieldPairValidator;

    /**
     * A constructor.
     */
    public BaseModuleValidationService() {
        this.conversionService = DefaultAtlasConversionService.getInstance();
        this.fieldActionService = DefaultAtlasFieldActionService.getInstance();
        this.collectionHelper = new DefaultAtlasCollectionHelper(this.fieldActionService);
        init();
    }

    /**
     * A constructor.
     * @param conversionService conversion service
     * @param fieldActionService field action service
     */
    public BaseModuleValidationService(AtlasConversionService conversionService, AtlasFieldActionService fieldActionService) {
        this.conversionService = conversionService;
        this.fieldActionService = fieldActionService;
        this.collectionHelper = new DefaultAtlasCollectionHelper(fieldActionService);
        init();
    }

    private void init() {
        this.mappingFieldPairValidator = new MappingFieldPairValidator(this);
    }

    /**
     * Sets the module mode.
     * @param mode module mode
     */
    public void setMode(AtlasModuleMode mode) {
        this.mode = mode;
    }

    /**
     * Gets the module mode.
     * @return module mode
     */
    public AtlasModuleMode getMode() {
        return mode;
    }

    /**
     * Sets the Document ID.
     * @param docId Document ID
     */
    public void setDocId(String docId) {
        this.docId = docId;
    }

    /**
     * Gets the Document ID.
     * @return Document ID
     */
    public String getDocId() {
        return this.docId;
    }

    /**
     * Gets the {@link AtlasModuleDetail} which contains module metadata.
     * @return module detail
     */
    protected abstract AtlasModuleDetail getModuleDetail();

    @Override
    public List<Validation> validateMapping(AtlasMapping mapping) {
        List<Validation> validations = new ArrayList<>();
        if (getMode() == AtlasModuleMode.UNSET) {
            Validation validation = new Validation();
            validation.setMessage(String.format(
                    "No mode specified for %s/%s, skipping module validations",
                    this.getModuleDetail().name(), this.getClass().getSimpleName()));
        }

        if (mapping != null && mapping.getMappings() != null && mapping.getMappings().getMapping() != null
                && !mapping.getMappings().getMapping().isEmpty()) {
            validateMappingEntries(mapping.getMappings().getMapping(), validations);
        }

        boolean found = false;
        for (DataSource ds : mapping.getDataSource()) {
            if (ds.getUri() != null && ds.getUri().startsWith(getModuleDetail().uri())) {
                found = true;
                break;
            }
        }

        if (!found) {
            Validation validation = new Validation();
            validation.setScope(ValidationScope.DATA_SOURCE);
            validation.setMessage(String.format("No DataSource with '%s' uri specified", getModuleDetail().uri()));
            validation.setStatus(ValidationStatus.ERROR);
            validations.add(validation);
        }

        return validations;
    }

    /**
     * Validates mapping entries.
     * @param mappings a list of the mapping entries
     * @param validations a container to put the result validations
     */
    protected void validateMappingEntries(List<BaseMapping> mappings, List<Validation> validations) {
        for (BaseMapping fieldMapping : mappings) {
            if (fieldMapping.getClass().isAssignableFrom(CustomMapping.class)) {
                validateCustomMapping((CustomMapping)fieldMapping, validations);
            } else if (fieldMapping.getClass().isAssignableFrom(Mapping.class)
                    && MappingType.SEPARATE.equals(((Mapping) fieldMapping).getMappingType())) {
                validateSeparateMapping((Mapping) fieldMapping, validations);
            } else if (fieldMapping.getClass().isAssignableFrom(Mapping.class)
                    && MappingType.COMBINE.equals(((Mapping) fieldMapping).getMappingType())) {
                validateCombineMapping((Mapping) fieldMapping, validations);
            } else {
                if (fieldMapping instanceof io.atlasmap.v2.Collection) {
                    fieldMapping = ((io.atlasmap.v2.Collection)fieldMapping).getMappings().getMapping().get(0);
                }
                validateMapMapping((Mapping) fieldMapping, validations);
            }
        }
    }

    /**
     * Validates MAP mapping.
     * @param mapping mapping
     * @param validations a container to put the result validations
     */
    protected void validateMapMapping(Mapping mapping, List<Validation> validations) {
        if (mapping == null
                || mapping.getInputField() == null || (mapping.getInputFieldGroup() == null && mapping.getInputField().size() <= 0)
                || mapping.getOutputField() == null || mapping.getOutputField().size() <= 0) {
            return;
        }
        String mappingId = mapping.getId();

        if (getMode() == AtlasModuleMode.SOURCE) {
            FieldGroup sourceFieldGroup = mapping.getInputFieldGroup();
            if (sourceFieldGroup != null) {
                validateFieldGroup(mappingId, sourceFieldGroup, FieldDirection.SOURCE, validations);
            } else {
                List<Field> sourceFields = mapping.getInputField();
                sourceFields.forEach(sourceField -> {
                    validateField(mappingId, null, sourceField, FieldDirection.SOURCE, validations);
                });
            }
        } else if (getMode() == AtlasModuleMode.TARGET) {
            List<Field> targetFields = mapping.getOutputField();

            if (targetFields.size() == 1 && Integer.valueOf(0).equals(targetFields.get(0).getIndex())) {
                //The index should not have been set as there's only one item
                targetFields.get(0).setIndex(null);
            }

            int i  = 0;
            List<Field> sourceFields = mapping.getInputField();
            for (Field targetField: targetFields) {
                if (sourceFields.size() > i) {
                    validateField(mappingId, sourceFields.get(i), targetField, FieldDirection.TARGET, validations);
                } else {
                    validateField(mappingId, null, targetField, FieldDirection.TARGET, validations);
                }
                i++;
            }
        }

        if (getMode() == AtlasModuleMode.SOURCE) {
            validateFieldCombinations(mapping, validations);
        }
    }

    /**
     * Validates the custom mapping.
     * @param mapping custom mapping
     * @param validations a container to put the result validations
     */
    protected void validateCustomMapping(CustomMapping mapping, List<Validation> validations) {
        if (mapping.getClassName() == null || mapping.getClassName().isEmpty()) {
            Validation v = new Validation();
            v.setScope(ValidationScope.MAPPING);
            v.setMessage("Class name must be specified for custom mapping");
            v.setStatus(ValidationStatus.ERROR);
            validations.add(v);
        }
    }

    /**
     * Validates the field group.
     * @param mappingId mapping ID
     * @param fieldGroup field group
     * @param direction direction
     * @param validations a container to put the result validations
     */
    protected void validateFieldGroup(String mappingId, FieldGroup fieldGroup, FieldDirection direction, List<Validation> validations) {
        fieldGroup.getField().forEach(f -> {validateField(mappingId, null, f, direction, validations);});
    }

    /**
     * Validate the combination of source field(s) and target field(s).
     * @param mapping mapping
     * @param validations a container to put the result validations
     */
    protected void validateFieldCombinations(Mapping mapping, List<Validation> validations) {
        String mappingId = mapping.getId();
        FieldGroup sourceFieldGroup = mapping.getInputFieldGroup();
        List<Field> sourceFields = mapping.getInputField();
        List<Field> targetFields = mapping.getOutputField();
        if (sourceFieldGroup != null || (sourceFields != null && sourceFields.size() > 1)) {
            if (targetFields.size() > 1) {
                Validation validation = new Validation();
                validation.setScope(ValidationScope.MAPPING);
                validation.setId(mappingId);
                validation.setMessage("Multiple fields can not be selected on both of Source and Target");
                validation.setStatus(ValidationStatus.ERROR);
                validations.add(validation);
            }
            if (sourceFieldGroup != null) {
                mappingFieldPairValidator.validateFieldTypes(validations, mappingId, sourceFieldGroup, targetFields.get(0));
            } else {
                mappingFieldPairValidator.validateFieldTypes(validations, mappingId, sourceFields, targetFields.get(0));
            }
        } else if (targetFields != null && targetFields.size() > 1) {
            mappingFieldPairValidator.validateFieldTypes(validations, mappingId, sourceFields.get(0), targetFields);
        } else {
            mappingFieldPairValidator.validateFieldTypes(validations, mappingId, sourceFields.get(0), targetFields.get(0));
        }
    }

    /**
     * Validates the field.
     * @param mappingId mapping ID
     * @param sourceField source field
     * @param targetField target field
     * @param direction direction
     * @param validations a container to put the result validations
     */
    @SuppressWarnings("unchecked")
    protected void validateField(String mappingId, Field sourceField, Field targetField, FieldDirection direction, List<Validation> validations) {
        if (targetField == null) {
            return;
        }
        if (direction == FieldDirection.TARGET) {
            Integer sourceCollectionCount = null;
            if (sourceField != null) {
                sourceCollectionCount = collectionHelper.determineSourceCollectionCount(null, sourceField);
            }

            Integer targetCollectionCount = collectionHelper.determineTargetCollectionCount(targetField);

            if (sourceCollectionCount != null) {
                if (sourceCollectionCount > targetCollectionCount) {
                    Validation validation = new Validation();
                    validation.setScope(ValidationScope.MAPPING);
                    validation.setId(mappingId);
                    String message = String.format(
                        "Target [%s] has %s collection(s) on the path, whereas source has %s. Values from the %s rightmost " +
                            "source collections on the path will be added in depth-first order to the rightmost target " +
                            "collection(s) unless transformed explicitly.",
                        targetField.getPath(), targetCollectionCount, sourceCollectionCount,
                        sourceCollectionCount - targetCollectionCount + 1);
                    validation.setMessage(message);
                    validation.setStatus(ValidationStatus.WARN);
                    validations.add(validation);
                } else if (sourceCollectionCount < targetCollectionCount) {
                    Validation validation = new Validation();
                    validation.setScope(ValidationScope.MAPPING);
                    validation.setId(mappingId);
                    validation.setMessage(String.format("The 0 index will be used for any extra parent collections in " +
                            "target [%s], since target has %s collections on the path, whereas source has %s.",
                        targetField.getPath(), targetCollectionCount, sourceCollectionCount));
                    validation.setStatus(ValidationStatus.WARN);
                    validations.add(validation);
                }
            }

        }
        if (getFieldType().isAssignableFrom(targetField.getClass()) && matchDocIdOrNull(targetField.getDocId())) {
            validateModuleField(mappingId, (T)targetField, direction, validations);
        }
    }

    /**
     * Gets the field type.
     * @return field type class
     */
    protected abstract Class<T> getFieldType();

    /**
     * Validates module specific field.
     * @param mappingId mapping ID
     * @param field field
     * @param direction direction
     * @param validation a container to put the result validations
     */
    protected abstract void validateModuleField(String mappingId, T field, FieldDirection direction, List<Validation> validation);

    /**
     * Gets if Document ID is null or matches with the specified.
     * @param docId Document ID
     * @return true if the Document ID is null or matches with the specified.
     */
    protected boolean matchDocIdOrNull(String docId) {
        return docId == null || getDocId().equals(docId);
    }

    /**
     * Gets the field name.
     * @param field field
     * @return field name
     */
    @SuppressWarnings("unchecked")
    protected String getFieldName(Field field) {
        if (field == null) {
            return "null";
        }
        if (field.getClass().isAssignableFrom(getFieldType())) {
            return getModuleFieldName((T)field);
        }
        if (field.getFieldType() != null) {
            return field.getFieldType().name();
        }
        return field.getClass().getName();
    }

    /**
     * Gets the module specific field name.
     * @param field field
     * @return field name
     */
    protected abstract String getModuleFieldName(T field);

    /**
     * Gets the conversion service.
     * @return conversion service
     */
    protected AtlasConversionService getConversionService() {
        return conversionService;
    }

    /**
     * Gets the field action service.
     * @return field action service.
     */
    protected AtlasFieldActionService getFieldActionService() {
        return fieldActionService;
    }

    /**
     * Gets the mapping field pair validator.
     * @return mapping field pair validator.
     */
    protected MappingFieldPairValidator getMappingFieldPairValidator() {
        return mappingFieldPairValidator;
    }

    /**
     * Sets the mapping field pair validator.
     * @param mfpv mapping field pair validator
     */
    protected void setMappingFieldPairValidator(MappingFieldPairValidator mfpv) {
        mappingFieldPairValidator = mfpv;
    }

    /**
     * Sets the conversion service.
     * @param conversionService conversion service
     */
    protected void setConversionService(AtlasConversionService conversionService) {
        this.conversionService = conversionService;
    }

    /*
     * vvv Remove in 2.0 vvv
     */

    /**
     * Validates combine mapping.
     * @deprecated
     * @param mapping mapping
     * @param validations a container to put the result validations
     */
    @Deprecated
    protected void validateCombineMapping(Mapping mapping, List<Validation> validations) {
        if (mapping == null) {
            return;
        }

        List<Field> sourceFields = mapping.getInputField();

        final List<Field> targetFields = mapping.getOutputField();
        final Field targetField = (targetFields != null && !targetFields.isEmpty()) ? targetFields.get(0) : null;
        if (targetField == null) {
            return;
        }

        String mappingId = mapping.getId();

        if (getMode() == AtlasModuleMode.TARGET && matchDocIdOrNull(targetField.getDocId())) {
            if (sourceFields != null) {
                // FIXME Run only for TARGET to avoid duplicate validation...
                // we should convert per module validations to plugin style
                for (Field sourceField : sourceFields) {
                    mappingFieldPairValidator.validateFieldTypes(validations, mappingId, sourceField, targetField);
                }
            }

            // check that the output field is of type String else error
            if (targetField.getFieldType() != null && targetField.getFieldType() != FieldType.STRING) {
                Validation validation = new Validation();
                validation.setScope(ValidationScope.MAPPING);
                validation.setId(mappingId);
                validation.setMessage(String.format(
                        "Target field '%s' must be of type '%s' for a Combine Mapping",
                        getFieldName(targetField), FieldType.STRING));
                validation.setStatus(ValidationStatus.ERROR);
                validations.add(validation);
            }
            validateField(mappingId, null, targetField, FieldDirection.TARGET, validations);
        } else if (sourceFields != null) { // SOURCE
            for (Field sourceField : sourceFields) {
                if (matchDocIdOrNull(sourceField.getDocId())) {
                    validateField(mappingId, null, sourceField, FieldDirection.SOURCE, validations);
                }
            }
        }
    }

    /**
     * Validates separate mapping.
     * @deprecated
     * @param mapping mapping
     * @param validations a container to put the result validations
     */
    @Deprecated
    protected void validateSeparateMapping(Mapping mapping, List<Validation> validations) {
        if (mapping == null) {
            return;
        }

        final List<Field> sourceFields = mapping.getInputField();
        final Field sourceField = (sourceFields != null && !sourceFields.isEmpty()) ? sourceFields.get(0) : null;
        if (sourceField == null) {
            return;
        }
        List<Field> targetFields = mapping.getOutputField();
        String mappingId = mapping.getId();

        if (getMode() == AtlasModuleMode.SOURCE && matchDocIdOrNull(sourceField.getDocId())) {
            // check that the source field is of type String else error
            if (sourceField.getFieldType() != null && sourceField.getFieldType() != FieldType.STRING) {
                Validation validation = new Validation();
                validation.setScope(ValidationScope.MAPPING);
                validation.setId(mapping.getId());
                validation.setMessage(String.format(
                        "Source field '%s' must be of type '%s' for a Separate Mapping",
                        getFieldName(sourceField), FieldType.STRING));
                validation.setStatus(ValidationStatus.ERROR);
                validations.add(validation);
            }
            validateField(mappingId, null, sourceField, FieldDirection.SOURCE, validations);

            if (targetFields != null) {
                // FIXME Run only for SOURCE to avoid duplicate validation...
                // we should convert per module validations to plugin style
                for (Field targetField : targetFields) {
                    mappingFieldPairValidator.validateFieldTypes(validations, mappingId, sourceField, targetField);
                }
            }
        } else if (targetFields != null) { // TARGET
            for (Field targetField : targetFields) {
                if (matchDocIdOrNull(targetField.getDocId())) {
                    validateField(mappingId, null, targetField, FieldDirection.TARGET, validations);
                }
            }
        }
    }

}
