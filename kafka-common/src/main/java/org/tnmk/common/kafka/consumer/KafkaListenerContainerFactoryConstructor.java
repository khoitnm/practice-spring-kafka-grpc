package org.tnmk.common.kafka.consumer;

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
import org.springframework.util.StringUtils;
import org.tnmk.common.kafka.KafkaConnectibleProperties;
import org.tnmk.common.kafka.serialization.protobuf.DeserializedRecord;
import org.tnmk.common.kafka.serialization.protobuf.ProtobufDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is actually a Factory of the {@link KafkaListenerContainerFactory}
 * But either the name ConsumerContainerFactoryFactory or KafkaListenerContainerFactoryFactory would be very confusing, so I use the name ConsumerContainerFactoryConstructor.
 * Please view the UnitTest to see how to use this class.
 *
 * @param <T> All the Kafka messages will extends from {@link GeneratedMessageV3}.
 */
public class KafkaListenerContainerFactoryConstructor<T extends GeneratedMessageV3> {
    private KafkaListenerContainerProperties kafkaListenerContainerProperties;

    public KafkaListenerContainerFactoryConstructor(KafkaListenerContainerProperties kafkaListenerContainerProperties) {
        this.kafkaListenerContainerProperties = kafkaListenerContainerProperties;
    }


    public ConcurrentKafkaListenerContainerFactory<String, DeserializedRecord<T>> createProtobufConcurrentConsumerContainerFactory(Class<T> messagePayloadType) {
        ConcurrentKafkaListenerContainerFactory<String, DeserializedRecord<T>> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        ConsumerFactory<String, DeserializedRecord<T>> consumerFactory = createProtobufConsumerFactory(kafkaListenerContainerProperties, messagePayloadType);

        containerFactory.setConsumerFactory(consumerFactory);
        containerFactory.setAutoStartup(kafkaListenerContainerProperties.isAutoStartup());
        containerFactory.getContainerProperties().setAckMode(kafkaListenerContainerProperties.getAckMode());
        containerFactory.getContainerProperties().setAckOnError(kafkaListenerContainerProperties.isAckOnError());
        //We set it as a default filter strategy. Client code is always able to replace it by another filter strategy if necessary.
        containerFactory.setRecordFilterStrategy(new KafkaIgnoreErrorRecordFilterStrategy<>());

        applyRetryConfiguration(containerFactory, kafkaListenerContainerProperties);

        //Always need a default wrapper to prevent endless loop when there's error in Deserialization.
        containerFactory.getContainerProperties().setErrorHandler(new KafkaGlobalContainerErrorHandler());
        return containerFactory;
    }

    public void applyErrorHandler(ConcurrentKafkaListenerContainerFactory<String, T> listenerContainerFactory, ErrorHandler errorHandler) {
        if (errorHandler != null) {
            //Always need a default wrapper to prevent endless loop when there's error in Deserialization.
            listenerContainerFactory.getContainerProperties().setErrorHandler(new KafkaGlobalContainerErrorHandler(errorHandler));
        }
    }

    public <R> void applyRecoveryCallback(ConcurrentKafkaListenerContainerFactory<String, T> listenerContainerFactory, RecoveryCallback<R> recoveryCallback) {
        if (recoveryCallback != null) {
            listenerContainerFactory.setRecoveryCallback(recoveryCallback);
        }
    }

    private Map<String, Object> createConsumerConfigs(KafkaListenerContainerProperties kafkaProperties) {
        Map<String, Object> props = new HashMap<>();
        KafkaConnectibleProperties kafkaConnectibleProperties = !StringUtils.isEmpty(kafkaProperties.getBootstrapServers()) ?
                kafkaProperties :
                kafkaProperties.getKafkaGlobalListenerContainerProperties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConnectibleProperties.getBootstrapServers());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.getAutoOffsetReset());

        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProperties.isAutoCommit());
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, kafkaProperties.getAutoCommitInterval());

        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaProperties.getRequestTimeout());
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, kafkaProperties.getSessionTimeout());

        return props;
    }

    private ConsumerFactory<String, DeserializedRecord<T>> createProtobufConsumerFactory(KafkaListenerContainerProperties kafkaProperties, Class<T> messagePayloadType) {
        return new DefaultKafkaConsumerFactory<>(
                createConsumerConfigs(kafkaProperties),
                new StringDeserializer(),
                new ProtobufDeserializer<>(messagePayloadType)
        );
    }

    /**
     * Overriding retry template is crucial. If not defined and manual message acknowledgement is enabled, then Spring will keep retrying the same message over and over again.
     * This behaviour will take up 100% CPU cycle and spam the logger.
     *
     * @param factory
     * @param kafkaProperties
     */
    private void applyRetryConfiguration(ConcurrentKafkaListenerContainerFactory<?, ?> factory, KafkaListenerContainerProperties kafkaProperties) {
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