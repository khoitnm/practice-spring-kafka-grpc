package org.tnmk.kafka.sampleapp.person.consumer.usecases;


import org.tnmk.practicespringkafkagrpc.common.message.protobuf.Person;

/**
 * In this consumer package, there's no implementation class for this interface because it will be mocked.
 */
public interface PersonConsumerSampleService {
    void autoAck(Person person);

    void manualAck(Person person);

    void autoAckError();

    void autoAckErrorAtOffset(long offset);

    void manualAckError();

    void manualAckErrorAtOffset(long offset);
}
