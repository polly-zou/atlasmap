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
package io.atlasmap.actions;

import static io.atlasmap.v2.AtlasModelFactory.unwrapField;
import static io.atlasmap.v2.AtlasModelFactory.wrapWithField;

import java.util.List;

import io.atlasmap.core.DefaultAtlasFunctionResolver;
import io.atlasmap.expression.Expression;
import io.atlasmap.expression.ExpressionException;
import io.atlasmap.spi.AtlasActionProcessor;
import io.atlasmap.spi.AtlasFieldAction;
import io.atlasmap.v2.Field;

/**
 * The expression field action.
 * @deprecated expression is now decleared at {@link io.atlasmap.v2.Mapping} level and processed directly in {@link io.atlasmap.core.DefaultAtlasContext}.
 */
@Deprecated
public class ExpressionFieldAction implements AtlasFieldAction {

    /**
     * Processes expression field action.
     * @param action action model
     * @param args expression arguments
     * @return processed
     * @throws ExpressionException expression processing error
     */
    @Deprecated
    @AtlasActionProcessor
    public static Object process(io.atlasmap.v2.Expression action, List<Object> args) throws ExpressionException {
        if (action.getExpression() == null || action.getExpression().trim().isEmpty()) {
            return null;
        }

        Expression parsedExpression = Expression.parse(action.getExpression(), DefaultAtlasFunctionResolver.getInstance());
        Field answer = parsedExpression.evaluate((index) -> {
            try {
                return wrapWithField(args.get(Integer.parseInt(index)));
            } catch (Throwable e) {
                throw new ExpressionException("Invalid variable: " + index);
            }
        });
        return unwrapField(answer);
    }

}
