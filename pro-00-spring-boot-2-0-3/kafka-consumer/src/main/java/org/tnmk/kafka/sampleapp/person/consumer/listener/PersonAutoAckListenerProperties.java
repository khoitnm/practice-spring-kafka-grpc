package org.tnmk.kafka.sampleapp.person.consumer.listener;

import org.tnmk.common.kafka.consumer.KafkaListenerContainerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "person-auto-commit-consumer")
public class PersonAutoAckListenerProperties extends KafkaListenerContainerProperties {
    //Just extend the superclass so that we can apply the @ConfigurationProperties on the properties of the super class.
    //No need to override anything here.
}