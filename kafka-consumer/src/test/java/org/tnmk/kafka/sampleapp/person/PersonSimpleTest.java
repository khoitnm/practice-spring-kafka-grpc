package org.tnmk.kafka.sampleapp.person;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.kafka.sampleapp.PubSubTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PubSubTestApplication.class})
public class PersonSimpleTest {
}
