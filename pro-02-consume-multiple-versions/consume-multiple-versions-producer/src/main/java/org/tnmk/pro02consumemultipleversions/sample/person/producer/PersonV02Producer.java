package org.tnmk.pro02consumemultipleversions.sample.person.producer;

import com.google.protobuf.GeneratedMessageV3;
import org.tnmk.practicespringkafkagrpc.common.message.protobuf.PersonV01Proto;
import org.tnmk.practicespringkafkagrpc.common.message.protobuf.PersonV02Proto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PersonV02Producer {

    private static final Logger logger = LoggerFactory.getLogger(PersonV02Producer.class);

    @Autowired
    private KafkaTemplate<String, GeneratedMessageV3> kafkaTemplate;

    private String topic = TopicConstants.PERSON;

    public void send(PersonV02Proto data){
        logger.info("[KAFKA PUBLISHER] sending data='{}' to topic='{}'", data, topic);

        kafkaTemplate.send(topic, data);
    }
}