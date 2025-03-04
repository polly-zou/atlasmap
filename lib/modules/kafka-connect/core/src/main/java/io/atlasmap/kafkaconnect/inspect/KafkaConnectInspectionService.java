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
package io.atlasmap.kafkaconnect.inspect;

import java.util.HashMap;

import io.atlasmap.kafkaconnect.v2.KafkaConnectDocument;

/**
 * The Document inspection service for Kafka Connect.
 */
public class KafkaConnectInspectionService {
    private KafkaConnectInspector inspector;

    /**
     * A constructor.
     * @param loader class loader
     */
    public KafkaConnectInspectionService(ClassLoader loader) {
        this.inspector = new KafkaConnectInspector(loader);
    }

    /**
     * Inspects Kafka Connect JSON schema.
     * @param jsonSchema Kafka Connect JSON schema
     * @param options inspection options
     * @return inspected
     * @throws Exception unexpected error
     */
    public KafkaConnectDocument inspectJson(String jsonSchema, HashMap<String, ?> options) throws Exception {
        inspector.inspectJson(jsonSchema, options);
        return inspector.getKafkaConnectDocument();
    }

    /**
     * Inspects Kafka Connect AVRO schema.
     * @param avroSchema Kafka Connect AVRO schema
     * @param options inspection options
     * @return inspected
     * @throws Exception unexpected error
     */
    public KafkaConnectDocument inspectAvro(String avroSchema, HashMap<String, ?> options) throws Exception {
        inspector.inspectAvro(avroSchema, options);
        return inspector.getKafkaConnectDocument();
    }

}
