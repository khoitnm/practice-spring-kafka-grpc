package org.tnmk.skeleton.practicespringkafkagrpc.kafkaproducer;

import com.google.protobuf.GeneratedMessageV3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.Foo;

@Service
public class FooProducerService {

    private static final Logger LOG = LoggerFactory.getLogger(FooProducerService.class);

    @Autowired
    private KafkaTemplate<String, GeneratedMessageV3> kafkaTemplate;

    @Value("${app.topic.example}")
    private String topic;

    public void send(Foo data){
        LOG.info("sending data='{}' to topic='{}'", data, topic);

        kafkaTemplate.send(topic, data);
    }
}