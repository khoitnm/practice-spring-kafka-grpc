package org.tnmk.kafka.sampleapp.person.producer;

import org.tnmk.practicespringkafkagrpc.common.message.protobuf.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {
    private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonProducerService personProducerService;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public void sendRequest(@RequestParam("realName") String realName){
        LOG.info("Send message: ");
        Person foo = Person.newBuilder().setRealName(realName).setNickName("Sample Description").build();
        personProducerService.send(foo);
    }
}
