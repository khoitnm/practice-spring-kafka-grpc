package org.tnmk.common.kafka.producer;

import org.tnmk.common.kafka.KafkaConnectibleProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka-global-producer")
public class KafkaGlobalProducerProperties implements KafkaConnectibleProperties {
    private String bootstrapServers;

    @Override
    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }
}
