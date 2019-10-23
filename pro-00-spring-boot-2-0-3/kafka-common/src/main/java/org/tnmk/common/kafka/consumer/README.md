This package provides convenient ways to configure the consumer.
You may wonder why the package is ```"consumer"``` but most of the class is ```"ListenerXXX".```
The reason is ```"Listener"``` is another concept of Spring framework.
<pre>
[Kafka Provider] --(send message)--> [Kafka]<--(register to)--[Kafka Consumer/ConsumerGroup]<--(listen from)--[Spring Listener] <--(manage)--[Spring Listener Container]
</pre>
