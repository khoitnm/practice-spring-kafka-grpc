package org.tnmk.pro01simple.sample.person.consumer;

import org.tnmk.practicespringkafkagrpc.common.message.protobuf.Person;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.tnmk.pro01simple.common.kafka.consumer.KafkaListenerConfigHelper;
import org.tnmk.pro01simple.common.kafka.serialization.protobuf.DeserializedRecord;

@Configuration
@EnableKafka
public class KafkaListenerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DeserializedRecord<Person>> kafkaListenerContainerFactory(@Autowired ConsumerFactory originalConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, DeserializedRecord<Person>> factory = KafkaListenerConfigHelper.createListenerContainerFactory(originalConsumerFactory, Person.class);
        return factory;
    }
}