package org.tnmk.pro02consumemultipleversions.sample.person.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringkafkagrpc.common.message.protobuf.EntityTypeProto;
import org.tnmk.practicespringkafkagrpc.common.message.protobuf.EventProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.tnmk.pro02consumemultipleversions.common.kafka.serialization.protobuf.DeserializedRecord;
import org.tnmk.pro02consumemultipleversions.sample.person.mapper.PersonMapper;
import org.tnmk.pro02consumemultipleversions.sample.person.model.PersonV01;
import org.tnmk.pro02consumemultipleversions.sample.person.model.PersonV02;
import org.tnmk.pro02consumemultipleversions.sample.person.service.PersonService;

@Service
public class PersonMultipleVersionsListener {

    private static final Logger logger = LoggerFactory.getLogger(PersonMultipleVersionsListener.class);

    @Autowired
    private PersonService personService;

    @KafkaListener(groupId = "personAutoAckGroup", topics = TopicConstants.PERSON)
    public void receive(@Payload DeserializedRecord<EventProto> message, @Headers MessageHeaders headers) {
        EventProto eventProto = message.getData();

        //TODO There should be a better way to handle this mapper? (avoid if-else)
        if (eventProto.getEntityType() == EntityTypeProto.ENTITY_TYPE_PERSON_V01) {
            PersonV01 personV01 = PersonMapper.toPersonV01(eventProto.getPersonV01());
            personService.logPersonV01(personV01);
        } else if (eventProto.getEntityType() == EntityTypeProto.ENTITY_TYPE_PERSON_V02) {
            PersonV02 personV02 = PersonMapper.toPersonV02(eventProto.getPersonV02());
            personService.logPersonV02(personV02);
        }
        logReceiveData(eventProto, headers);
    }

    private void logReceiveData(EventProto data, MessageHeaders headers) {
        Long offset = (Long) headers.get(KafkaHeaders.OFFSET);
        logger.info("[KAFKA LISTENER]received record[{}]='{}'", offset, data);
    }
}