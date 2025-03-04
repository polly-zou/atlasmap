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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import io.atlasmap.spi.AtlasConversionInfo;
import io.atlasmap.spi.AtlasConverter;
import io.atlasmap.v2.FieldType;

/**
 * The type converter for {@link java.sql.Date}.
 */
public class SqlDateConverter implements AtlasConverter<java.sql.Date> {

    /**
     * Converts to {@link Calendar}.
     * @param date value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE_TIME_TZ)
    public Calendar toCalendar(java.sql.Date date) {
        return date != null ? GregorianCalendar.from(ZonedDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault())) : null;
    }

    /**
     * Converts to {@link Date}.
     * @param date value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE_TIME)
    public Date toDate(java.sql.Date date, String sourceFormat, String targetFormat) {
        return date != null ? DateTimeHelper.convertSqlDateToDate(date, sourceFormat) : null;
    }

    /**
     * Converts to {@link GregorianCalendar}.
     * @param date value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE_TIME_TZ)
    public GregorianCalendar toGregorianCalendar(java.sql.Date date) {
        return date != null ? GregorianCalendar.from(ZonedDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault())) : null;
    }

    /**
     * Converts to {@link LocalDate}.
     * @param date value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE)
    public LocalDate toLocalDate(java.sql.Date date) {
        return date != null ? date.toLocalDate() : null;
    }

    /**
     * Converts to {@link LocalDateTime}.
     * @param date value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE_TIME)
    public LocalDateTime toLocalDateTime(java.sql.Date date) {
        return date != null ? date.toLocalDate().atStartOfDay() : null;
    }

    /**
     * Converts to {@link java.sql.Timestamp}.
     * @param date value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE_TIME)
    public java.sql.Timestamp toSqlTimestamp(java.sql.Date date) {
        return date != null ? new java.sql.Timestamp(date.getTime()) : null;
    }

    /**
     * Converts to {@link ZonedDateTime}.
     * @param date value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE_TIME_TZ)
    public ZonedDateTime toZonedDateTime(java.sql.Date date) {
        return date != null ? ZonedDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault()) : null;
    }

}
