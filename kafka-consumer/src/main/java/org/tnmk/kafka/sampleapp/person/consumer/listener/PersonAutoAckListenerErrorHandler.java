package org.tnmk.kafka.sampleapp.person.consumer.listener;

import org.apache.kafka.clients.consumer.Consumer;
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


/**
 * Note: If the retry-attempt > 0, the error handler will be only executed after all retries are failed.
 *
 * @return
 * @see SeekToCurrentErrorHandler
 */
@Component("personErrorHandler")
public class PersonAutoAckListenerErrorHandler implements ConsumerAwareListenerErrorHandler {
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
        LOGGER.error("Error in the consumer." +
                "\n\tConsumer Assignment: {}"+
                "\n\tData: {}." +
                "\n\tException: {}",consumer.assignment(), message, exception.getMessage(), exception);
        personConsumerSampleService.autoAckError();
        personConsumerSampleService.autoAckErrorAtOffset(offset);
        return null;
    }
}
