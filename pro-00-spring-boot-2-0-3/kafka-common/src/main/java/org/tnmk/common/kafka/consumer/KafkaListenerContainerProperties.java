package org.tnmk.common.kafka.consumer;

import org.tnmk.common.kafka.KafkaConnectibleProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * This is the Properties for each Consumer.
 * The global Properties for all consumers is configured in {@link KafkaGlobalListenerContainerProperties}
 *
 * The properties inside here could be reused for different topics, consumer groups and messageType.
 * So there will be no specific topics and consumers information here.
 * This file provides a convenient mapping with properties configuration in *.yml file.
 */
public class KafkaListenerContainerProperties implements KafkaConnectibleProperties {
    @Autowired
    private KafkaGlobalListenerContainerProperties kafkaGlobalListenerContainerProperties;

    private String bootstrapServers;

    private String initialOffset = "earliest";

    /**
     * What to do when there is no initial offset in Kafka or if the current offset does not exist any more on the server (e.g. because that data has been deleted): <ul><li>earliest: automatically reset the offset to the earliest offset<li>latest: automatically reset the offset to the latest offset</li><li>none: throw exception to the consumer if no previous offset is found for the consumer's group</li><li>anything else: throw exception to the consumer.</li></ul>
     * View more at {@link org.apache.kafka.clients.consumer.OffsetResetStrategy}
     */
    private String autoOffsetReset = "earliest";

    /**
     * If true the consumer's offset will be periodically committed in the background.
     * Several options are provided for committing offsets.
     * If the enable.auto.commit consumer property is true, kafka will auto-commit the offsets according to its configuration.
     * If it is false, the containers support the following AckMode s.
     */
    private boolean autoCommit = false;

    /**
     * View more at {@link ConsumerConfig#AUTO_COMMIT_INTERVAL_MS_CONFIG}
     */
    private int autoCommitInterval = 1000;

    /**
     * View more at {@link ContainerProperties#setAckOnError(boolean)}
     */
    private boolean ackOnError = true;

    /**
     * Auto startup the listener.
     * Should default this to false. Otherwise the consumer will start before the application is full initialized???
     */
    private boolean autoStartup = false;

    /**
     * We are using the default value of {@link ContainerProperties#ackMode}
     */
    private AbstractMessageListenerContainer.AckMode ackMode = AbstractMessageListenerContainer.AckMode.BATCH;

    /**
     * The configuration controls the maximum amount of time the client will wait for the response of a request.
     * If the response is not received before the timeout elapses the client will resend the request if necessary or fail the request if retries are exhausted.
     */
    private int requestTimeout = 60000;

    /**
     * The timeout used to detect consumer failures when using Kafka's group management facility.
     * The consumer sends periodic heartbeats to indicate its liveness to the broker.
     * If no heartbeats are received by the broker before the expiration of this session timeout, then the broker will remove this consumer from the group and initiate a rebalance.
     * Note that the value must be in the allowable range as configured in the broker configuration by <code>group.min.session.timeout.ms</code> and <code>group.max.session.timeout.ms</code>.
     */
    private int sessionTimeout = 30000;

    /**
     * The total number retrying before giving up.
     * View more at {@link RetryTemplate}.
     */
    private int retryAttempt = 5;

    /**
     * This is the period between retrying.
     * View more at {@link FixedBackOffPolicy}.
     */
    private long retryBackoffPeriod = 3000L;

    @Override
    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getAutoOffsetReset() {
        return autoOffsetReset;
    }

    public void setAutoOffsetReset(String autoOffsetReset) {
        this.autoOffsetReset = autoOffsetReset;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public boolean isAutoStartup() {
        return autoStartup;
    }

    public void setAutoStartup(boolean autoStartup) {
        this.autoStartup = autoStartup;
    }

    public AbstractMessageListenerContainer.AckMode getAckMode() {
        return ackMode;
    }

    public void setAckMode(AbstractMessageListenerContainer.AckMode ackMode) {
        this.ackMode = ackMode;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public int getRetryAttempt() {
        return retryAttempt;
    }

    public void setRetryAttempt(int retryAttempt) {
        this.retryAttempt = retryAttempt;
    }

    public long getRetryBackoffPeriod() {
        return retryBackoffPeriod;
    }

    public void setRetryBackoffPeriod(long retryBackoffPeriod) {
        this.retryBackoffPeriod = retryBackoffPeriod;
    }

    public String getInitialOffset() {
        return initialOffset;
    }

    public void setInitialOffset(String initialOffset) {
        this.initialOffset = initialOffset;
    }

    public KafkaGlobalListenerContainerProperties getKafkaGlobalListenerContainerProperties() {
        return kafkaGlobalListenerContainerProperties;
    }

    public int getAutoCommitInterval() {
        return autoCommitInterval;
    }

    public void setAutoCommitInterval(int autoCommitInterval) {
        this.autoCommitInterval = autoCommitInterval;
    }

    public boolean isAckOnError() {
        return ackOnError;
    }

    public void setAckOnError(boolean ackOnError) {
        this.ackOnError = ackOnError;
    }
}
