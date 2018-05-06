package org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.consumer;

import com.google.protobuf.GeneratedMessageV3;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.serialization.protobuf.ProtobufDeserializer;

import java.util.HashMap;
import java.util.Map;

public class KafkaConsumerContainerFactoryBuilder<T extends GeneratedMessageV3> {
    private KafkaConsumerProperties kafkaProperties;

    public KafkaConsumerContainerFactoryBuilder(KafkaConsumerProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    public ConcurrentKafkaListenerContainerFactory<String, T> protobufConcurrentConsumerContainerFactory(Class<T> messagePayloadType) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(protobufConsumerFactory(kafkaProperties, messagePayloadType));
        return factory;
    }

    private Map<String, Object> consumerConfigs(KafkaConsumerProperties kafkaProperties) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "json");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.getAutoOffsetReset());
        return props;
    }

    private ConsumerFactory<String, T> protobufConsumerFactory(KafkaConsumerProperties kafkaProperties, Class<T> messagePayloadType) {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(kafkaProperties), new StringDeserializer(), new ProtobufDeserializer<>(messagePayloadType));
    }


}
