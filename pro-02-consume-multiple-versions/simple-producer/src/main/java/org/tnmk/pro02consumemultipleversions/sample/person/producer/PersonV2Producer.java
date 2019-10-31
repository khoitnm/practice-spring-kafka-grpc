package org.tnmk.pro02consumemultipleversions.sample.person.producer;

import com.google.protobuf.GeneratedMessageV3;
import com.leonardo.monalisa.common.message.protobuf.Person;
import com.leonardo.monalisa.common.message.protobuf.PersonV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PersonV2Producer {

    private static final Logger logger = LoggerFactory.getLogger(PersonV2Producer.class);

    @Autowired
    private KafkaTemplate<String, GeneratedMessageV3> kafkaTemplate;

    private String topic = TopicConstants.PERSON;

    public void send(PersonV2 data){
        logger.info("[KAFKA PUBLISHER] sending data='{}' to topic='{}'", data, topic);

        kafkaTemplate.send(topic, data);
    }
}