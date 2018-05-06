package org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.producer;

public class KafkaProducerProperties {
    private String bootstrapServers;
    //TODO we will be some more information about authentication here.

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }
}
