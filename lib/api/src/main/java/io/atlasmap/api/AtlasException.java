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
package io.atlasmap.api;

/**
 * The generic AtlasMap Exception.
 */
public class AtlasException extends Exception {
    private static final long serialVersionUID = 7547364931796852076L;

    /**
     * A constructor.
     */
    public AtlasException() {
        super();
    }

    /**
     * A constructor.
     * @param message message
     * @param cause cause
     */
    public AtlasException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * A constructor.
     * @param message message
     */
    public AtlasException(String message) {
        super(message);
    }

    /**
     * A constructor.
     * @param cause cause
     */
    public AtlasException(Throwable cause) {
        super(cause);
    }
}
