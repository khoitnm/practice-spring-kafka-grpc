package org.tnmk.kafka.sampleapp.person.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Note: If the retry-attempt > 0, the error handler will be only executed after all retries are failed.
 *
 * @return
 * @see SeekToCurrentErrorHandler
 */
@Component
public class PersonManualAckListenerContainerErrorHandler implements ContainerAwareErrorHandler {
    public static Logger LOGGER = LoggerFactory.getLogger(PersonConsumerConfig.class);

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @Override
    public void handle(Exception thrownException, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer,
                MessageListenerContainer container){
        LOGGER.info("[LISTENER-CONTAINER-ERROR]"+records.toString());
        Set<TopicPartition> partitionSet = consumer.assignment();
        String positionOnPartitions = partitionSet.stream().map(partition -> "Partition: "+partition + ", position: " + consumer.position(partition)).collect(Collectors.joining("\n"));

        MessageListenerContainer listenerContainer = registry.getListenerContainer("personManualAckListener");
//        listenerContainer.pause();
    }
}
