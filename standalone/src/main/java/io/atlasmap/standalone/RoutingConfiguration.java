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
package io.atlasmap.standalone;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Routing configuration.
 */
@Component
public class RoutingConfiguration extends WebMvcConfigurerAdapter {

    /**
     * Adds a view controller.
     * @param registry registry
     */
    public void addViewControllers(final ViewControllerRegistry registry){
        super.addViewControllers(registry);
        registry.addViewController("/mapping-id/*").setViewName("forward:/");
    }
}
