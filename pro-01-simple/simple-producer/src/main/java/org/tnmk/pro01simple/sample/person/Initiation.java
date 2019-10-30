package org.tnmk.pro01simple.sample.person;

import com.leonardo.monalisa.common.message.protobuf.Person;
import com.leonardo.monalisa.common.message.protobuf.PersonV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.pro01simple.sample.person.producer.PersonProducer;
import org.tnmk.pro01simple.sample.person.producer.PersonV2Producer;

@Service
public class Initiation {

    @Autowired
    private PersonProducer personProducer;
    @Autowired
    private PersonV2Producer personV2Producer;

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        Person person = Person.newBuilder()
            .setNickName("PersonV1_"+System.nanoTime())
            .setRealName("RealName_"+System.nanoTime())
            .build();
        personProducer.send(person);

        PersonV2 personV2 = PersonV2.newBuilder()
            .setNickName("PersonV2_"+System.nanoTime())
            .setFirstName("FirstName_"+System.nanoTime())
            .setLastName("LastName_"+System.nanoTime())
            .build();
        personV2Producer.send(personV2);
    }
}
