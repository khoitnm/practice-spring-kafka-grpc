package org.tnmk.kafka.sampleapp.person.consumer;

import org.tnmk.common.kafka.consumer.KafkaListenerContainerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "person-manual-commit-consumer")
public class PersonManualAckListenerProperties extends KafkaListenerContainerProperties {
    //Just extend the superclass so that we can apply the @ConfigurationProperties on the properties of the super class.
    //No need to override anything here.
}