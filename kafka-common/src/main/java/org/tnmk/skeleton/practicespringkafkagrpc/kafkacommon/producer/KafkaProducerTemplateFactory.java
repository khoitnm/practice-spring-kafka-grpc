package org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.producer;

import com.google.protobuf.GeneratedMessageV3;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.serialization.protobuf.ProtobufSerializer;

import java.util.HashMap;
import java.util.Map;

public class KafkaProducerTemplateFactory<T extends GeneratedMessageV3> {
    private KafkaProducerProperties kafkaProperties;

    public KafkaProducerTemplateFactory(KafkaProducerProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    public KafkaTemplate<String, T> protobufKafkaTemplate(){
        return new KafkaTemplate<>(producerFactory(kafkaProperties));
    }

    private Map<String, Object> producerConfigs(KafkaProducerProperties kafkaProperties) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        return props;
    }

    private ProducerFactory<String, T> producerFactory(KafkaProducerProperties kafkaProperties) {
        return new DefaultKafkaProducerFactory<>(producerConfigs(kafkaProperties), new StringSerializer(), new ProtobufSerializer<>());
    }
}
