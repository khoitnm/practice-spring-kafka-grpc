package org.tnmk.kafka.sampleapp.person.producer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "kafka")
public class TopicsProperties {
    private Map<String, TopicProperties> topics;


    public Map<String, TopicProperties> getTopics() {
        return topics;
    }

    public void setTopics(Map<String, TopicProperties> topics) {
        this.topics = topics;
    }
}
