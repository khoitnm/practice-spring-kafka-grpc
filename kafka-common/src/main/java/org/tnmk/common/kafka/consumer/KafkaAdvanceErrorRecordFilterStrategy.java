package org.tnmk.common.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.tnmk.common.kafka.serialization.protobuf.DeserializedRecord;

/**
 * This is the default behavior: filter out all records with exception.
 * They will be ignore and the consumer will continue consume next records.
 * However, ignored error records are not committed.
 * It means that the next time the consumer restarted, it could replay those error records (if the committed offset still lower than the error offset).
 * @param <K>
 * @param <V>
 */
public class KafkaAdvanceErrorRecordFilterStrategy<K, V> implements RecordFilterStrategy<K, DeserializedRecord<V>> {
    public static final Logger LOGGER = LoggerFactory.getLogger(KafkaAdvanceErrorRecordFilterStrategy.class);

    @Override
    public boolean filter(ConsumerRecord<K, DeserializedRecord<V>> consumerRecord) {
        DeserializedRecord<V> deserializedRecord = consumerRecord.value();
        if (deserializedRecord.getException() != null) {
            LOGGER.info("The message is error, so it will not be sent to the consumer. Message: {}", consumerRecord);
            return true;
        } else {
            return false;
        }
    }
}
