package org.tnmk.pro01simple.sample.person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot application to test Kafka integration.
 */
@SpringBootApplication
public class Pro01SimpleConsumerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(Pro01SimpleConsumerApplication.class, args);
    }
}
