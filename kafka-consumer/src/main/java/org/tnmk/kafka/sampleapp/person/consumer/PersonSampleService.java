package org.tnmk.kafka.sampleapp.person.consumer;


import com.leonardo.monalisa.common.message.protobuf.Person;

/**
 * In this consumer package, there's no implementation class for this interface because it will be mocked.
 */
public interface PersonSampleService {
    void autoAck(Person person);

    void manualAck(Person person);

    void autoAckError();

    void autoAckErrorAtOffset(long offset);

    void manualAckError();

    void manualAckErrorAtOffset(long offset);
}
