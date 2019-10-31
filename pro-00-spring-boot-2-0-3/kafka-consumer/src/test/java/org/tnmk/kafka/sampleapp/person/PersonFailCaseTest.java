package org.tnmk.kafka.sampleapp.person;

import org.tnmk.practicespringkafkagrpc.common.message.protobuf.Person;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.common.kafka.producer.KafkaGlobalProducerProperties;
import org.tnmk.kafka.sampleapp.person.consumer.usecases.PersonConsumerSampleService;
import org.tnmk.kafka.sampleapp.person.producer.PersonProducer;

public class PersonFailCaseTest extends BaseKafkaTest {
    private static final long TEST_CONSUMER_TIMEOUT = 1000;

    @Autowired
    private PersonProducer personProducer;

    @Autowired
    private PersonConsumerSampleService personConsumerSampleService;

    @Test
    public void testSendAndReceiveKafkaMessage_Fail() {
        Person theBad = Person.newBuilder()
            .setRealName("")//This will cause Exception in Service.
            .setNickName("The Bad")
            .build();

        personProducer.send(theBad);

        Mockito.verify(personConsumerSampleService, Mockito.timeout(TEST_CONSUMER_TIMEOUT).atLeastOnce()).autoAckError();
        Mockito.verify(personConsumerSampleService, Mockito.timeout(TEST_CONSUMER_TIMEOUT).atLeastOnce()).autoAckError();
    }
}
