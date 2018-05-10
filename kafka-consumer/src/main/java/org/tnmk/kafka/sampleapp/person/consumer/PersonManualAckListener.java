package org.tnmk.kafka.sampleapp.person.consumer;

import com.leonardo.monalisa.common.message.protobuf.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * For some reason, the manual acknowledge doesn't work???
 * https://stackoverflow.com/questions/41497790/cannot-disable-manual-commits-on-kafka-message-using-spring-integration-kafka-in
 */
@Service
public class PersonManualAckListener {

    private static final Logger LOG = LoggerFactory.getLogger(PersonManualAckListener.class);

    @Autowired
    private PersonActionsAcknowledgement personActionsAcknowledgement;

    //Note: this groupId is different from PersonAutoAckListener
    @KafkaListener(id = "personManualAckListener", groupId = "personManualAckGroup", topics = "${app.topic.example}",
            containerFactory = "personManualAckListenerContainerFactory",
            errorHandler = "personStopWhenExceptionErrorHandler")
    public void receive(@Payload Person data, @Headers MessageHeaders headers, Acknowledgment acknowledgment) {
        logReceiveData(data, headers);
        if (StringUtils.isEmpty(data.getRealName())) {
            //We do this to test the Error Handler
            throw new IllegalArgumentException("The real name must be not empty: " + data);
        } else {
            personActionsAcknowledgement.manualAck(data);
        }
        // Note: Even if the don't call acknowledge(), the Listener still continue processing the next item. It doesn't stuck here.
        // However, when we restart the application, it will replay old records which are not acknowledged yet.
        Long offset = (Long)headers.get(KafkaHeaders.OFFSET);
//        if (offset == 115){
            acknowledgment.acknowledge();
//        }
    }

    private void logReceiveData(Person data, MessageHeaders headers) {
        Long offset = (Long)headers.get(KafkaHeaders.OFFSET);
        LOG.info("[MANUAL-ACK]received record[{}]='{}'",offset, data);
    }
}