package org.tnmk.skeleton.practicespringkafkagrpc.kafkaproducer;

import com.google.protobuf.GeneratedMessageV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.producer.KafkaProducerProperties;
import org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.producer.KafkaProducerTemplateFactory;

@Configuration
public class FooProducerConfig {
    @Configuration
    @ConfigurationProperties(prefix = "kafka-producer")
    public static class ProducerProperties extends KafkaProducerProperties{
        //Just extend the superclass so that we can apply the @ConfigurationProperties on properties of the super class.
    }
    @Autowired
    private ProducerProperties producerProperties;


    @Bean
    public  <T extends GeneratedMessageV3> KafkaTemplate<String, T> kafkaTemplate() {
        KafkaProducerTemplateFactory<T> kafkaProducerTemplateFactory = new KafkaProducerTemplateFactory<>(producerProperties);
        return kafkaProducerTemplateFactory.createProtobufKafkaTemplate();
    }
}