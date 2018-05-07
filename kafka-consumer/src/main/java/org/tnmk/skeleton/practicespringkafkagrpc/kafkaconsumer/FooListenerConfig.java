package org.tnmk.skeleton.practicespringkafkagrpc.kafkaconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.Foo;
import org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.consumer.KafkaConsumerProperties;
import org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.consumer.KafkaConsumerContainerFactoryConstructor;

@Configuration
@EnableKafka
public class FooListenerConfig {

    @Configuration
    @ConfigurationProperties(prefix = "kafka-consumer")
    public static class ConsumerProperties extends KafkaConsumerProperties{}

    @Autowired
    private KafkaConsumerProperties kafkaConsumerProperties;

    @Bean("fooKafkaContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Foo> kafkaListenerContainerFactory() {
        KafkaConsumerContainerFactoryConstructor kafkaConsumerContainerFactoryConstructor = new KafkaConsumerContainerFactoryConstructor(kafkaConsumerProperties);
        return kafkaConsumerContainerFactoryConstructor.protobufConcurrentConsumerContainerFactory(Foo.class);
    }

}