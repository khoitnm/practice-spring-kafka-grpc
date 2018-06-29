package org.tnmk.kafka.sampleapp.person.consumer.listener;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.tnmk.kafka.sampleapp.person.consumer.PersonConsumerConfig;
import org.tnmk.kafka.sampleapp.person.consumer.usecases.PersonConsumerSampleService;

import java.util.Set;
import java.util.stream.Collectors;


/**
 * Note: If the retry-attempt > 0, the error handler will be only executed after all retries are failed.
 *
 * @return
 * @see SeekToCurrentErrorHandler
 */
@Component("personManualAckListenerErrorHandler")
public class PersonManualAckListenerErrorHandler implements ConsumerAwareListenerErrorHandler {
    public static Logger LOGGER = LoggerFactory.getLogger(PersonConsumerConfig.class);

    @Autowired
    private PersonConsumerSampleService personConsumerSampleService;

    /**
     * @see KafkaListenerErrorHandler
     * @param message
     * @param exception
     * @param consumer
     * @return The return is ignore if there no @SendTo configuration. (see more at {@link KafkaListenerErrorHandler}
     */
    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
        Long offset = (Long)message.getHeaders().get(KafkaHeaders.OFFSET);
        message.getHeaders().keySet().forEach(key -> {
            LOGGER.info("{}: {}", key, message.getHeaders().get(key));
        });
        Set<TopicPartition> partitionSet = consumer.assignment();
        String positionOnPartitions = partitionSet.stream().map(partition -> "Partition: "+partition + ", position: " + consumer.position(partition)).collect(Collectors.joining("\n"));
        LOGGER.error("Error in the consumer." +
                "\n\tPositions: {}"+
                "\n\tConsumer Assignment: {}"+
                "\n\tData: {}." +
                "\n\tException: {}",
                positionOnPartitions,consumer.assignment(), message, exception.getMessage(), exception);
        personConsumerSampleService.manualAckErrorAtOffset(offset);
        personConsumerSampleService.manualAckError();
        return null;
    }
}
