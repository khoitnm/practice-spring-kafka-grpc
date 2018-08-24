# practice-spring-kafka-grpc
Applying Spring Boot + Kafka + gRPC serialization

`kafka-common`: common code which can be reused in both consumer and producer
`kafka-producer` & `kafka-consumer` :	The implemenation of Kafka Publisher & Consumer. You can use Docker Compose to start a Kafka server on your local machine, then run Publisher to send a message to Kafka, and run Consumer to receive message from Kafka.

`kafka-consumer` also has an example test code which uses EmbeddedKafka server.
