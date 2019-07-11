package org.tnmk.kafka.sampleapp.person.producer;

import com.google.protobuf.GeneratedMessageV3;
import com.leonardo.monalisa.common.message.protobuf.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PersonProducerService {

    private static final Logger LOG = LoggerFactory.getLogger(PersonProducerService.class);

    @Autowired
    private KafkaTemplate<String, GeneratedMessageV3> kafkaTemplate;

    @Autowired
    private TopicsProperties topicsProperties;

    public void send(Person data){
        String topic = topicsProperties.getTopics().get(TopicsProperties.TOPIC_KEY_01).getName();
        LOG.info("sending data='{}' to topic='{}'", data, topic);

        kafkaTemplate.send(topic, data);
    }
}