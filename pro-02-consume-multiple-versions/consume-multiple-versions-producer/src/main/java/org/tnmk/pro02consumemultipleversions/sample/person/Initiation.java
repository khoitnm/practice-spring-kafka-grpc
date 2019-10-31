package org.tnmk.pro02consumemultipleversions.sample.person;

import org.tnmk.practicespringkafkagrpc.common.message.protobuf.PersonV01Proto;
import org.tnmk.practicespringkafkagrpc.common.message.protobuf.PersonV02Proto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.pro02consumemultipleversions.sample.person.producer.PersonV01Producer;
import org.tnmk.pro02consumemultipleversions.sample.person.producer.PersonV02Producer;

@Service
public class Initiation {

    @Autowired
    private PersonV01Producer personV01Producer;
    @Autowired
    private PersonV02Producer personV02Producer;

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        PersonV01Proto person = PersonV01Proto.newBuilder()
            .setNickName("PersonV1_"+System.nanoTime())
            .setRealName("RealName_"+System.nanoTime())
            .build();
        personV01Producer.send(person);

        PersonV02Proto personV2 = PersonV02Proto.newBuilder()
            .setNickName("PersonV02_"+System.nanoTime())
            .setFirstName("FirstName_"+System.nanoTime())
            .setLastName("LastName_"+System.nanoTime())
            .build();
        personV02Producer.send(personV2);
    }
}
