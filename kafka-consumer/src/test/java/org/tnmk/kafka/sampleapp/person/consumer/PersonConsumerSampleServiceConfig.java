package org.tnmk.kafka.sampleapp.person.consumer;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.tnmk.kafka.sampleapp.person.consumer.usecases.PersonConsumerSampleService;

@Configuration
public class PersonConsumerSampleServiceConfig {

    @Primary
    @Bean
    public PersonConsumerSampleService personSampleService() {
        return Mockito.mock(PersonConsumerSampleService.class);
    }
}
