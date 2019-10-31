package org.tnmk.pro02consumemultipleversions.sample.person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot application to test Kafka integration.
 */
@SpringBootApplication
public class Pro02ConsumeMultipleVersionsConsumerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(Pro02ConsumeMultipleVersionsConsumerApplication.class, args);
    }
}
