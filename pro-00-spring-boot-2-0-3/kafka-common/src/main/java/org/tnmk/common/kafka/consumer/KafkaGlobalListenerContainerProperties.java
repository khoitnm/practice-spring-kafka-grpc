package org.tnmk.common.kafka.consumer;

import org.tnmk.common.kafka.KafkaConnectibleProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * This class provides common properties for all ListenerContainer
 */
@Configuration
@ConfigurationProperties(prefix = "kafka-global-consumer")
public class KafkaGlobalListenerContainerProperties implements KafkaConnectibleProperties {
    private String bootstrapServers;

    @Override
    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }
}
