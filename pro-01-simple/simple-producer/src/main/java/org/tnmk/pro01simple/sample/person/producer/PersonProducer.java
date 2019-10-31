package org.tnmk.pro01simple.sample.person.producer;

import com.google.protobuf.GeneratedMessageV3;
import org.tnmk.practicespringkafkagrpc.common.message.protobuf.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PersonProducer {

    private static final Logger logger = LoggerFactory.getLogger(PersonProducer.class);

    @Autowired
    private KafkaTemplate<String, GeneratedMessageV3> kafkaTemplate;

    private String topic = TopicConstants.PERSON;

    public void send(Person data){
        logger.info("[KAFKA PUBLISHER] sending data='{}' to topic='{}'", data, topic);

        kafkaTemplate.send(topic, data);
    }
}