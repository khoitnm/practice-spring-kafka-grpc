package org.tnmk.common.kafka.serialization.protobuf;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.leonardo.monalisa.common.message.protobuf.Person;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.ExtendedDeserializer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Deserializer for a Protocol Buffer message payload.
 *
 * @param <T> The Protocol Buffer message type.
 */
public class ProtobufDeserializer<T extends GeneratedMessageV3> implements ExtendedDeserializer<T> {
    protected static final String PARSER_GETTER_METHOD_NAME = "parser";

    protected Parser<T> parser;

    /**
     * For now, the constructor need the @param messageType because the Protobuf parser instance is coupled to the generated message type.
     * It means that each type will have a separated parser instance.
     * </br>
     * If I can find a way to initiate that parser independently, then we don't need the messageType anymore.
     * And then this deserializer could be work with any message type.
     *
     * @param messageType
     */
    public ProtobufDeserializer(Class<T> messageType) {
        parser = getParserFromMessageType(messageType);
    }

    private Parser<T> getParserFromMessageType(Class<T> messageType) {
        if (messageType == null) {
            throw new SerializationException("Protocol Buffer message type cannot be null");
        }

        try {
            Method method = messageType.getMethod(PARSER_GETTER_METHOD_NAME);
            return (Parser<T>) method.invoke(null);
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new SerializationException("Failed to find Protocol Buffer parser for [" + messageType.getCanonicalName() + "]", e);
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        //Unused
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
//            throw new RuntimeException("Some runtime here.");
            //The error here will cause infinite loop for Consumer.
//            data = new byte[]{1};
            T result = parser.parseFrom(data);
            Person person = (Person)result;
            if (person.getRealName().contains("DeErr")){
                throw new RuntimeException("Deserialize error intentionally");
            }
            return result;
        } catch (InvalidProtocolBufferException e) {
            throw new SerializationException(String.format("[Protobuf Serialization] Cannot parse byte[] data to object in topic '%s': %s", topic, e.getMessage()), e);
        }
    }

    @Override
    public void close() {
        //Unused
    }

    @Override
    public T deserialize(String topic, Headers headers, byte[] data) {
        try {
            return deserialize(topic, data);
        } catch (Exception e) {
            throw e;
        }
    }
}
