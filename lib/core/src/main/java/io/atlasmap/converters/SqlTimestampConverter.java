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

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import io.atlasmap.spi.AtlasConversionInfo;
import io.atlasmap.spi.AtlasConverter;
import io.atlasmap.v2.FieldType;

/**
 * The type converter for {@link Timestamp}.
 */
public class SqlTimestampConverter implements AtlasConverter<Timestamp> {

    /**
     * Converts to {@link Calendar}.
     * @param timestamp value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.DATE_TIME_TZ)
    public Calendar toCalendar(Timestamp timestamp) {
        return timestamp != null ? GregorianCalendar.from(ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault())) : null;
    }

    /**
     * Converts to {@link Date}.
     * @param timestamp value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.DATE_TIME)
    public Date toDate(Timestamp timestamp) {
        return timestamp != null ? Date.from(timestamp.toInstant()) : null;
    }

    /**
     * Converts to {@link GregorianCalendar}.
     * @param timestamp value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.DATE_TIME_TZ)
    public GregorianCalendar toGregorianCalendar(Timestamp timestamp) {
        return timestamp != null ? GregorianCalendar.from(ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault())) : null;
    }

    /**
     * Converts to {@link LocalDate}.
     * @param timestamp value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.DATE)
    public LocalDate toLocalDate(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime().toLocalDate() : null;
    }

    /**
     * Converts to {@link LocalDateTime}.
     * @param timestamp value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.DATE_TIME)
    public LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }

    /**
     * Converts to {@link LocalTime}.
     * @param timestamp value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.TIME)
    public LocalTime toLocalTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime().toLocalTime() : null;
    }

    /**
     * Converts to {@link java.sql.Date}.
     * @param timestamp value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.DATE)
    public java.sql.Date toSqlDate(Timestamp timestamp) {
        return timestamp != null ? java.sql.Date.valueOf(timestamp.toLocalDateTime().toLocalDate()) : null;
    }

    /**
     * Converts to {@link java.sql.Time}.
     * @param timestamp value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.TIME)
    public java.sql.Time toSqlTime(Timestamp timestamp) {
        return timestamp != null ? java.sql.Time.valueOf(timestamp.toLocalDateTime().toLocalTime()) : null;
    }

    /**
     * Converts to {@link ZonedDateTime}.
     * @param timestamp value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.DATE_TIME_TZ)
    public ZonedDateTime toZonedDateTime(Timestamp timestamp) {
        return timestamp != null ? ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault()) : null;
    }

}
