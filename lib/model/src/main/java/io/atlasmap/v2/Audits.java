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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The container of the {@link Audit}.
 */
public class Audits implements Serializable {

    private static final long serialVersionUID = 1L;

    /** A list of {@link Audit} */
    protected List<Audit> audit;

    /**
     * Gets the value of the audit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the audit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAudit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Audit }
     * 
     * @return a list of {@link Audit}
     */
    public List<Audit> getAudit() {
        if (audit == null) {
            audit = new ArrayList<Audit>();
        }
        return this.audit;
    }

}
