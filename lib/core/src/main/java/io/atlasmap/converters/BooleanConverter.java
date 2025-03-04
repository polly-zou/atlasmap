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
package io.atlasmap.converters;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.CharBuffer;

import io.atlasmap.spi.AtlasConversionConcern;
import io.atlasmap.spi.AtlasConversionInfo;
import io.atlasmap.spi.AtlasConverter;
import io.atlasmap.v2.FieldType;

/**
 * The type converters for {@link Boolean}.
 */
public class BooleanConverter implements AtlasConverter<Boolean> {

    private static final String STRING_VALUES = "true|false";

    /**
     * Converts to {@link BigDecimal}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BOOLEAN, targetType = FieldType.DECIMAL)
    public BigDecimal toBigDecimal(Boolean value) {
        return value != null ? BigDecimal.valueOf(value ? 1 : 0) : null;
    }

    /**
     * Converts to {@link BigInteger}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BOOLEAN, targetType = FieldType.BIG_INTEGER)
    public BigInteger toBigInteger(Boolean value) {
        return value != null ? BigInteger.valueOf(value ? 1 : 0) : null;
    }

    /**
     * Converts to {@link Boolean}.
     * @param value value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BOOLEAN, targetType = FieldType.BOOLEAN)
    public Boolean toBoolean(Boolean value, String sourceFormat, String targetFormat) {
        return value != null ? Boolean.valueOf(value) : null;
    }

    /**
     * Converts to {@link Byte}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BOOLEAN, targetType = FieldType.BYTE)
    public Byte toByte(Boolean value) {
        return value != null ? (byte) (value ? 1 : 0) : null;
    }

    /**
     * Converts to {@link Character}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BOOLEAN, targetType = FieldType.CHAR)
    public Character toCharacter(Boolean value) {
        return value != null ? (char) (value ? 1 : 0) : null;
    }

    /**
     * Converts to {@link Double}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BOOLEAN, targetType = FieldType.DOUBLE)
    public Double toDouble(Boolean value) {
        return value != null ? value ? 1.0d : 0.0d : null;
    }

    /**
     * Converts to {@link Float}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BOOLEAN, targetType = FieldType.FLOAT)
    public Float toFloat(Boolean value) {
        return value != null ? (value ? 1.0f : 0.0f) : null;
    }

    /**
     * Converts to {@link Integer}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BOOLEAN, targetType = FieldType.INTEGER)
    public Integer toInteger(Boolean value) {
        return value != null ? (value ? 1 : 0) : null;
    }

    /**
     * Converts to {@link Long}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BOOLEAN, targetType = FieldType.LONG)
    public Long toLong(Boolean value) {
        return value != null ? (value ? 1L : 0L) : null;
    }

    /**
     * Converts to {@link Number}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BOOLEAN, targetType = FieldType.NUMBER)
    public Number toNumber(Boolean value) {
        return toShort(value);
    }

    /**
     * Converts to {@link Short}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BOOLEAN, targetType = FieldType.SHORT)
    public Short toShort(Boolean value) {
        return value != null ? (short) (value ? 1 : 0) : null;
    }

    /**
     * Converts to {@link String}.
     * @param value value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BOOLEAN, targetType = FieldType.STRING, concerns = {
            AtlasConversionConcern.CONVENTION })
    public String toString(Boolean value, String sourceFormat, String targetFormat) {
        if (value == null) {
            return null;
        }
        // TODO optimize/save defaults
        String format = targetFormat != null && !"".equals(targetFormat) ? targetFormat : STRING_VALUES;
        String[] values = format.split("\\|");
        String trueValue = "";
        String falseValue = "";
        if (values.length == 2) {
            trueValue = values[0];
            falseValue = values[1];
        } else if (values.length == 1) {
            trueValue = values[0];
        }
        return String.valueOf((value ? trueValue : falseValue));
    }

    /**
     * Converts to {@link CharBuffer}.
     * @param value value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BOOLEAN, targetType = FieldType.STRING)
    public CharBuffer toCharBuffer(Boolean value, String sourceFormat, String targetFormat) {
        return value != null ? CharBuffer.wrap(toString(value, sourceFormat, targetFormat)) : null;
    }

    /**
     * Converts to {@link CharSequence}.
     * @param value value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BOOLEAN, targetType = FieldType.STRING)
    public CharSequence toCharSequence(Boolean value, String sourceFormat, String targetFormat) {
        return value != null ? toString(value, sourceFormat, targetFormat) : null;
    }

    /**
     * Converts to {@link StringBuffer}.
     * @param value value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BOOLEAN, targetType = FieldType.STRING)
    public StringBuffer toStringBuffer(Boolean value, String sourceFormat, String targetFormat) {
        return value != null ? new StringBuffer(toString(value, sourceFormat, targetFormat)) : null;
    }

    /**
     * Converts to {@link StringBuilder}.
     * @param value value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BOOLEAN, targetType = FieldType.STRING)
    public StringBuilder toStringBuilder(Boolean value, String sourceFormat, String targetFormat) {
        return value != null ? new StringBuilder(toString(value, sourceFormat, targetFormat)) : null;
    }

}
