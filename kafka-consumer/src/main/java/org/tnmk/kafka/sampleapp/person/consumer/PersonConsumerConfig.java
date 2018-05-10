package org.tnmk.kafka.sampleapp.person.consumer;

import org.tnmk.common.kafka.consumer.KafkaListenerContainerFactoryConstructor;
import org.tnmk.common.kafka.serialization.protobuf.ProtobufDeserializer;
import com.leonardo.monalisa.common.message.protobuf.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@Configuration
@EnableKafka //@EnableKafka is used to enable detection of @KafkaListener annotation.
public class PersonConsumerConfig {
//    /**
//     * We want a mock instance so that we can verify whether the method {@link PersonActionsAcknowledgement#autoAck(Person)} is processed or not.
//     * @return
//     */
//    @Bean
//    public PersonActionsAcknowledgement personRepository() {
//        return Mockito.mock(PersonActionsAcknowledgement.class);
//    }

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
        ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory = kafkaListenerContainerFactoryConstructor.createProtobufConcurrentConsumerContainerFactory(Person.class);
        kafkaListenerContainerFactoryConstructor.applyErrorHandler(concurrentKafkaListenerContainerFactory, personGlobalContainerErrorHandler);
        return concurrentKafkaListenerContainerFactory;
    }

    //MANUAL ACKNOWLEDGE LISTENER /////////////////////////////////////////////////////////////////////////////
    @Autowired
    private PersonGlobalContainerErrorHandler personGlobalContainerErrorHandler;

    @Autowired
    private PersonManualAckListenerProperties personManualAckListenerProperties;

    @Bean("personManualAckListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Person> personManualAckListenerContainerFactory() {
        KafkaListenerContainerFactoryConstructor kafkaListenerContainerFactoryConstructor = new KafkaListenerContainerFactoryConstructor(personManualAckListenerProperties);
        ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory = kafkaListenerContainerFactoryConstructor.createProtobufConcurrentConsumerContainerFactory(Person.class);
        kafkaListenerContainerFactoryConstructor.applyErrorHandler(concurrentKafkaListenerContainerFactory, personGlobalContainerErrorHandler);
        return concurrentKafkaListenerContainerFactory;
    }
}