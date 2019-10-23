package org.tnmk.kafka.sampleapp.person.consumer.usecases;

import com.leonardo.monalisa.common.message.protobuf.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class PersonConsumerSampleServiceImpl implements PersonConsumerSampleService {
    /**
     * This is recommend by this: https://www.slf4j.org/faq.html#declared_static
     */
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void autoAck(Person person) {
        logger.info("autoAck: {}", person);
    }

    @Override
    public void manualAck(Person person) {
        logger.info("manualAck: {}", person);
    }

    @Override
    public void autoAckError() {
        logger.info("autoAckError");
    }

    @Override
    public void autoAckErrorAtOffset(long offset) {
        logger.info("autoAckErrorAtOffset: {}", offset);
    }

    @Override
    public void manualAckError() {
        logger.info("manualAckError");
    }

    @Override
    public void manualAckErrorAtOffset(long offset) {
        logger.info("manualAckErrorAtOffset: {}", offset);
    }
}
