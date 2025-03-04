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
 * The type converter for {@link ZonedDateTime}.
 */
public class ZonedDateTimeConverter implements AtlasConverter<ZonedDateTime> {

    /**
     * Converts to {@link BigDecimal}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.DECIMAL,
            concerns = AtlasConversionConcern.TIMEZONE)
    public BigDecimal toBigDecimal(ZonedDateTime value) {
        return value != null ? BigDecimal.valueOf(getEpochMilli(value)) : null;
    }

    /**
     * Converts to {@link BigInteger}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.BIG_INTEGER,
            concerns = AtlasConversionConcern.TIMEZONE)
    public BigInteger toBigInteger(ZonedDateTime value) {
        return value != null ? BigInteger.valueOf(getEpochMilli(value)) : null;
    }

    /**
     * Converts to {@link Byte}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.BYTE,
            concerns = {AtlasConversionConcern.RANGE, AtlasConversionConcern.TIMEZONE})
    public Byte toByte(ZonedDateTime value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Long longValue = getEpochMilli(value);
        if (longValue >= Byte.MIN_VALUE && longValue <= Byte.MAX_VALUE) {
            return longValue.byteValue();
        }
        throw new AtlasConversionException(
                String.format("ZonedDateTime %s is greater than Byte.MAX_VALUE or less than Byte.MIN_VALUE", value));
    }

    /**
     * Converts to {@link Calendar}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.DATE_TIME_TZ)
    public Calendar toCalendar(ZonedDateTime value) {
        return value != null ? GregorianCalendar.from(value) : null;
    }

    /**
     * Converts to {@link Date}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.DATE_TIME,
            concerns = AtlasConversionConcern.TIMEZONE)
    public Date toDate(ZonedDateTime value) {
        return value != null ? Date.from(value.toInstant()) : null;
    }

    /**
     * Converts to {@link Double}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.DOUBLE,
            concerns = AtlasConversionConcern.TIMEZONE)
    public Double toDouble(ZonedDateTime value) {
        return value != null ? getEpochMilli(value).doubleValue() : null;
    }

    /**
     * Converts to {@link Float}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.FLOAT,
            concerns = AtlasConversionConcern.TIMEZONE)
    public Float toFloat(ZonedDateTime value) {
        return value != null ? getEpochMilli(value).floatValue() : null;
    }

    /**
     * Converts to {@link GregorianCalendar}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.DATE_TIME_TZ)
    public GregorianCalendar toGregorianCalendar(ZonedDateTime value) {
        return value != null ? GregorianCalendar.from(value) : null;
    }

    /**
     * Converts to {@link Integer}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.INTEGER,
            concerns = {AtlasConversionConcern.RANGE, AtlasConversionConcern.TIMEZONE})
    public Integer toInteger(ZonedDateTime value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Long longValue = getEpochMilli(value);
        if (longValue > Integer.MAX_VALUE || longValue < Integer.MIN_VALUE) {
            throw new AtlasConversionException(String
                    .format("ZonedDateTime %s is greater than Integer.MAX_VALUE or less than Integer.MIN_VALUE", value));
        }
        return longValue.intValue();
    }

    /**
     * Converts to {@link LocalDate}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.DATE)
    public LocalDate toLocalDate(ZonedDateTime value) {
        return value != null ? value.toLocalDate() : null;
    }

    /**
     * Converts to {@link LocalDateTime}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.DATE_TIME)
    public LocalDateTime toLocalDateTime(ZonedDateTime value) {
        return value != null ? value.toLocalDateTime() : null;
    }

    /**
     * Converts to {@link LocalTime}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.TIME)
    public LocalTime toLocalTime(ZonedDateTime value) {
        return value != null ? value.toLocalTime() : null;
    }

    /**
     * Converts to {@link Long}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.LONG,
            concerns = AtlasConversionConcern.TIMEZONE)
    public Long toLong(ZonedDateTime value) {
        return value != null ? getEpochMilli(value) : null;
    }

    /**
     * Converts to {@link Short}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.SHORT,
            concerns = {AtlasConversionConcern.RANGE, AtlasConversionConcern.TIMEZONE})
    public Short toShort(ZonedDateTime value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Long longValue = getEpochMilli(value);
        if (longValue > Short.MAX_VALUE || longValue < Short.MIN_VALUE) {
            throw new AtlasConversionException(
                    String.format("LocalDateTime %s is greater than Short.MAX_VALUE or less than Short.MIN_VALUE", value));
        }
        return longValue.shortValue();
    }

    /**
     * Converts to {@link String}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.STRING)
    public String toString(ZonedDateTime value) {
        return value != null ? value.toString() : null;
    }

    /**
     * Converts to {@link CharBuffer}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.STRING)
    public CharBuffer toCharBuffer(ZonedDateTime value) {
        return value != null ? CharBuffer.wrap(toString(value)) : null;
    }

    /**
     * Converts to {@link CharSequence}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.STRING)
    public CharSequence toCharSequence(ZonedDateTime value) {
        return value != null ? toString(value) : null;
    }

    /**
     * Converts to {@link StringBuffer}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.STRING)
    public StringBuffer toStringBuffer(ZonedDateTime value) {
        return value != null ? new StringBuffer(toString(value)) : null;
    }

    /**
     * Converts to {@link StringBuilder}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.STRING)
    public StringBuilder toStringBuilder(ZonedDateTime value) {
        return value != null ? new StringBuilder(toString(value)) : null;
    }

    /**
     * Converts to {@link Number}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.NUMBER,
            concerns = AtlasConversionConcern.TIMEZONE)
    public Number toNumber(ZonedDateTime value) {
        return value != null ? getEpochMilli(value) : null;
    }

    /**
     * Converts to {@link java.sql.Date}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.DATE)
    public java.sql.Date toSqlDate(ZonedDateTime value) {
        return value != null ? java.sql.Date.valueOf(value.toLocalDate()) : null;
    }

    /**
     * Converts to {@link java.sql.Time}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.TIME)
    public java.sql.Time toSqlTime(ZonedDateTime value) {
        return value != null ? java.sql.Time.valueOf(value.toLocalTime()) : null;
    }

    /**
     * Converts to {@link java.sql.Timestamp}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.DATE_TIME)
    public java.sql.Timestamp toSqlTimestamp(ZonedDateTime value) {
        return value != null ? java.sql.Timestamp.valueOf(value.toLocalDateTime()) : null;
    }

    /**
     * Converts to {@link ZonedDateTime}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME_TZ, targetType = FieldType.DATE_TIME_TZ)
    public ZonedDateTime toZonedDateTime(ZonedDateTime value) {
        return value;
    }

    private Long getEpochMilli(ZonedDateTime value) {
        return value.toInstant().toEpochMilli();
    }

}
