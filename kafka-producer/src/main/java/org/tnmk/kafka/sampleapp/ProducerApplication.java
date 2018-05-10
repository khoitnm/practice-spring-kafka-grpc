package org.tnmk.kafka.sampleapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot application to test Kafka integration.
 */
@SpringBootApplication
public class ProducerApplication {

    /**
     * This method just to show you an example how to do it in a real application. In this testing context, it do nothing!!!
     * @param args
     */
    @SuppressWarnings("resource")
    public static void main(final String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }
}
