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
package io.atlasmap.expression.internal;

import static io.atlasmap.v2.AtlasModelFactory.wrapWithField;

import java.math.BigDecimal;

import io.atlasmap.expression.Expression;
import io.atlasmap.expression.ExpressionContext;
import io.atlasmap.expression.ExpressionException;
import io.atlasmap.v2.Field;


/**
 * Represents a constant expression.
 * 
 * @version $Revision: 1.2 $
 */
public class ConstantExpression implements Expression {

    static class BooleanConstantExpression extends ConstantExpression implements BooleanExpression {
        BooleanConstantExpression(Object value) {
            super(value);
        }

        public boolean matches(ExpressionContext message) throws ExpressionException {
            Object object = evaluate(message);
            return object != null && object == Boolean.TRUE;
        }
    }

    /** null. */
    public static final BooleanConstantExpression NULL = new BooleanConstantExpression(null);
    /** true. */
    public static final BooleanConstantExpression TRUE = new BooleanConstantExpression(Boolean.TRUE);
    /** false. */
    public static final BooleanConstantExpression FALSE = new BooleanConstantExpression(Boolean.FALSE);

    private Object value;

    /**
     * A constructor.
     * @param value value
     */
    public ConstantExpression(Object value) {
        this.value = value;
    }

    /**
     * Creates from the decimal.
     * @param text decimal
     * @return result
     */
    public static ConstantExpression createFromDecimal(String text) {

        // Strip off the 'l' or 'L' if needed.
        if (text.endsWith("l") || text.endsWith("L")) {
            text = text.substring(0, text.length() - 1);
        }

        Number value;
        try {
            value = new Long(text);
        } catch (NumberFormatException e) {
            // The number may be too big to fit in a long.
            value = new BigDecimal(text);
        }

        long l = value.longValue();
        if (Integer.MIN_VALUE <= l && l <= Integer.MAX_VALUE) {
            value = Integer.valueOf(value.intValue());
        }
        return new ConstantExpression(value);
    }

    /**
     * Creates from the hex.
     * @param text hex
     * @return result
     */
    public static ConstantExpression createFromHex(String text) {
        Number value = Long.valueOf(Long.parseLong(text.substring(2), 16));
        long l = value.longValue();
        if (Integer.MIN_VALUE <= l && l <= Integer.MAX_VALUE) {
            value = Integer.valueOf(value.intValue());
        }
        return new ConstantExpression(value);
    }

    /**
     * Creates from the octal.
     * @param text octal
     * @return result
     */
    public static ConstantExpression createFromOctal(String text) {
        Number value = Long.valueOf(Long.parseLong(text, 8));
        long l = value.longValue();
        if (Integer.MIN_VALUE <= l && l <= Integer.MAX_VALUE) {
            value = Integer.valueOf(value.intValue());
        }
        return new ConstantExpression(value);
    }

    /**
     * Creates from the float.
     * @param text float
     * @return result
     */
    public static ConstantExpression createFloat(String text) {
        Number value = new Double(text);
        return new ConstantExpression(value);
    }

    /**
     * Evaluates the expression.
     * @param expressionContext expression context
     * @return {@link Field}
     */
    public Field evaluate(ExpressionContext expressionContext) throws ExpressionException {
        return wrapWithField(value);
    }

    /**
     * Gets the value.
     * @return value
     */
    public Object getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        if (value == null) {
            return "NULL";
        }
        if (value instanceof Boolean) {
            return ((Boolean)value).booleanValue() ? "TRUE" : "FALSE";
        }
        if (value instanceof String) {
            return encodeString((String)value);
        }
        return value.toString();
    }

    /**
     * {@inheritDoc}
     * TODO: more efficient hashCode()
     */
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * {@inheritDoc}
     * TODO: more efficient hashCode()
     */
    public boolean equals(Object o) {

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }
        return toString().equals(o.toString());

    }

    /**
     * Encodes the value of string so that it looks like it would look like when
     * it was provided in a selector.
     * 
     * @param s {@link String} to encode
     * @return encoded {@link String}
     */
    public static String encodeString(String s) {
        StringBuffer b = new StringBuffer();
        b.append('\'');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\'') {
                b.append(c);
            }
            b.append(c);
        }
        b.append('\'');
        return b.toString();
    }

}
