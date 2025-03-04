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

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.CharBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;

import io.atlasmap.api.AtlasConversionException;
import io.atlasmap.spi.AtlasConversionInfo;
import io.atlasmap.spi.AtlasConverter;
import io.atlasmap.v2.FieldType;

/**
 * The type converter for {@link Number}.
 */
public class NumberConverter implements AtlasConverter<Number> {
    private BigDecimalConverter bigDecimalConverter = new BigDecimalConverter();
    private BigIntegerConverter bigIntegerConverter = new BigIntegerConverter();
    private ByteConverter byteConverter = new ByteConverter();
    private DoubleConverter doubleConverter = new DoubleConverter();
    private FloatConverter floatConverter = new FloatConverter();
    private IntegerConverter integerConverter = new IntegerConverter();
    private LongConverter longConverter = new LongConverter();
    private ShortConverter shortConverter = new ShortConverter();

    /**
     * Converts to {@link BigDecimal}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.DECIMAL)
    public BigDecimal toBigDecimal(Number value) throws AtlasConversionException {
        return invoke(value, BigDecimal.class);
    }

    /**
     * Converts to {@link BigInteger}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.BIG_INTEGER)
    public BigInteger toBigInteger(Number value) throws AtlasConversionException {
        return invoke(value, BigInteger.class);
    }

    /**
     * Converts to {@link Boolean}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.BOOLEAN)
    public Boolean toBoolean(Number value) throws AtlasConversionException {
        return invoke(value, Boolean.class);
    }

    /**
     * Converts to {@link Byte}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.BYTE)
    public Byte toByte(Number value) throws AtlasConversionException {
        return invoke(value, Byte.class);
    }

    /**
     * Converts to {@link Character}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.CHAR)
    public Character toCharacter(Number value) throws AtlasConversionException {
        return invoke(value, Character.class);
    }

    /**
     * Converts to {@link Date}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.DATE_TIME)
    public Date toDate(Number value) throws AtlasConversionException {
        return invoke(value, Date.class);
    }

    /**
     * Converts to {@link Double}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.DOUBLE)
    public Double toDouble(Number value) throws AtlasConversionException {
        return invoke(value, Double.class);
    }

    /**
     * Converts to {@link Float}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.FLOAT)
    public Float toFloat(Number value) throws AtlasConversionException {
        return invoke(value, Float.class);
    }

    /**
     * Converts to {@link Integer}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.INTEGER)
    public Integer toInteger(Number value) throws AtlasConversionException {
        return invoke(value, Integer.class);
    }

    /**
     * Converts to {@link LocalDate}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.DATE)
    public LocalDate toLocalDate(Number value) throws AtlasConversionException {
        return invoke(value, LocalDate.class);
    }

    /**
     * Converts to {@link LocalTime}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.TIME)
    public LocalTime toLocalTime(Number value) throws AtlasConversionException {
        return invoke(value, LocalTime.class);
    }

    /**
     * Converts to {@link LocalDateTime}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.DATE_TIME)
    public LocalDateTime toLocalDateTime(Number value) throws AtlasConversionException {
        return invoke(value, LocalDateTime.class);
    }

    /**
     * Converts to {@link Long}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.LONG)
    public Long toLong(Number value) throws AtlasConversionException {
        return invoke(value, Long.class);
    }

    /**
     * Converts to {@link Number}.
     * @param value value
     * @return converted
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.NUMBER)
    public Number toNumber(Number value) {
        return value;
    }

    /**
     * Converts to {@link Short}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.SHORT)
    public Short toShort(Number value) throws AtlasConversionException {
        return invoke(value, Short.class);
    }

    /**
     * Converts to {@link String}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.STRING)
    public String toString(Number value) throws AtlasConversionException {
        return invoke(value, String.class);
    }

    /**
     * Converts to {@link CharBuffer}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.STRING)
    public CharBuffer toCharBuffer(Number value) throws AtlasConversionException {
        return value != null ? CharBuffer.wrap(toString(value)) : null;
    }

    /**
     * Converts to {@link CharSequence}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.STRING)
    public CharSequence toCharSequence(Number value) throws AtlasConversionException {
        return value != null ? toString(value) : null;
    }

    /**
     * Converts to {@link StringBuffer}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.STRING)
    public StringBuffer toStringBuffer(Number value) throws AtlasConversionException {
        return value != null ? new StringBuffer(toString(value)) : null;
    }

    /**
     * Converts to {@link StringBuilder}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.STRING)
    public StringBuilder toStringBuilder(Number value) throws AtlasConversionException {
        return value != null ? new StringBuilder(toString(value)) : null;
    }

    /**
     * Converts to {@link ZonedDateTime}.
     * @param value value
     * @return converted
     * @throws AtlasConversionException conversion error
     */
    @AtlasConversionInfo(sourceType = FieldType.NUMBER, targetType = FieldType.DATE_TIME_TZ)
    public ZonedDateTime toZonedDateTime(Number value) throws AtlasConversionException {
        return invoke(value, ZonedDateTime.class);
    }

    private <T> T invoke(Number object, Class<T> returnType) throws AtlasConversionException {
        if (object == null) {
            return null;
        }
        if (returnType.isInstance(object)) {
            return returnType.cast(object);
        }

        try {
            if (object instanceof BigDecimal) {
                Method m = bigDecimalConverter.getClass().getDeclaredMethod(
                        "to" + returnType.getSimpleName(),
                        object.getClass());
                return returnType.cast(m.invoke(bigDecimalConverter, object));

            } else if (object instanceof BigInteger) {
                Method m = bigIntegerConverter.getClass().getDeclaredMethod(
                        "to" + returnType.getSimpleName(),
                        object.getClass());
                return returnType.cast(m.invoke(bigIntegerConverter, object));

            } else if (object instanceof Byte) {
                Method m = byteConverter.getClass().getDeclaredMethod(
                        "to" + returnType.getSimpleName(),
                        object.getClass());
                return returnType.cast(m.invoke(byteConverter, object));

            } else if (object instanceof Double) {
                Method m = doubleConverter.getClass().getDeclaredMethod(
                        "to" + returnType.getSimpleName(),
                        object.getClass());
                return returnType.cast(m.invoke(doubleConverter, object));

            } else if (object instanceof Float) {
                Method m = floatConverter.getClass().getDeclaredMethod(
                        "to" + returnType.getSimpleName(),
                        object.getClass());
                return returnType.cast(m.invoke(floatConverter, object));

            } else if (object instanceof Integer) {
                Method m = integerConverter.getClass().getDeclaredMethod(
                        "to" + returnType.getSimpleName(),
                        object.getClass());
                return returnType.cast(m.invoke(integerConverter, object));

            } else if (object instanceof Long) {
                Method m = longConverter.getClass().getDeclaredMethod(
                        "to" + returnType.getSimpleName(),
                        object.getClass());
                return returnType.cast(m.invoke(longConverter, object));

            } else if (object instanceof Short) {
                Method m = shortConverter.getClass().getDeclaredMethod(
                        "to" + returnType.getSimpleName(),
                        object.getClass());
                return returnType.cast(m.invoke(shortConverter, object));

            } else {
                throw new AtlasConversionException(String.format(
                        "Unsupported Number type '%s'", object.getClass().getName()));
            }
        } catch (Exception e) {
            throw new AtlasConversionException(String.format(
                    "No converter found from='%s' to='%s'", object.getClass().getName(), returnType.getName()));
        }
    }
}
