package org.tnmk.skeleton.practicespringkafkagrpc.kafkaproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.Foo;

@RestController
public class FooController {
    private static final Logger LOG = LoggerFactory.getLogger(FooController.class);

    @Autowired
    private FooProducerService fooProducerService;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public void sendRequest(){
        LOG.info("Send message: ");
        Foo foo = Foo.newBuilder().setName("Sample Name").setDescription("Sample Description").build();
        fooProducerService.send(foo);
    }
}
