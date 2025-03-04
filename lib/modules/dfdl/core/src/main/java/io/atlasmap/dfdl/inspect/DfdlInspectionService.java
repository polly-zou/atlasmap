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
package io.atlasmap.dfdl.inspect;

import java.util.Map;

import io.atlasmap.xml.v2.XmlDocument;

/**
 * The DFDL inspection service.
 */
public class DfdlInspectionService {
    private DfdlInspector inspector;

    /**
     * A constructor.
     * @param loader class loader
     */
    public DfdlInspectionService(ClassLoader loader) {
        this.inspector = new DfdlInspector(loader);
    }

    /**
     * Inspects the DFDL instance.
     * @param dfdlSchemaName instance
     * @param options options
     * @return inspected
     * @throws Exception unexpected error
     */
    public XmlDocument inspectDfdlInstance(String dfdlSchemaName, Map<String, String> options) throws Exception {
        inspector.inspectInstance(dfdlSchemaName, options);
        return inspector.getXmlDocument();
    }

    /**
     * Inspects the DFDL schema.
     * @param dfdlSchemaName schema
     * @param options options
     * @return inspected
     * @throws Exception unexpected error
     */
    public XmlDocument inspectDfdlSchema(String dfdlSchemaName, Map<String, String> options) throws Exception {
        inspector.inspectSchema(dfdlSchemaName, options);
        return inspector.getXmlDocument();
    }

}
