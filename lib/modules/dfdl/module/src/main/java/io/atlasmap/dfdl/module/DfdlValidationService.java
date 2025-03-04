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
package io.atlasmap.dfdl.module;

import io.atlasmap.spi.AtlasConversionService;
import io.atlasmap.spi.AtlasFieldActionService;
import io.atlasmap.spi.AtlasModuleDetail;
import io.atlasmap.xml.module.XmlValidationService;

/**
 * The module validation service for DFDL Document.
 */
public class DfdlValidationService extends XmlValidationService {

    private AtlasModuleDetail moduleDetail = DfdlModule.class.getAnnotation(AtlasModuleDetail.class);

    /**
     * A constructor.
     * @param conversionService conversion service
     * @param fieldActionService field action service
     */
    public DfdlValidationService(AtlasConversionService conversionService, AtlasFieldActionService fieldActionService) {
        super(conversionService, fieldActionService);
    }

    @Override
    protected AtlasModuleDetail getModuleDetail() {
        return moduleDetail;
    }

}
