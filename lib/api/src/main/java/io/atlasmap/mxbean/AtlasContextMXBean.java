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
package io.atlasmap.mxbean;

/**
 * The {@link io.atlasmap.api.AtlasContext} MBean.
 */
public interface AtlasContextMXBean {

    /**
     * Gets the UUID.
     * @return UUID
     */
    String getUuid();

    /**
     * Gets the mapping name.
     * @return mapping name
     */
    String getMappingName();

    /**
     * Gets the mapping URI.
     * @return mapping URI
     */
    String getMappingUri();

    /**
     * Gets the class name.
     * @return class name
     */
    String getClassName();

    /**
     * Gets the thread name.
     * @return thread name
     */
    String getThreadName();

    /**
     * Gets the version.
     * @return version
     */
    String getVersion();

}
