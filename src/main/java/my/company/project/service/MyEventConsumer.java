package my.company.project.service;

import my.company.project.logging.Logger;
import my.company.project.kafka.config.MyKafkaListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

@Service
public class MyEventConsumer {

    private final Logger log = new Logger(this.getClass());

    @MyKafkaListener(topics = "my.topic")
    protected void consume(ConsumerRecord<?, MyEvent> input) {
        log.info("Received new event='{}': {}", "MyEvent", input.value());
    }

}
