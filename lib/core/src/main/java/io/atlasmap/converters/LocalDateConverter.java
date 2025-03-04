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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import io.atlasmap.api.AtlasConversionException;
import io.atlasmap.spi.AtlasConversionConcern;
import io.atlasmap.spi.AtlasConversionInfo;
import io.atlasmap.spi.AtlasConverter;
import io.atlasmap.v2.FieldType;

/**
 * The type converter for {@link LocalDate}.
 */
public class LocalDateConverter implements AtlasConverter<LocalDate> {

    /**
     * Converts to {@link BigDecimal}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DECIMAL)
    public BigDecimal toBigDecimal(LocalDate value) {
        return value != null ? BigDecimal.valueOf(getStartEpochMilli(value)) : null;
    }

    /**
     * Converts to {@link BigInteger}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.BIG_INTEGER)
    public BigInteger toBigInteger(LocalDate value) {
        return value != null ? BigInteger.valueOf(getStartEpochMilli(value)) : null;
    }

    /**
     * Converts to {@link Byte}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.BYTE,
            concerns = AtlasConversionConcern.RANGE)
    public Byte toByte(LocalDate value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Long longValue = getStartEpochMilli(value);
        if (longValue >= Byte.MIN_VALUE && longValue <= Byte.MAX_VALUE) {
            return longValue.byteValue();
        }
        throw new AtlasConversionException(
                String.format("LocalDate %s is greater than Byte.MAX_VALUE or less than Byte.MIN_VALUE", value));
    }

    /**
     * Converts to {@link Calendar}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE_TIME_TZ)
    public Calendar toCalendar(LocalDate value) {
        return value != null ? GregorianCalendar.from(value.atStartOfDay(ZoneId.systemDefault())) : null;
    }

    /**
     * Converts to {@link Date}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE_TIME)
    public Date toDate(LocalDate value) {
        return value != null ? new Date(getStartEpochMilli(value)) : null;
    }

    /**
     * Converts to {@link Double}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DOUBLE)
    public Double toDouble(LocalDate value) {
        return value != null ? getStartEpochMilli(value).doubleValue() : null;
    }

    /**
     * Converts to {@link Float}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.FLOAT)
    public Float toFloat(LocalDate value) {
        return value != null ? getStartEpochMilli(value).floatValue() : null;
    }

    /**
     * Converts to {@link GregorianCalendar}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE_TIME_TZ)
    public GregorianCalendar toGregorianCalendar(LocalDate value) {
        return value != null ? GregorianCalendar.from(value.atStartOfDay(ZoneId.systemDefault())) : null;
    }

    /**
     * Converts to {@link Integer}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.INTEGER,
            concerns = AtlasConversionConcern.RANGE)
    public Integer toInteger(LocalDate value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Long longValue = getStartEpochMilli(value);
        if (longValue > Integer.MAX_VALUE || longValue < Integer.MIN_VALUE) {
            throw new AtlasConversionException(String
                    .format("LocalDate %s is greater than Integer.MAX_VALUE or less than Integer.MIN_VALUE", value));
        }
        return longValue.intValue();
    }

    /**
     * Converts to {@link LocalDate}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE)
    public LocalDate toLocalDate(LocalDate value) {
        return value;
    }

    /**
     * Converts to {@link LocalDateTime}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE_TIME)
    public LocalDateTime toLocalDateTime(LocalDate value) {
        return value != null ? value.atStartOfDay() : null;
    }

    /**
     * Converts to {@link LocalTime}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.TIME)
    public LocalTime toLocalTime(LocalDate value) {
        return value != null ? value.atStartOfDay().toLocalTime() : null;
    }

    /**
     * Converts to {@link Long}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.LONG)
    public Long toLong(LocalDate value) {
        return value != null ? getStartEpochMilli(value) : null;
    }

    /**
     * Converts to {@link Short}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.SHORT,
            concerns = AtlasConversionConcern.RANGE)
    public Short toShort(LocalDate value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Long longValue = getStartEpochMilli(value);
        if (longValue > Short.MAX_VALUE || longValue < Short.MIN_VALUE) {
            throw new AtlasConversionException(
                    String.format("LocalDate %s is greater than Short.MAX_VALUE or less than Short.MIN_VALUE", value));
        }
        return longValue.shortValue();
    }

    /**
     * Converts to {@link String}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.STRING)
    public String toString(LocalDate value) {
        return value != null ? value.toString() : null;
    }

    /**
     * Converts to {@link CharBuffer}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.STRING)
    public CharBuffer toCharBuffer(LocalDate value) {
        return value != null ? CharBuffer.wrap(toString(value)) : null;
    }

    /**
     * Converts to {@link CharSequence}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.STRING)
    public CharSequence toCharSequence(LocalDate value) {
        return value != null ? toString(value) : null;
    }

    /**
     * Converts to {@link StringBuffer}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.STRING)
    public StringBuffer toStringBuffer(LocalDate value) {
        return value != null ? new StringBuffer(toString(value)) : null;
    }

    /**
     * Converts to {@link StringBuilder}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.STRING)
    public StringBuilder toStringBuilder(LocalDate value) {
        return value != null ? new StringBuilder(toString(value)) : null;
    }

    /**
     * Converts to {@link Number}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.NUMBER)
    public Number toNumber(LocalDate value) {
        return value != null ? getStartEpochMilli(value) : null;
    }

    /**
     * Converts to {@link java.sql.Date}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE)
    public java.sql.Date toSqlDate(LocalDate value) {
        return java.sql.Date.valueOf(value);
    }

    /**
     * Converts to {@link java.sql.Timestamp}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE_TIME)
    public java.sql.Timestamp toSqlTimestamp(LocalDate value) {
        return value != null ? java.sql.Timestamp.valueOf(value.atStartOfDay()) : null;
    }

    /**
     * Converts to {@link ZonedDateTime}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE_TIME_TZ)
    public ZonedDateTime toZonedDateTime(LocalDate value) {
        return value != null ? value.atStartOfDay(ZoneId.systemDefault()) : null;
    }

    private Long getStartEpochMilli(LocalDate value) {
        return value != null ? value.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() : null;
    }
}
