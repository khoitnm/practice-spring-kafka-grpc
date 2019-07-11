package org.tnmk.kafka.sampleapp.person.producer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "kafka")
public class TopicsProperties {
    public static final String TOPIC_KEY_01 = "topic_01";
    public static final String TOPIC_KEY_02 = "topic_02";


    private Map<String, TopicProperties> topics;


    public Map<String, TopicProperties> getTopics() {
        return topics;
    }

    public void setTopics(Map<String, TopicProperties> topics) {
        this.topics = topics;
    }
}
