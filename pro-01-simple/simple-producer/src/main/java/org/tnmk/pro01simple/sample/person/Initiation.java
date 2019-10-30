package org.tnmk.pro01simple.sample.person;

import com.leonardo.monalisa.common.message.protobuf.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.pro01simple.sample.person.producer.PersonProducer;

@Service
public class Initiation {

    @Autowired
    private PersonProducer personProducer;

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        Person person = Person.newBuilder()
            .setNickName("Nick_"+System.nanoTime())
            .setRealName("RealName_"+System.nanoTime())
            .build();
        personProducer.send(person);
    }
}
