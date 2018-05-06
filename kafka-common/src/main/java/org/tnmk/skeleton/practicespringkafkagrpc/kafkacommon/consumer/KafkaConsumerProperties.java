package org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.consumer;

/**
 * The properties inside here could be reused for different topics, consumer groups and messageType.
 * So there will be no specific topics and consumers information here.
 */
public class KafkaConsumerProperties {
    private String bootstrapServers;
    //TODO we will be some more information about authentication here.

    private String autoOffsetReset = "earliest";

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
}
