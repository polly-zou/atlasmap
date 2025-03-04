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
import io.atlasmap.spi.AtlasConversionConcern;
import io.atlasmap.spi.AtlasConversionInfo;
import io.atlasmap.spi.AtlasConverter;
import io.atlasmap.v2.FieldType;

/**
 * The type converters for {@link BigInteger}.
 */
public class BigIntegerConverter implements AtlasConverter<BigInteger> {

    /**
     * Converts to {@link BigDecimal}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.DECIMAL)
    public BigDecimal toBigDecimal(BigInteger value) {
        return value != null ? new BigDecimal(value) : null;
    }

    /**
     * Converts to {@link BigInteger}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.BIG_INTEGER)
    public BigInteger toBigInteger(BigInteger value) {
        return value;
    }

    /**
     * Converts to {@link Boolean}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.BOOLEAN)
    public Boolean toBoolean(BigInteger value) {
        if (value == null) {
            return null;
        }
        return value.intValue() == 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    /**
     * Converts to {@link Byte}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.BYTE,
            concerns = AtlasConversionConcern.RANGE)
    public Byte toByte(BigInteger value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        try {
            return value.byteValueExact();
        } catch (ArithmeticException e) {
            throw new AtlasConversionException(String.format(
                    "BigInteger %s is greater than Byte.MAX_VALUE or less than Byte.MIN_VALUE", value));
        }
    }

    /**
     * Converts to {@link Character}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.CHAR,
            concerns = AtlasConversionConcern.RANGE)
    public Character toCharacter(BigInteger value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        try {
            Character.valueOf((char) value.intValueExact());
        } catch (ArithmeticException e) {
            throw new AtlasConversionException(String.format(
                    "BigInteger %s is greater than Character.MAX_VALUE or less than Character.MIN_VALUE", value));
        }
        return Character.valueOf((char) value.intValue());
    }

    /**
     * Converts to {@link Date}.
     * @param date value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.DATE_TIME,
            concerns = AtlasConversionConcern.RANGE)
    public Date toDate(BigInteger date) throws AtlasConversionException {
        if (date == null) {
            return null;
        }
        try {
            long dateLong = date.longValueExact();
            if (dateLong >= Instant.MIN.getEpochSecond()) {
                return Date.from(Instant.ofEpochMilli(dateLong));
            }
            return new Date(dateLong);
        } catch (ArithmeticException e) {
            throw new AtlasConversionException(String.format(
                    "BigInteger %s is greater than Long.MAX_VALUE or less than Long.MIN_VALUE", date));
        }
    }

    /**
     * Converts to {@link Double}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.DOUBLE,
            concerns = AtlasConversionConcern.RANGE)
    public Double toDouble(BigInteger value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Double answer = value.doubleValue();
        if (answer == Double.NEGATIVE_INFINITY || answer == Double.POSITIVE_INFINITY) {
            throw new AtlasConversionException(String.format(
                    "BigInteger %s is greater than Double.MAX_VALUE or less than Double.MIN_VALUE", value));
        }
        return answer;
    }

    /**
     * COnverts to {@link Float}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.FLOAT,
            concerns = AtlasConversionConcern.RANGE)
    public Float toFloat(BigInteger value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Float answer = value.floatValue();
        if (answer == Float.NEGATIVE_INFINITY || answer == Float.POSITIVE_INFINITY) {
            throw new AtlasConversionException(String.format(
                    "BigInteger %s is greater than Float.MAX_VALUE or less than Float.MIN_VALUE", value));
        }
        return answer;
    }

    /**
     * Converts to {@link Integer}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.INTEGER,
            concerns = AtlasConversionConcern.RANGE)
    public Integer toInteger(BigInteger value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        try {
            return value.intValueExact();
        } catch (ArithmeticException e) {
            throw new AtlasConversionException(String.format(
                    "BigInteger %s is greater than Integer.MAX_VALUE or less than Integer.MIN_VALUE", value));
        }
    }

    /**
     * Converts to {@link LocalDate}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.DATE,
            concerns = AtlasConversionConcern.RANGE)
    public LocalDate toLocalDate(BigInteger value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        try {
            long longValue = value.longValueExact();
            return Instant.ofEpochMilli(longValue).atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ArithmeticException e) {
            throw new AtlasConversionException(String.format(
                    "BigInteger %s is greater than Long.MAX_VALUE or less than Long.MIN_VALUE", value));
        }
    }

    /**
     * Converts to {@link LocalTime}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.TIME,
            concerns = AtlasConversionConcern.RANGE)
    public LocalTime toLocalTime(BigInteger value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        try {
            long longValue = value.longValueExact();
            return Instant.ofEpochMilli(longValue).atZone(ZoneId.systemDefault()).toLocalTime();
        } catch (ArithmeticException e) {
            throw new AtlasConversionException(String.format(
                    "BigInteger %s is greater than Long.MAX_VALUE or less than Long.MIN_VALUE", value));
        }
    }

    /**
     * Converts to {@link LocalDateTime}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.DATE_TIME,
            concerns = AtlasConversionConcern.RANGE)
    public LocalDateTime toLocalDateTime(BigInteger value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        try {
            long longValue = value.longValueExact();
            return Instant.ofEpochMilli(longValue).atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (ArithmeticException e) {
            throw new AtlasConversionException(String.format(
                    "BigInteger %s is greater than Long.MAX_VALUE or less than Long.MIN_VALUE", value));
        }
    }

    /**
     * Converts to {@link Long}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.LONG,
            concerns = AtlasConversionConcern.RANGE)
    public Long toLong(BigInteger value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        try {
            return value.longValueExact();
        } catch (ArithmeticException e) {
            throw new AtlasConversionException(String.format(
                    "BigInteger %s is greater than Long.MAX_VALUE or less than Long.MIN_VALUE", value));
        }
    }

    /**
     * Converts to {@link Number}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.NUMBER)
    public Number toNumber(BigInteger value) {
        return value;
    }

    /**
     * Converts to {@link Short}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.SHORT,
            concerns = AtlasConversionConcern.RANGE)
    public Short toShort(BigInteger value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        try {
            return value.shortValueExact();
        } catch (ArithmeticException e) {
            throw new AtlasConversionException(
                    String.format("BigInteger %s is greater than Short.MAX_VALUE or less than Short.MIN_VALUE", value));
        }
    }

    /**
     * Converts to {@link String}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.STRING)
    public String toString(BigInteger value) {
        return value != null ? value.toString() : null;
    }

    /**
     * Converts to {@link CharBuffer}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.STRING)
    public CharBuffer toCharBuffer(BigInteger value) {
        return value != null ? CharBuffer.wrap(toString(value)) : null;
    }

    /**
     * Converts to {@link CharSequence}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.STRING)
    public CharSequence toCharSequence(BigInteger value) {
        return value != null ? toString(value) : null;
    }

    /**
     * Converts to {@link StringBuffer}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.STRING)
    public StringBuffer toStringBuffer(BigInteger value) {
        return value != null ? new StringBuffer(toString(value)) : null;
    }

    /**
     * Converts to {@link StringBuilder}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.STRING)
    public StringBuilder toStringBuilder(BigInteger value) {
        return value != null ? new StringBuilder(toString(value)) : null;
    }

    /**
     * Converts to {@link ZonedDateTime}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.BIG_INTEGER, targetType = FieldType.DATE_TIME_TZ,
            concerns = AtlasConversionConcern.RANGE)
    public ZonedDateTime toZonedDateTime(BigInteger value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        try {
            long longValue = value.longValueExact();
            return Instant.ofEpochMilli(longValue).atZone(ZoneId.systemDefault());
        } catch (ArithmeticException e) {
            throw new AtlasConversionException(String.format(
                    "BigInteger %s is greater than Long.MAX_VALUE or less than Long.MIN_VALUE", value));
        }
    }

}
