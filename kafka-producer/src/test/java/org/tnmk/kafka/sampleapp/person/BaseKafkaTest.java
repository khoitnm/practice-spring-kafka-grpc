package org.tnmk.kafka.sampleapp.person;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.kafka.sampleapp.ProducerApplication;

@Ignore
@ActiveProfiles("pubsubtest")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProducerApplication.class})
public class BaseKafkaTest {

    /**
     * This Kafka instance will be shared for all test cases.
     * It means that the Embedded Kafka will be start before all test cases, and closed after all test cases are finished.
     * <p/>
     * If you want the Kafka instance is started and closed for each Test Class, please use {@link org.junit.ClassRule} and don't need to call {@link KafkaEmbedded#afterPropertiesSet()}.
     */
    //    @ClassRule
    public static final KafkaEmbedded EMBEDDED_KAFKA = new KafkaEmbedded(1, true, 1);
    static {
        // You can use EMBEDDED_KAFKA.setKafkaPorts(9099) if you want a fixed port.
//        finishStartingEmbeddedKafka();
    }

    /**
     * If you use {@link org.junit.ClassRule} on {@link #EMBEDDED_KAFKA}, you don't need to call this method. Spring will automatically complete starting Kafka for you.
     * Behind the scene, Spring will start the embedded Kafka and then set broker server address into the property {@link KafkaEmbedded#SPRING_EMBEDDED_KAFKA_BROKERS}.
     * That will help Producers and Consumers to be able to connect to Kafka via that broker server address (please view more in application.yml to understand more.
     */
    private static void finishStartingEmbeddedKafka() {
        try {
            EMBEDDED_KAFKA.afterPropertiesSet();
        } catch (Exception e) {
            throw new UnexpectedKafkaException(e);
        }
    }

    private static class UnexpectedKafkaException extends RuntimeException {
        public UnexpectedKafkaException(Throwable e) {
            super(e);
        }
    }

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    /**
     * In my personal observation, we don't need this method and the test still run just fine.
     * @throws Exception
     */
    @Before
    public void waitForListeners() throws Exception {
        // Wait until the Partitions in EmbeddedKafka are assigned to ListenerContainers.
        for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry.getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(messageListenerContainer, EMBEDDED_KAFKA.getPartitionsPerTopic());
        }
    }
}
