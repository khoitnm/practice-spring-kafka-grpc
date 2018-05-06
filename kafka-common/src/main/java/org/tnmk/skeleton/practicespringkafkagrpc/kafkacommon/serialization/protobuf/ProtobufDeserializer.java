package org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.serialization.protobuf;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import org.apache.kafka.common.serialization.Deserializer;
import org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.serialization.SerializationException;

import java.util.Map;

/**
 * Deserializer for a Protocol Buffer message payload.
 *
 * @param <T> The Protocol Buffer message type.
 */
public class ProtobufDeserializer<T extends GeneratedMessageV3> implements Deserializer<T> {
    protected static final String PARSER_GETTER_METHOD_NAME = "parser";

    protected Parser<T> parser;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        //Unused
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            return parser.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            throw new SerializationException(String.format("[Protobuf Serialization] Cannot parse byte[] data to object in topic '%s': %s", topic, e.getMessage()), e);
        }
    }

    @Override
    public void close() {
        //Unused
    }
}
