package org.tnmk.skeleton.practicespringkafkagrpc.kafkaproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tnmk.skeleton.practicespringkafkagrpc.kafkacommon.Foo;

@SpringBootApplication
public class KafkaProducerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApplication.class, args);
    }

    @Autowired
    private FooSender sender;

    @Override
    public void run(String... strings) throws Exception {
        Foo foo = new Foo("Spring Kafka", "sending and receiving JSON messages");
        sender.send(foo);
    }
}
