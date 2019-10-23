package org.tnmk.kafka.sampleapp.person.consumer.listener;

import com.leonardo.monalisa.common.message.protobuf.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.tnmk.common.kafka.serialization.protobuf.DeserializedRecord;
import org.tnmk.kafka.sampleapp.person.consumer.usecases.PersonConsumerSampleService;

@Service
public class PersonAutoAckListener {

    private static final Logger LOG = LoggerFactory.getLogger(PersonAutoAckListener.class);

    @Autowired
    private PersonConsumerSampleService personConsumerSampleService;

    @KafkaListener(id = "personAutoAckListener",groupId = "personAutoAckGroup", topics = "${app.topic.example}",
            containerFactory = "personAutoAckListenerContainerFactory", errorHandler = "personErrorHandler")
    public void receive(@Payload DeserializedRecord<Person> message, @Headers MessageHeaders headers) {
        Person data = message.getData();
        logReceiveData(data, headers);
        if (StringUtils.isEmpty(data.getRealName())) {
            //We do this to test the Error Handler
            throw new IllegalArgumentException("The real name must be not empty: "+data);
        } else {
            personConsumerSampleService.autoAck(data);
        }
    }

    private void logReceiveData(Person data, MessageHeaders headers){
        Long offset = (Long)headers.get(KafkaHeaders.OFFSET);
        LOG.info("[AUTO-ACK]received record[{}]='{}'",offset, data);
    }
}