package org.tnmk.kafka.sampleapp.person.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * View more at https://docs.spring.io/spring-kafka/reference/html/#configuring-topics
 * Note from above document:
 * <pre>
 *     	If the broker supports it (1.0.0 or higher), the admin increases the number of partitions if it is found that an existing topic has fewer partitions than the NewTopic.numPartitions.
 * </pre>
 */
@Configuration
public class TopicsConfig {

    @Autowired
    TopicsProperties topicsProperties;


    @Bean
    public NewTopic topic1(TopicsProperties topicsProperties) {
        TopicProperties topicProperties = topicsProperties.getTopics().get(TopicsProperties.TOPIC_KEY_01);
        return new NewTopic(topicProperties.getName(), topicProperties.getNumPartitions(), topicProperties.getReplicationFactor());
    }

    @Bean
    public NewTopic topic2(TopicsProperties topicsProperties) {
        TopicProperties topicProperties = topicsProperties.getTopics().get(TopicsProperties.TOPIC_KEY_02);
        return new NewTopic(topicProperties.getName(), topicProperties.getNumPartitions(), topicProperties.getReplicationFactor());
    }


}
