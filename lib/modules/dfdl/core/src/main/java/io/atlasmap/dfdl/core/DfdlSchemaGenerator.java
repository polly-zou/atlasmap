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
package io.atlasmap.dfdl.core;

import java.util.Map;

import org.w3c.dom.Document;

/**
 * A service interface for DFDL Schema generator. DFDL module look for implementation classes
 * of this interface and use for generating customized DFDL schema.
 */
public interface DfdlSchemaGenerator {

    /**
     * Gets the name.
     * @return name
     */
    String getName();

    /**
     * Generates the DFDL Document.
     * @param classLoader class loader
     * @param options options
     * @return Document
     * @throws Exception unexpected error
     */
    Document generate(ClassLoader classLoader, Map<String, String> options) throws Exception;

    /**
     * Gets the options.
     * @return options
     */
    String[] getOptions();

}
