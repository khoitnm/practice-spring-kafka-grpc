package org.tnmk.pro01simple.common.kafka.consumer;

import com.google.protobuf.GeneratedMessageV3;
import com.leonardo.monalisa.common.message.protobuf.Person;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.tnmk.pro01simple.common.kafka.serialization.protobuf.DeserializedRecord;
import org.tnmk.pro01simple.common.kafka.serialization.protobuf.ProtobufDeserializer;

public class KafkaListenerConfigHelper {

    public static <T extends GeneratedMessageV3> ConsumerFactory<String, DeserializedRecord<T>> createConsumerFactory(ConsumerFactory originalConsumerFactory, Class<T> messageClass) {

        return new DefaultKafkaConsumerFactory<String, DeserializedRecord<T>>(
            originalConsumerFactory.getConfigurationProperties(),
                new StringDeserializer(),
                new ProtobufDeserializer<T>(messageClass));
    }

}