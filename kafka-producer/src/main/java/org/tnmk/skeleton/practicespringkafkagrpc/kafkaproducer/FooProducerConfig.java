package org.tnmk.skeleton.practicespringkafkagrpc.kafkaproducer;

import com.google.protobuf.GeneratedMessageV3;
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
public class FooProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return props;
    }

    @Bean
    public <T extends GeneratedMessageV3> ProducerFactory<String, T> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs(), new StringSerializer(), new ProtobufSerializer<>());
    }

    @Bean
    public  <T extends GeneratedMessageV3> KafkaTemplate<String, T> kafkaTemplate() {
        return new KafkaTemplate<> (producerFactory());
    }
}