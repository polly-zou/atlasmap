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
 * The type converter for {@link Double}.
 */
public class DoubleConverter implements AtlasConverter<Double> {

    /**
     * Converts to {@link BigDecimal}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.DECIMAL)
    public BigDecimal toBigDecimal(Double value) {
        return value != null ? BigDecimal.valueOf(value) : null;
    }

    /**
     * Converts to {@link BigInteger}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.BIG_INTEGER,
            concerns = AtlasConversionConcern.FRACTIONAL_PART)
    public BigInteger toBigInteger(Double value) {
        return value != null ? BigDecimal.valueOf(value).toBigInteger() : null;
    }

    /**
     * Converts to {@link Boolean}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.BOOLEAN, concerns = {
            AtlasConversionConcern.CONVENTION })
    public Boolean toBoolean(Double value) {
        if (value == null) {
            return null;
        }
        return value == 0.0 ? Boolean.FALSE : Boolean.TRUE;
    }

    /**
     * Converts to {@link Byte}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.BYTE,
            concerns = {AtlasConversionConcern.RANGE, AtlasConversionConcern.FRACTIONAL_PART})
    public Byte toByte(Double value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        if (value % 1 == 0 && value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
            return value.byteValue();
        }
        throw new AtlasConversionException(new AtlasUnsupportedException(String.format(
                "Double %s is greater than Byte.MAX_VALUE or less than Byte.MIN_VALUE or is not a whole number",
                value)));
    }

    /**
     * Converts to {@link Character}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.CHAR,
            concerns = {AtlasConversionConcern.RANGE, AtlasConversionConcern.FRACTIONAL_PART})
    public Character toCharacter(Double value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }

        if (value < Character.MIN_VALUE || value > Character.MAX_VALUE) {
            throw new AtlasConversionException(String
                    .format("Double %s is greater than Character.MAX_VALUE or less than Character.MIN_VALUE", value));
        }
        return Character.valueOf((char) value.intValue());
    }

    /**
     * Converts to {@link Date}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.DATE,
            concerns = {AtlasConversionConcern.RANGE, AtlasConversionConcern.FRACTIONAL_PART})
    public Date toDate(Double value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        if (value < Long.MIN_VALUE || value > Long.MAX_VALUE) {
            throw new AtlasConversionException(String
                    .format("Double %s is greater than Long.MAX_VALUE or less than Long.MIN_VALUE", value));
        }
        return new Date(value.longValue());
    }

    /**
     * Converts to {@link Double}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.DOUBLE)
    public Double toDouble(Double value) {
        return value != null ? Double.valueOf(value) : null;
    }

    /**
     * Converts to {@link Float}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.FLOAT,
            concerns = AtlasConversionConcern.RANGE)
    public Float toFloat(Double value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        double absValue = Math.abs(value);
        if (absValue > Float.MAX_VALUE || (absValue < Float.MIN_VALUE && value != 0)) {
            throw new AtlasConversionException(
                    String.format("Double %s is greater than Float.MAX_VALUE or less than Float.MIN_VALUE", value));
        }
        return value.floatValue();
    }

    /**
     * Converts to {@link Integer}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.INTEGER,
            concerns = {AtlasConversionConcern.RANGE, AtlasConversionConcern.FRACTIONAL_PART})
    public Integer toInteger(Double value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
            throw new AtlasConversionException(String.format(
                    "Double %s is greater than Integer.MAX_VALUE or less than Integer.MIN_VALUE", value));
        }
        return value.intValue();
    }

    /**
     * Converts to {@link LocalDate}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.DATE,
            concerns = {AtlasConversionConcern.RANGE, AtlasConversionConcern.FRACTIONAL_PART})
    public LocalDate toLocalDate(Double value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        if (value < Long.MIN_VALUE || value > Long.MAX_VALUE) {
            throw new AtlasConversionException(String.format(
                    "Double %s is greater than Long.MAX_VALUE or less than Long.MIN_VALUE", value));
        }
        return Instant.ofEpochMilli(value.longValue()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Converts to {@link LocalTime}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.TIME,
            concerns = {AtlasConversionConcern.RANGE, AtlasConversionConcern.FRACTIONAL_PART})
    public LocalTime toLocalTime(Double value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        if (value < Long.MIN_VALUE || value > Long.MAX_VALUE) {
            throw new AtlasConversionException(String.format(
                    "Double %s is greater than Long.MAX_VALUE or less than Long.MIN_VALUE", value));
        }
        return Instant.ofEpochMilli(value.longValue()).atZone(ZoneId.systemDefault()).toLocalTime();
    }

    /**
     * Converts to {@link LocalDateTime}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.DATE_TIME,
            concerns = {AtlasConversionConcern.RANGE, AtlasConversionConcern.FRACTIONAL_PART})
    public LocalDateTime toLocalDateTime(Double value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        if (value < Long.MIN_VALUE || value > Long.MAX_VALUE) {
            throw new AtlasConversionException(String.format(
                    "Double %s is greater than Long.MAX_VALUE or less than Long.MIN_VALUE", value));
        }
        return Instant.ofEpochMilli(value.longValue()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Converts to {@link Long}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.LONG, concerns = AtlasConversionConcern.RANGE)
    public Long toLong(Double value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        if (value < Long.MIN_VALUE || value > Long.MAX_VALUE) {
            throw new AtlasConversionException(String.format(
                    "Double %s is greater than Long.MAX_VALUE or less than Long.MIN_VALUE", value));
        }
        return value.longValue();
    }

    /**
     * Converts to {@link Number}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.NUMBER)
    public Number toNumber(Double value) {
        return value;
    }

    /**
     * Converts to {@link Short}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.SHORT, concerns = AtlasConversionConcern.RANGE)
    public Short toShort(Double value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        if (value > Short.MAX_VALUE || value < Short.MIN_VALUE) {
            throw new AtlasConversionException(
                    String.format("Double %s is greater than Short.MAX_VALUE or less than Short.MIN_VALUE", value));
        }
        return value.shortValue();
    }

    /**
     * Converts to {@link String}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.STRING)
    public String toString(Double value) {
        return value != null ? String.valueOf(value) : null;
    }

    /**
     * Converts to {@link CharBuffer}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.STRING)
    public CharBuffer toCharBuffer(Double value) {
        return value != null ? CharBuffer.wrap(toString(value)) : null;
    }

    /**
     * Converts to {@link CharSequence}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.STRING)
    public CharSequence toCharSequence(Double value) {
        return value != null ? toString(value) : null;
    }

    /**
     * Converts to {@link StringBuffer}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.STRING)
    public StringBuffer toStringBuffer(Double value) {
        return value != null ? new StringBuffer(toString(value)) : null;
    }

    /**
     * Converts to {@link StringBuilder}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.STRING)
    public StringBuilder toStringBuilder(Double value) {
        return value != null ? new StringBuilder(toString(value)) : null;
    }

    /**
     * Converts to {@link ZonedDateTime}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.DOUBLE, targetType = FieldType.DATE_TIME_TZ,
            concerns = {AtlasConversionConcern.RANGE, AtlasConversionConcern.FRACTIONAL_PART})
    public ZonedDateTime toZonedDateTime(Double value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        if (value > Long.MAX_VALUE || value < Long.MIN_VALUE) {
            throw new AtlasConversionException(String.format(
                    "Double %s is greater than Long.MAX_VALUE or less than Long.MIN_VALUE", value));
        }
        return Instant.ofEpochMilli(value.longValue()).atZone(ZoneId.systemDefault());
    }

}
