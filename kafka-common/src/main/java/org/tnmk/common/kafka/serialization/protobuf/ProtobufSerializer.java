package org.tnmk.common.kafka.serialization.protobuf;

import com.google.protobuf.GeneratedMessageV3;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Serializer for a Protocol Buffer message payload.
 */
public class ProtobufSerializer<T extends GeneratedMessageV3> implements Serializer<T> {
    /**
     * Creates a serializer for Protocol Buffer message payload.
     */
    public ProtobufSerializer() {
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        //Unused
    }

    @Override
    public byte[] serialize(String topic, T data) {
        return data.toByteArray();
    }

    @Override
    public void close() {
        //Unused
    }
}
