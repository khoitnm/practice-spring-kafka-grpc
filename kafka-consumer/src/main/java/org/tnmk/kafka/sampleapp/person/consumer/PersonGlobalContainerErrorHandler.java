package org.tnmk.kafka.sampleapp.person.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Note: If the retry-attempt > 0, the error handler will be only executed after all retries are failed.
 *
 * @return
 * @see SeekToCurrentErrorHandler
 */
@Component
public class PersonGlobalContainerErrorHandler implements ContainerAwareErrorHandler {
    public static Logger LOGGER = LoggerFactory.getLogger(PersonConsumerConfig.class);

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @Override
    public void handle(Exception thrownException, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer,
                       MessageListenerContainer container) {
        logError(thrownException, records, consumer, container);
        //Option 1: We pause it. And then when changing some code and restarting the Consumer, it will replay the error record (message).
//        container.pause();

        //Option 2: acknowledge, store log and ignore the error message.
        Set<TopicPartition> assignment = consumer.assignment();
        assignment.forEach(topicPartition -> {
            long errorRecordOffset = consumer.position(topicPartition);
            long nextOffset = errorRecordOffset + 1;
            //TODO Handle the error Record.
            LOGGER.info("[LISTENER-CONTAINER-ERROR] Advance to next offset in partition: topic: {}, partition: {}, errorOffset: {}, nextOffset: {}",
                    topicPartition.topic(), topicPartition.partition(), errorRecordOffset, nextOffset);
            consumer.seek(topicPartition, nextOffset);//Advance to the next record.
        });
        throw new RuntimeException(thrownException.getMessage(), thrownException);
    }

    private void logError(Exception thrownException, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer, MessageListenerContainer container) {
        Set<TopicPartition> partitionSet = consumer.assignment();

        LOGGER.error("[LISTENER-CONTAINER-ERROR]\n\t Ending Positions: {}," +
                "\n\t metrics: {}" +
                "\n\t records: {}." +
                "\n\t Exception Message (more detail in stack trace): {}", consumer.endOffsets(partitionSet), container.metrics(), records.toString(), thrownException.getMessage(), thrownException);
    }
}
