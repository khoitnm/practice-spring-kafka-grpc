package org.tnmk.kafka.sampleapp.person.producer;

import com.google.protobuf.GeneratedMessageV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.tnmk.common.kafka.producer.KafkaGlobalProducerProperties;
import org.tnmk.common.kafka.producer.KafkaProducerTemplateFactory;

@Configuration
public class PersonProducerConfig {
    /**
     * All the properties values of this bean was loaded from *.yml file.
     * It was configured in {@link KafkaGlobalProducerProperties}
     */
    @Autowired
    private KafkaGlobalProducerProperties kafkaGlobalProducerProperties;

    @Bean
    public  <T extends GeneratedMessageV3> KafkaTemplate<String, T> kafkaTemplate() {
        KafkaProducerTemplateFactory<T> kafkaProducerTemplateFactory = new KafkaProducerTemplateFactory<>(kafkaGlobalProducerProperties);
        return kafkaProducerTemplateFactory.createProtobufKafkaTemplate();
    }
}