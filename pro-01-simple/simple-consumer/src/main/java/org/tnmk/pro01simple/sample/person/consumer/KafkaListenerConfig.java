package org.tnmk.pro01simple.sample.person.consumer;

import com.leonardo.monalisa.common.message.protobuf.Person;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.tnmk.pro01simple.common.kafka.consumer.KafkaListenerConfigHelper;

@Configuration
@EnableKafka
public class KafkaListenerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Person> kafkaListenerContainerFactory(@Autowired ConsumerFactory originalConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Person> factory = new ConcurrentKafkaListenerContainerFactory<>();
        ConsumerFactory consumerFactory = KafkaListenerConfigHelper.createConsumerFactory(originalConsumerFactory, Person.class);
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Person> kafkaListenerContainerFactory(@Autowired ConcurrentKafkaListenerContainerFactory originalKafkaListenerContainerFactory) {
//        ConsumerFactory consumerFactory = KafkaListenerConfigHelper.createConsumerFactory(originalKafkaListenerContainerFactory.getConsumerFactory(), Person.class);
//
//        ConcurrentKafkaListenerContainerFactory<String, Person> factory = new ConcurrentKafkaListenerContainerFactory<>();
////        BeanUtils.copyProperties(originalKafkaListenerContainerFactory, factory);
//        factory.setConsumerFactory(consumerFactory);
//        return factory;
//    }
}