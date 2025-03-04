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
 * The type converter for {@link LocalTime}.
 */
public class LocalTimeConverter implements AtlasConverter<LocalTime> {

    /**
     * Converts to {@link BigDecimal}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DECIMAL)
    public BigDecimal toBigDecimal(LocalTime value) {
        return value != null ? BigDecimal.valueOf(getTodaysEpochMilli(value)) : null;
    }

    /**
     * Converts to {@link BigInteger}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.BIG_INTEGER)
    public BigInteger toBigInteger(LocalTime value) {
        return value != null ? BigInteger.valueOf(getTodaysEpochMilli(value)) : null;
    }

    /**
     * Converts to {@link Byte}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.BYTE,
            concerns = AtlasConversionConcern.RANGE)
    public Byte toByte(LocalTime value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Long longValue = getTodaysEpochMilli(value);
        if (longValue >= Byte.MIN_VALUE && longValue <= Byte.MAX_VALUE) {
            return longValue.byteValue();
        }
        throw new AtlasConversionException(
                String.format("LocalTime %s of today is greater than Byte.MAX_VALUE or less than Byte.MIN_VALUE", value));
    }

    /**
     * Converts to {@link Calendar}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DATE_TIME_TZ)
    public Calendar toCalendar(LocalTime value) {
        return value != null ? GregorianCalendar.from(value.atDate(LocalDate.now()).atZone(ZoneId.systemDefault())) : null;
    }

    /**
     * Converts to {@link Date}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DATE_TIME)
    public Date toDate(LocalTime value) {
        return value != null ? new Date(getTodaysEpochMilli(value)) : null;
    }

    /**
     * Converts to {@link Double}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DOUBLE)
    public Double toDouble(LocalTime value) {
        return value != null ? getTodaysEpochMilli(value).doubleValue() : null;
    }

    /**
     * Converts to {@link Float}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.FLOAT)
    public Float toFloat(LocalTime value) {
        return value != null ? getTodaysEpochMilli(value).floatValue() : null;
    }

    /**
     * Converts to {@link GregorianCalendar}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DATE_TIME_TZ)
    public GregorianCalendar toGregorianCalendar(LocalTime value) {
        return value != null ? GregorianCalendar.from(value.atDate(LocalDate.now()).atZone(ZoneId.systemDefault())) : null;
    }

    /**
     * Converts to {@link Integer}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.INTEGER,
            concerns = AtlasConversionConcern.RANGE)
    public Integer toInteger(LocalTime value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Long longValue = getTodaysEpochMilli(value);
        if (longValue > Integer.MAX_VALUE || longValue < Integer.MIN_VALUE) {
            throw new AtlasConversionException(String
                    .format("LocalTime nano-of-day %s is greater than Integer.MAX_VALUE or less than Integer.MIN_VALUE", longValue));
        }
        return longValue.intValue();
    }

    /**
     * Converts to {@link LocalDateTime}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DATE_TIME)
    public LocalDateTime toLocalDateTime(LocalTime value) {
        return value != null ? value.atDate(LocalDate.now()) : null;
    }

    /**
     * Converts to {@link LocalTime}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.TIME)
    public LocalTime toLocalTime(LocalTime value) {
        return value;
    }

    /**
     * Converts to {@link Long}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.LONG)
    public Long toLong(LocalTime value) {
        return value != null ? getTodaysEpochMilli(value) : null;
    }

    /**
     * Converts to {@link Short}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException out of range
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.SHORT,
            concerns = AtlasConversionConcern.RANGE)
    public Short toShort(LocalTime value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Long longValue = getTodaysEpochMilli(value);
        if (longValue > Short.MAX_VALUE || longValue < Short.MIN_VALUE) {
            throw new AtlasConversionException(
                    String.format("LocalTime nano-of-day %s is greater than Short.MAX_VALUE or less than Short.MIN_VALUE", longValue));
        }
        return longValue.shortValue();
    }

    /**
     * Converts to {@link String}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.STRING)
    public String toString(LocalTime value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    /**
     * Converts to {@link CharBuffer}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.STRING)
    public CharBuffer toCharBuffer(LocalTime value) {
        return value != null ? CharBuffer.wrap(toString(value)) : null;
    }

    /**
     * Converts to {@link CharSequence}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.STRING)
    public CharSequence toCharSequence(LocalTime value) {
        return value != null ? toString(value) : null;
    }

    /**
     * Converts to {@link StringBuffer}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.STRING)
    public StringBuffer toStringBuffer(LocalTime value) {
        return value != null ? new StringBuffer(toString(value)) : null;
    }

    /**
     * Converts to {@link StringBuilder}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.STRING)
    public StringBuilder toStringBuilder(LocalTime value) {
        return value != null ? new StringBuilder(toString(value)) : null;
    }

    /**
     * Converts to {@link Number}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.NUMBER)
    public Number toNumber(LocalTime value) {
        if (value == null) {
            return null;
        }
        return getTodaysEpochMilli(value);
    }

    /**
     * Converts to {@link java.sql.Time}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.TIME)
    public java.sql.Time toSqlTime(LocalTime value) {
        return java.sql.Time.valueOf(value);
    }

    /**
     * Converts to {@link java.sql.Timestamp}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DATE_TIME)
    public java.sql.Timestamp toSqlTimestamp(LocalTime value) {
        return value != null ? java.sql.Timestamp.valueOf(value.atDate(LocalDate.now())) : null;
    }

    /**
     * Converts to {@link ZonedDateTime}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DATE_TIME_TZ)
    public ZonedDateTime toZonedDateTime(LocalTime value) {
        return value != null ? value.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()) : null;
    }

    private Long getTodaysEpochMilli(LocalTime value) {
        return value.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
