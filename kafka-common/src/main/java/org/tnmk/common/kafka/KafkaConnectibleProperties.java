package org.tnmk.common.kafka;

public interface KafkaConnectibleProperties {
    String getBootstrapServers();

    //TODO We will need some more information about authentication here.
    // And when doing this, it will force all implementation to add the new fields, that's exactly what I want.
}
