package org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.consumer;

import com.google.protobuf.GeneratedMessageV3;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.serialization.protobuf.ProtobufDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is actually a Factory of the {@link KafkaListenerContainerFactory}
 * But either the name ConsumerContainerFactoryFactory or KafkaListenerContainerFactoryFactory would be very confusing, so I use the name ConsumerContainerFactoryConstructor.
 * @param <T> All the Kafka messages will extends from {@link GeneratedMessageV3}.
 */
public class KafkaConsumerContainerFactoryConstructor<T extends GeneratedMessageV3> {
    private KafkaConsumerProperties kafkaConsumerProperties;

    public KafkaConsumerContainerFactoryConstructor(KafkaConsumerProperties kafkaConsumerProperties) {
        this.kafkaConsumerProperties = kafkaConsumerProperties;
    }



    public ConcurrentKafkaListenerContainerFactory<String, T> createProtobufConcurrentConsumerContainerFactory(Class<T> messagePayloadType) {
        ConcurrentKafkaListenerContainerFactory<String, T> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        ConsumerFactory<String, T> consumerFactory = createProtobufConsumerFactory(kafkaConsumerProperties, messagePayloadType);

        containerFactory.setConsumerFactory(consumerFactory);

        containerFactory.setAutoStartup(kafkaConsumerProperties.isAutoStartup());
        containerFactory.getContainerProperties().setAckMode(kafkaConsumerProperties.getAckMode());

        applyRetryConfiguration(containerFactory, kafkaConsumerProperties);
        return containerFactory;
    }

    public void applyErrorHandler(ConcurrentKafkaListenerContainerFactory<String, T> listenerContainerFactory, ErrorHandler errorHandler) {
        if (errorHandler != null) {
            listenerContainerFactory.getContainerProperties().setErrorHandler(errorHandler);
        }
    }

    public <R> void applyRecoveryCallback(ConcurrentKafkaListenerContainerFactory<String, T> listenerContainerFactory, RecoveryCallback<R> recoveryCallback) {
        if (recoveryCallback != null) {
            listenerContainerFactory.setRecoveryCallback(recoveryCallback);
        }
    }

    private Map<String, Object> createConsumerConfigs(KafkaConsumerProperties kafkaProperties) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.getAutoOffsetReset());

        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProperties.isAutoCommit());
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaProperties.getRequestTimeout());
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, kafkaProperties.getSessionTimeout());

        return props;
    }

    private ConsumerFactory<String, T> createProtobufConsumerFactory(KafkaConsumerProperties kafkaProperties, Class<T> messagePayloadType) {
        return new DefaultKafkaConsumerFactory<>(createConsumerConfigs(kafkaProperties), new StringDeserializer(), new ProtobufDeserializer<>(messagePayloadType));
    }

    private void applyRetryConfiguration(ConcurrentKafkaListenerContainerFactory<?, ?> factory, KafkaConsumerProperties kafkaProperties) {
        // Overriding retry template is crucial. If not defined and manual message acknowledgement is enabled, then Spring will keep retrying the same message over and over again.
        // This behaviour will take up 100% CPU cycle and spam the logger.
        RetryTemplate retryTemplate = new RetryTemplate();
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();

        retryPolicy.setMaxAttempts(kafkaProperties.getRetryAttempt());
        retryTemplate.setRetryPolicy(retryPolicy);

        backOffPolicy.setBackOffPeriod(kafkaProperties.getRetryBackoffPeriod());
        retryTemplate.setBackOffPolicy(backOffPolicy);

        factory.setRetryTemplate(retryTemplate);
    }

}
