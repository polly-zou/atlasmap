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

/**
 * The string handling utility.
 */
public final class Strings {

    private Strings(){}

    /**
     * Strips the prefix.
     * @param string string
     * @param prefix prefix
     * @return result
     */
    public static String stripPrefix(String string, String prefix) {
        if( string == null ) {
            return null;
        }
        if( prefix==null ) {
            return string;
        }
        if( string.startsWith(prefix) ) {
            return string.substring(prefix.length());
        }
        return string;
    }

    /**
     * Strips the suffix.
     * @param string string
     * @param suffix suffix
     * @return result
     */
    public static String stripSuffix(String string, String suffix) {
        if( string == null ) {
            return null;
        }
        if( suffix==null ) {
            return string;
        }
        if( string.endsWith(suffix) ) {
            return string.substring(0, string.length()-suffix.length());
        }
        return string;
    }

}
