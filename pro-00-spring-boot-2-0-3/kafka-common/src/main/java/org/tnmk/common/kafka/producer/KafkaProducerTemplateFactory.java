package org.tnmk.common.kafka.producer;

import com.google.protobuf.GeneratedMessageV3;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.tnmk.common.kafka.serialization.protobuf.ProtobufSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Please view the UnitTest to see how to use this class.
 * @param <T>
 */
public class KafkaProducerTemplateFactory<T extends GeneratedMessageV3> {
    private KafkaGlobalProducerProperties kafkaProperties;

    public KafkaProducerTemplateFactory(KafkaGlobalProducerProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    public KafkaTemplate<String, T> createProtobufKafkaTemplate(){
        return new KafkaTemplate<>(createProducerFactory(kafkaProperties));
    }

    private Map<String, Object> createProducerConfigs(KafkaGlobalProducerProperties kafkaProperties) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        return props;
    }

    private ProducerFactory<String, T> createProducerFactory(KafkaGlobalProducerProperties kafkaProperties) {
        return new DefaultKafkaProducerFactory<>(createProducerConfigs(kafkaProperties), new StringSerializer(), new ProtobufSerializer<>());
    }
}
