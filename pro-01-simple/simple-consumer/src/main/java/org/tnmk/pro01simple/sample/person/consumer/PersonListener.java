package org.tnmk.pro01simple.sample.person.consumer;

import com.leonardo.monalisa.common.message.protobuf.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.tnmk.pro01simple.common.kafka.serialization.protobuf.DeserializedRecord;

@Service
public class PersonListener {

    private static final Logger logger = LoggerFactory.getLogger(PersonListener.class);

    @KafkaListener(groupId = "personAutoAckGroup", topics = TopicConstants.PERSON)
    public void receive(@Payload DeserializedRecord<Person> message, @Headers MessageHeaders headers) {
        Person data = message.getData();
        logReceiveData(data, headers);
    }

    private void logReceiveData(Person data, MessageHeaders headers){
        Long offset = (Long)headers.get(KafkaHeaders.OFFSET);
        logger.info("[KAFKA LISTENER]received record[{}]='{}'",offset, data);
    }
}