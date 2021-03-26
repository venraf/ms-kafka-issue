package my.company.project.service;

import my.company.project.kafka.annotation.KafkaGenericListener;
import my.company.project.logging.MyLogger;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

@Service
public class MyEventConsumer {

    private final MyLogger log = new MyLogger(this.getClass());

    @KafkaGenericListener(topics = "my.topic")
    protected void consume(ConsumerRecord<?, MyEvent> input) {
        log.info("Received new event='{}': {}", "MyEvent", input.value());
    }

}
