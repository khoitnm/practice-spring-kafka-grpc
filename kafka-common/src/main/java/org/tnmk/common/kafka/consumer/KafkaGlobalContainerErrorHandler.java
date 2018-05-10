package org.tnmk.common.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.ContainerAwareErrorHandler;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Note: If the retry-attempt > 1, the error handler will be only executed after all retries are failed.
 * This ListenerContainerErrorHandler can handle the exception inside {@link org.apache.kafka.common.serialization.Deserializer}.
 * If we con't handle that case, it could cause Endless Loop in the Consumer.
 *
 * You can register to the listener by using {@link KafkaListenerContainerFactoryConstructor#applyErrorHandler(ConcurrentKafkaListenerContainerFactory, ErrorHandler)}.
 * @return
 * @see SeekToCurrentErrorHandler
 */
public class KafkaGlobalContainerErrorHandler implements ContainerAwareErrorHandler {
    public static Logger LOGGER = LoggerFactory.getLogger(KafkaGlobalContainerErrorHandler.class);


    private ErrorHandler nestedErrorHandler;

    public KafkaGlobalContainerErrorHandler(){
        //Default constructor
    }
    public KafkaGlobalContainerErrorHandler(ErrorHandler nestedErrorHandler) {
        this.nestedErrorHandler = nestedErrorHandler;
    }

    @Override
    public void handle(Exception thrownException, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer,
                       MessageListenerContainer container) {
        logError(thrownException, records, consumer, container);
        //Option 1: We pause it. And then when changing some code and restarting the Consumer, it will replay the error record (message).
//        container.pause();

        //Option 2: log the error offset, store it somewhere, and advance to next record in partitions.
        advanceToNextRecord(consumer);

        if (nestedErrorHandler != null){
            nestedErrorHandler.handle(thrownException, records, consumer, container);
        }
    }

    /**
     * Advance the next record but don't acknowledge it.
     * If you restart the consumer when there's no newer acknowledged records, it could be replayed if the ack-on-error is false or the ack-mode is MANUAL).
     * @param consumer
     */
    private void advanceToNextRecord(Consumer consumer){
        Set<TopicPartition> assignment = consumer.assignment();
        // Each consumer only handles one partition for each topic, so it looks OK.
        // We may not need to worry about handling records in non-error partitions.
        assignment.forEach(topicPartition -> {
            advanceToNextRecord(consumer, topicPartition);
        });
    }

    private void advanceToNextRecord(Consumer consumer, TopicPartition topicPartition){
        long errorRecordOffset = consumer.position(topicPartition);
        long nextOffset = errorRecordOffset + 1;
        //TODO Handle the error Record. Maybe we want to store it in some ResultReport?
        LOGGER.info("[LISTENER-CONTAINER-ERROR] Advance to next offset in partition: topic: {}, partition: {}, errorOffset: {}, nextOffset: {}",
                topicPartition.topic(), topicPartition.partition(), errorRecordOffset, nextOffset);
        consumer.seek(topicPartition, nextOffset);
    }

    private void logError(Exception thrownException, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer, MessageListenerContainer container) {
        Set<TopicPartition> partitionSet = consumer.assignment();
        String positionOnPartitions = partitionSet.stream().map(partition -> "{" + partition + ": " + consumer.position(partition) + "}").collect(Collectors.joining(", "));

        LOGGER.error("[LISTENER-CONTAINER-ERROR]\n\t Ending Positions: {}," +
                "\n\t records: {}." +
                "\n\t Exception Message (more detail in stack trace): {}", positionOnPartitions, records.toString(), thrownException.getMessage(), thrownException);
    }

    public ErrorHandler getNestedErrorHandler() {
        return nestedErrorHandler;
    }

    public void setNestedErrorHandler(ErrorHandler nestedErrorHandler) {
        this.nestedErrorHandler = nestedErrorHandler;
    }

}
