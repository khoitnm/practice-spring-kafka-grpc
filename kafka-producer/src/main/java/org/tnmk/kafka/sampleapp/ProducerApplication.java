package org.tnmk.kafka.sampleapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot application to test Kafka integration.
 */
@SpringBootApplication
public class ProducerApplication {
    @SuppressWarnings("resource")
    public static void main(final String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }
}
