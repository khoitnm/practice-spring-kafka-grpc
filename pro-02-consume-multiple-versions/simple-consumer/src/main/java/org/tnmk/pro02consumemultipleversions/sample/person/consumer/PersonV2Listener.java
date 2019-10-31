package org.tnmk.pro02consumemultipleversions.sample.person.consumer;

import com.leonardo.monalisa.common.message.protobuf.Person;
import com.leonardo.monalisa.common.message.protobuf.PersonV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.tnmk.pro02consumemultipleversions.common.kafka.serialization.protobuf.DeserializedRecord;

@Service
public class PersonV2Listener {

    private static final Logger logger = LoggerFactory.getLogger(PersonV2Listener.class);

    @KafkaListener(groupId = "personAutoAckGroup", topics = TopicConstants.PERSON)
    public void receive(@Payload DeserializedRecord<PersonV2> message, @Headers MessageHeaders headers) {
        PersonV2 data = message.getData();
        logReceiveData(data, headers);
    }

    private void logReceiveData(PersonV2 data, MessageHeaders headers){
        Long offset = (Long)headers.get(KafkaHeaders.OFFSET);
        logger.info("[KAFKA LISTENER]received record[{}]='{}'",offset, data);
    }
}