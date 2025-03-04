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
package io.atlasmap.v2;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * The jackson {@link com.fasterxml.jackson.databind.Module} of the field action.
 */
public class AtlasJsonModule extends SimpleModule {

    private static final long serialVersionUID = -2352383379765836801L;
    private static final String NAME = "AtlasJsonModule";
    private static final VersionUtil VERSION_UTIL = new VersionUtil() {
    };

    /**
     * A constructor.
     */
    public AtlasJsonModule() {
        super(NAME, VERSION_UTIL.version());
    }
}
