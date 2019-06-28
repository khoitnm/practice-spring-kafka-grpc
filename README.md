# practice-spring-kafka-grpc
Applying Spring Boot + Kafka + gRPC serialization

- `kafka-common`: common code which can be reused in both consumer and producer. 
    - The code inside here is quite complicated because I was trying to build a wrapper classes around Spring framework to make it easier to configure producer & consumer.
      Hence the code inside `kafka-producer` and `kafka-consumer` will be much more simple. 
    - The main class to integrate with gRPC are `ProtobufDeserializer` and `ProtobufSerializer`
- `kafka-producer` & `kafka-consumer` :	The implemenation of Kafka Publisher & Consumer. You can use Docker Compose to start a Kafka server on your local machine, then run Publisher to send a message to Kafka, and run Consumer to receive message from Kafka.
    - `kafka-consumer` also has an example test code which uses EmbeddedKafka server.
