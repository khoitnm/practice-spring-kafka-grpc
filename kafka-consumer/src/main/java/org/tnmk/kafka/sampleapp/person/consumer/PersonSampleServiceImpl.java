package org.tnmk.kafka.sampleapp.person.consumer;

import com.leonardo.monalisa.common.message.protobuf.Person;
import org.springframework.stereotype.Service;

@Service
public class PersonSampleServiceImpl implements PersonSampleService {
    @Override
    public void autoAck(Person person) {

    }

    @Override
    public void manualAck(Person person) {

    }

    @Override
    public void autoAckError() {

    }

    @Override
    public void autoAckErrorAtOffset(long offset) {

    }

    @Override
    public void manualAckError() {

    }

    @Override
    public void manualAckErrorAtOffset(long offset) {

    }
}
