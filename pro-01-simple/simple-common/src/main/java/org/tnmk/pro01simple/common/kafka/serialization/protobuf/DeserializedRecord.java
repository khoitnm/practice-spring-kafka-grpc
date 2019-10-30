package org.tnmk.pro01simple.common.kafka.serialization.protobuf;

import org.apache.kafka.common.header.Headers;

/**
 * At this moment, Spring Kafka can help us to get message headers when success.
 * But when there's error (e.g. Deserialization is corrupted), so it would be helpful to return the failed record with headers and exception.
 *
 * @Note We may not need this class to prevent endless loop in consumer because the {@link com.leonardo.monalisa.common.kafka.consumer.KafkaGlobalContainerErrorHandler} will be automatically registered into KafkaListenerContainer (view more in the code {@link com.leonardo.monalisa.common.kafka.consumer.KafkaListenerContainerFactoryConstructor}).
 * However, I think it will give use more convenient way to handle CorruptedDeserialization.
 * @param <T>
 */
public class DeserializedRecord<T> {
    private byte[] originalBytes;
    /**
     * The converted data.
     */
    private T data;
    /**
     * The exception when doing deserialization (if exist).
     */
    private Throwable exception;

    public byte[] getOriginalBytes() {
        return originalBytes;
    }

    public void setOriginalBytes(byte[] originalBytes) {
        this.originalBytes = originalBytes;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }
}
