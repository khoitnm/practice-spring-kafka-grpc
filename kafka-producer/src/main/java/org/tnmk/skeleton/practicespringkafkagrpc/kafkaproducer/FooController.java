package org.tnmk.skeleton.practicespringkafkagrpc.kafkaproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.Foo;

@RestController
public class FooController {
    private static final Logger LOG = LoggerFactory.getLogger(FooController.class);

    @Autowired
    private FooSender fooSender;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public void sendRequest(){
        LOG.info("Send message: ");
        fooSender.send(new Foo("SAMPLE", "SAMPLE DESCRIPTION"));
    }
}
