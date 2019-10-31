package org.tnmk.kafka.sampleapp.person.consumer;

import org.tnmk.practicespringkafkagrpc.common.message.protobuf.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.tnmk.common.kafka.consumer.KafkaListenerContainerFactoryConstructor;
import org.tnmk.common.kafka.serialization.protobuf.ProtobufDeserializer;
import org.tnmk.kafka.sampleapp.person.consumer.listener.PersonAutoAckListenerProperties;
import org.tnmk.kafka.sampleapp.person.consumer.listener.PersonManualAckListenerProperties;

@Configuration
@EnableKafka //@EnableKafka is used to enable detection of @KafkaListener annotation.
public class PersonConsumerConfig {
    //AUTO ACKNOWLEDGE LISTENER /////////////////////////////////////////////////////////////////////////////
    /**
     * All the properties values of this bean was loaded from *.yml file.
     * It was configured in {@link PersonAutoAckListenerProperties}
     */
    @Autowired
    private PersonAutoAckListenerProperties personAutoAckListenerProperties;

    /**
     * For each type of message (e.g. Person), we have to create a separated ListenerContainerFactory bean.
     * It's not so good, but I cannot find any other convenient ways to do it right now.
     * <br/>
     * The root cause is the Protobuf parser instance is coupled to the generated message type.
     * Please view more in {@link ProtobufDeserializer}
     * @return
     */
    @Bean("personAutoAckListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Person> personAutoAckListenerContainerFactory() {
        KafkaListenerContainerFactoryConstructor kafkaListenerContainerFactoryConstructor = new KafkaListenerContainerFactoryConstructor(personAutoAckListenerProperties);
        return kafkaListenerContainerFactoryConstructor.createProtobufConcurrentConsumerContainerFactory(Person.class);
    }

    //MANUAL ACKNOWLEDGE LISTENER /////////////////////////////////////////////////////////////////////////////
    @Autowired
    private PersonManualAckListenerProperties personManualAckListenerProperties;

    @Bean("personManualAckListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Person> personManualAckListenerContainerFactory() {
        KafkaListenerContainerFactoryConstructor kafkaListenerContainerFactoryConstructor = new KafkaListenerContainerFactoryConstructor(personManualAckListenerProperties);
        return kafkaListenerContainerFactoryConstructor.createProtobufConcurrentConsumerContainerFactory(Person.class);
    }
}