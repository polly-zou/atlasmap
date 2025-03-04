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

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
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
 * The type converter for {@link Time}.
 */
public class SqlTimeConverter implements AtlasConverter<Time> {

    /**
     * Converts to {@link Calendar}.
     * @param time value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DATE_TIME_TZ)
    public Calendar toCalendar(Time time, String sourceFormat, String targetFormat) {
        return time != null ? GregorianCalendar.from(ZonedDateTime.ofInstant(Instant.ofEpochMilli(time.getTime()), ZoneId.systemDefault())) : null;
    }

    /**
     * Converts to {@link Date}.
     * @param time value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DATE_TIME)
    public Date toDate(Time time, String sourceFormat, String targetFormat) {
        return time != null ? new Date(time.getTime()) : null;
    }

    /**
     * Converts to {@link GregorianCalendar}.
     * @param time value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DATE_TIME_TZ)
    public GregorianCalendar toGregorianCalendar(Time time, String sourceFormat, String targetFormat) {
        return time != null ? GregorianCalendar.from(ZonedDateTime.ofInstant(Instant.ofEpochMilli(time.getTime()), ZoneId.systemDefault())) : null;
    }

    /**
     * Converts to {@link LocalTime}.
     * @param time value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.TIME)
    public LocalTime toLocalTime(Time time, String sourceFormat, String targetFormat) {
        return time != null ? time.toLocalTime() : null;
    }

    /**
     * Converts to {@link LocalDateTime}.
     * @param time value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DATE_TIME)
    public LocalDateTime toLocalDateTime(Time time, String sourceFormat, String targetFormat) {
        return time != null ? time.toLocalTime().atDate(LocalDate.now()) : null;
    }

    /**
     * Converts to {@link Timestamp}.
     * @param time value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DATE_TIME)
    public Timestamp toSqlTimestamp(Time time, String sourceFormat, String targetFormat) {
        return time != null ? new Timestamp(time.getTime()) : null;
    }

    /**
     * Converts to {@link ZonedDateTime}.
     * @param time value
     * @param sourceFormat source format
     * @param targetFormat target format
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DATE_TIME_TZ)
    public ZonedDateTime toZonedDateTime(Time time, String sourceFormat, String targetFormat) {
        return time != null ? ZonedDateTime.ofInstant(Instant.ofEpochMilli(time.getTime()), ZoneId.systemDefault()) : null;
    }

}
