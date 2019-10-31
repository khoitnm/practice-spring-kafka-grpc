package org.tnmk.kafka.sampleapp.person;

import org.tnmk.practicespringkafkagrpc.common.message.protobuf.Person;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.tnmk.kafka.sampleapp.person.consumer.usecases.PersonConsumerSampleService;
import org.tnmk.kafka.sampleapp.person.producer.PersonProducer;

public class PersonSuccessCaseTest extends BaseKafkaTest {
    @ClassRule
    public static final KafkaEmbedded EMBEDDED_KAFKA = new KafkaEmbedded(1, true, 1);
    private static final long TEST_CONSUMER_TIMEOUT = 1000;

    @Autowired
    private PersonProducer personProducer;

    @Autowired
    private PersonConsumerSampleService personConsumerSampleService;

    @Test
    public void testSendAndReceiveKafkaMessage_Success() {
        Person theGood = Person.newBuilder()
            .setRealName("The Man With No Name")
            .setNickName("The Blondie")
            .build();

        Person theBad = Person.newBuilder()
            .setRealName("")//This will cause Exception in Service.
            .setNickName("The Bad")
            .build();

        Person theUgly = Person.newBuilder()
            .setRealName("Tuco")
            .setNickName("The Ugly")
            .build();

        personProducer.send(theGood);
        personProducer.send(theBad);
        personProducer.send(theUgly);

        Mockito.verify(personConsumerSampleService, Mockito.timeout(TEST_CONSUMER_TIMEOUT).times(1)).autoAck(theGood);
        Mockito.verify(personConsumerSampleService, Mockito.timeout(TEST_CONSUMER_TIMEOUT).atLeastOnce()).autoAckError();
        Mockito.verify(personConsumerSampleService, Mockito.timeout(TEST_CONSUMER_TIMEOUT).times(1)).autoAck(theUgly);

        Mockito.verify(personConsumerSampleService, Mockito.timeout(TEST_CONSUMER_TIMEOUT).times(1)).manualAck(theGood);
        Mockito.verify(personConsumerSampleService, Mockito.timeout(TEST_CONSUMER_TIMEOUT).atLeastOnce()).autoAckError();
        Mockito.verify(personConsumerSampleService, Mockito.timeout(TEST_CONSUMER_TIMEOUT).times(1)).manualAck(theUgly);
    }
}
