package org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.serialization;

/**
 * Exception thrown when broker message failed de/serialization.
 */
public class SerializationException extends RuntimeException {
    public SerializationException() {
    }

    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializationException(Throwable cause) {
        super(cause);
    }
}
