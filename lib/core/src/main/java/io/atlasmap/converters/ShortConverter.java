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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import io.atlasmap.api.AtlasConversionException;
import io.atlasmap.api.AtlasUnsupportedException;
import io.atlasmap.spi.AtlasConversionConcern;
import io.atlasmap.spi.AtlasConversionInfo;
import io.atlasmap.spi.AtlasConverter;
import io.atlasmap.v2.FieldType;

/**
 * The type converter for {@link Short}.
 */
public class ShortConverter implements AtlasConverter<Short> {

    /**
     * Converts to {@link BigDecimal}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.DECIMAL)
    public BigDecimal toBigDecimal(Short value) {
        return value != null ? BigDecimal.valueOf(value) : null;
    }

    /**
     * Converts to {@link BigInteger}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.BIG_INTEGER)
    public BigInteger toBigInteger(Short value) {
        return value != null ? BigInteger.valueOf(value) : null;
    }

    /**
     * Converts to {@link Boolean}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.BOOLEAN, concerns = AtlasConversionConcern.CONVENTION)
    public Boolean toBoolean(Short value) {
        if (value == null) {
            return null;
        }
        return value == 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    /**
     * Converts to {@link Byte}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.BYTE, concerns = AtlasConversionConcern.RANGE)
    public Byte toByte(Short value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
            return value.byteValue();
        }
        throw new AtlasConversionException(new AtlasUnsupportedException(
                String.format("Short %s is greater than Byte.MAX_VALUE or less than Byte.MIN_VALUE", value)));
    }

    /**
     * Converts to {@link Character}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.CHAR, concerns = {
            AtlasConversionConcern.RANGE, AtlasConversionConcern.CONVENTION })
    public Character toCharacter(Short value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }

        if (value < Character.MIN_VALUE || value > Character.MAX_VALUE) {
            throw new AtlasConversionException(String
                    .format("Short %s is greater than Character.MAX_VALUE  or less than Character.MIN_VALUE", value));
        }
        return Character.valueOf((char) value.intValue());
    }

    /**
     * Converts to {@link Date}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.DATE_TIME)
    public Date toDate(Short value) {
        if (value >= Instant.MIN.getEpochSecond()) {
            return Date.from(Instant.ofEpochMilli(value));
        }
        return new Date(value);
    }

    /**
     * Converts to {@link Double}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.DOUBLE)
    public Double toDouble(Short value) {
        if (value == null) {
            return null;
        }
        return value.doubleValue();
    }

    /**
     * Converts to {@link Float}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.FLOAT)
    public Float toFloat(Short value) {
        if (value == null) {
            return null;
        }
        return value.floatValue();
    }

    /**
     * Converts to {@link Integer}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.INTEGER)
    public Integer toInteger(Short value) {
        if (value == null) {
            return null;
        }
        return value.intValue();
    }

    /**
     * Converts to {@link LocalDate}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.DATE)
    public LocalDate toLocalDate(Short value) {
        return value != null ? Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }

    /**
     * Converts to {@link LocalTime}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.TIME)
    public LocalTime toLocalTime(Short value) {
        return value != null ? Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalTime() : null;
    }

    /**
     * Converts to {@link LocalDateTime}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.DATE_TIME)
    public LocalDateTime toLocalDateTime(Short value) {
        return value != null ? Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
    }

    /**
     * Converts to {@link Long}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.LONG)
    public Long toLong(Short value) {
        return value != null ? value.longValue() : null;
    }

    /**
     * Converts to {@link Number}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.NUMBER)
    public Number toNumber(Short value) {
        return value;
    }

    /**
     * Converts to {@link Short}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.SHORT)
    public Short toShort(Short value) {
        return value != null ? new Short(value) : null;
    }

    /**
     * Converts to {@link String}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.STRING)
    public String toString(Short value) {
        return value != null ? String.valueOf(value) : null;
    }

    /**
     * Converts to {@link CharBuffer}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.STRING)
    public CharBuffer toCharBuffer(Short value) {
        return value != null ? CharBuffer.wrap(toString(value)) : null;
    }

    /**
     * Converts to {@link CharSequence}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.STRING)
    public CharSequence toCharSequence(Short value) {
        return value != null ? toString(value) : null;
    }

    /**
     * Converts to {@link StringBuffer}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.STRING)
    public StringBuffer toStringBuffer(Short value) {
        return value != null ? new StringBuffer(toString(value)) : null;
    }

    /**
     * Converts to {@link StringBuilder}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.STRING)
    public StringBuilder toStringBuilder(Short value) {
        return value != null ? new StringBuilder(toString(value)) : null;
    }

    /**
     * Converts to {@link ZonedDateTime}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.SHORT, targetType = FieldType.DATE_TIME_TZ)
    public ZonedDateTime toZonedDateTime(Short value) {
        return value != null ? Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()) : null;
    }

}
