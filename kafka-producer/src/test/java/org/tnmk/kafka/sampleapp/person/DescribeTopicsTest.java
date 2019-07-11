package org.tnmk.kafka.sampleapp.person;

import org.apache.kafka.common.PartitionInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.kafka.sampleapp.ProducerApplication;
import org.tnmk.kafka.sampleapp.person.producer.TopicProperties;
import org.tnmk.kafka.sampleapp.person.producer.TopicsProperties;

import java.util.List;

@ActiveProfiles("pubsubtest")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProducerApplication.class})
public class DescribeTopicsTest {
    @Autowired
    private TopicsProperties topicsProperties;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    public void testSendAndReceiveKafkaMessage_Fail() {
        TopicProperties topicProperties = topicsProperties.getTopics().get(TopicsProperties.TOPIC_KEY_01);
        String topic01 = topicProperties.getName();
        List<PartitionInfo> partitionInfoList = kafkaTemplate.partitionsFor(topic01);
        Assert.assertEquals(topicProperties.getNumPartitions(), partitionInfoList.size());
    }
}
