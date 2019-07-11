package org.tnmk.kafka.sampleapp.person.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicsConfig {
//    @Bean
//    public KafkaAdmin admin() {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
//                StringUtils.arrayToCommaDelimitedString(embeddedKafka().getBrokerAddresses()));
//        return new KafkaAdmin(configs);
//    }

    @Autowired
    TopicsProperties topicsProperties;

    @Bean
    public NewTopic topic1(TopicsProperties topicsProperties) {
        String topicName = "topic_01";
        TopicProperties topicProperties = topicsProperties.getTopics().get(topicName);
        return new NewTopic(topicName, topicProperties.getNumPartitions(), topicProperties.getReplicationFactor());
    }

    @Bean
    public NewTopic topic2(TopicsProperties topicsProperties) {
        String topicName = "topic_02";
        TopicProperties topicProperties = topicsProperties.getTopics().get(topicName);
        return new NewTopic(topicName, topicProperties.getNumPartitions(), topicProperties.getReplicationFactor());
    }


}
