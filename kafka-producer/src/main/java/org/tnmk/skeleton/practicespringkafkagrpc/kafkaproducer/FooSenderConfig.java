package org.tnmk.skeleton.practicespringkafkagrpc.kafkaproducer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.Foo;
import org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.serialization.protobuf.ProtobufSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FooSenderConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ProtobufSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, Foo> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Foo> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}