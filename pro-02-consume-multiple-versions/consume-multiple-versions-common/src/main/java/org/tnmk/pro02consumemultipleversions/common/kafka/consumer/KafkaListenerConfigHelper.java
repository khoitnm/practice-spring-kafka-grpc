package org.tnmk.pro02consumemultipleversions.common.kafka.consumer;

import com.google.protobuf.GeneratedMessageV3;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.tnmk.pro02consumemultipleversions.common.kafka.serialization.protobuf.DeserializedRecord;
import org.tnmk.pro02consumemultipleversions.common.kafka.serialization.protobuf.ProtobufDeserializer;

public class KafkaListenerConfigHelper {

    public static <T extends GeneratedMessageV3> ConsumerFactory<String, DeserializedRecord<T>> createConsumerFactory(ConsumerFactory originalConsumerFactory, Class<T> messageClass) {

        return new DefaultKafkaConsumerFactory<String, DeserializedRecord<T>>(
            originalConsumerFactory.getConfigurationProperties(),
                new StringDeserializer(),
                new ProtobufDeserializer<T>(messageClass));
    }

    public static <T extends GeneratedMessageV3> ConcurrentKafkaListenerContainerFactory<String, DeserializedRecord<T>> createListenerContainerFactory(ConsumerFactory originalConsumerFactory, Class<T> messageClass){
        ConcurrentKafkaListenerContainerFactory<String, DeserializedRecord<T>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        ConsumerFactory consumerFactory = KafkaListenerConfigHelper.createConsumerFactory(originalConsumerFactory, messageClass);
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

}