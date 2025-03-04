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

import io.atlasmap.api.AtlasConversionException;
import io.atlasmap.spi.AtlasConversionConcern;
import io.atlasmap.spi.AtlasConversionInfo;
import io.atlasmap.spi.AtlasConverter;
import io.atlasmap.v2.FieldType;

/**
 * The type converter for {@link Character}.
 */
public class CharacterConverter implements AtlasConverter<Character> {

    private static final String TRUE_REGEX = "t|T|y|Y|[1-9]";

    /**
     * Converts to {@link BigDecimal}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.CHAR, targetType = FieldType.DECIMAL)
    public BigDecimal toBigDecimal(Character value) {
        return value != null ? BigDecimal.valueOf(value) : null;
    }

    /**
     * Converts to {@link BigInteger}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.CHAR, targetType = FieldType.BIG_INTEGER)
    public BigInteger toBigInteger(Character value) {
        return value != null ? BigInteger.valueOf(value) : null;
    }

    /**
     * Converts to {@link Boolean}.
     * @param value value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.CHAR, targetType = FieldType.BOOLEAN, concerns = {
            AtlasConversionConcern.CONVENTION })
    public Boolean toBoolean(Character value, String sourceFormat, String targetFormat) {
        if (value == null) {
            return null;
        }

        String regex = sourceFormat != null && !"".equals(sourceFormat) ? sourceFormat : TRUE_REGEX;
        if (Character.toString(value).matches(regex)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * Converts to {@link Byte}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.CHAR, targetType = FieldType.BYTE, concerns = {
            AtlasConversionConcern.RANGE })
    public Byte toByte(Character value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        if (value.charValue() > Byte.MAX_VALUE) {
            throw new AtlasConversionException(
                    String.format("Character value %s is greater than BYTE.MAX_VALUE", value));
        }
        return (byte) value.charValue();
    }

    /**
     * Converts to {@link Character}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.CHAR, targetType = FieldType.CHAR)
    public Character toCharacter(Character value) {
        if (value == null) {
            return null;
        }
        // we want new Character from the value
        return new Character(value);
    }

    /**
     * Converts to {@link Double}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.CHAR, targetType = FieldType.DOUBLE)
    public Double toDouble(Character value) {
        if (value == null) {
            return null;
        }
        return Double.valueOf(value);
    }

    /**
     * Converts to {@link Float}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.CHAR, targetType = FieldType.FLOAT)
    public Float toFloat(Character value) {
        if (value == null) {
            return null;
        }
        return Float.valueOf(value);
    }

    /**
     * Converts to {@link Integer}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.CHAR, targetType = FieldType.INTEGER)
    public Integer toInteger(Character value) {
        if (value == null) {
            return null;
        }
        return Integer.valueOf(value);
    }

    /**
     * Converts to {@link Long}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.CHAR, targetType = FieldType.LONG)
    public Long toLong(Character value) {
        if (value == null) {
            return null;
        }
        return Long.valueOf(value);
    }

    /**
     * Converts to {@link Number}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.CHAR, targetType = FieldType.NUMBER)
    public Number toNumber(Character value) {
        if (value == null) {
            return null;
        }
        return Integer.valueOf(value);
    }

    /**
     * Converts to {@link Short}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.CHAR, targetType = FieldType.SHORT, concerns = {
            AtlasConversionConcern.RANGE, AtlasConversionConcern.CONVENTION })
    public Short toShort(Character value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        // only care if the char is larger than the short MAX
        if (value > Short.MAX_VALUE) {
            throw new AtlasConversionException();
        }
        return (short) value.charValue();
    }

    /**
     * Converts to {@link String}.
     * @param value value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.CHAR, targetType = FieldType.STRING)
    public String toString(Character value, String sourceFormat, String targetFormat) {
        if (value == null) {
            return null;
        }
        return String.valueOf(value);
    }

    /**
     * Converts to {@link CharBuffer}.
     * @param value value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.CHAR, targetType = FieldType.STRING)
    public CharBuffer toCharBuffer(Character value, String sourceFormat, String targetFormat) {
        return value != null ? CharBuffer.wrap(toString(value, sourceFormat, targetFormat)) : null;
    }

    /**
     * Converts to {@link CharSequence}.
     * @param value value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.CHAR, targetType = FieldType.STRING)
    public CharSequence toCharSequence(Character value, String sourceFormat, String targetFormat) {
        return value != null ? toString(value, sourceFormat, targetFormat) : null;
    }

    /**
     * Converts to {@link StringBuffer}.
     * @param value value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.CHAR, targetType = FieldType.STRING)
    public StringBuffer toStringBuffer(Character value, String sourceFormat, String targetFormat) {
        return value != null ? new StringBuffer(toString(value, sourceFormat, targetFormat)) : null;
    }

    /**
     * Converts to {@link StringBuilder}.
     * @param value value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.CHAR, targetType = FieldType.STRING)
    public StringBuilder toStringBuilder(Character value, String sourceFormat, String targetFormat) {
        return value != null ? new StringBuilder(toString(value, sourceFormat, targetFormat)) : null;
    }

}
