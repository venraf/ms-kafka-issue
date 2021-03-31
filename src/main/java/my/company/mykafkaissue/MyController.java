package my.company.mykafkaissue;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private static final Logger log = LoggerFactory.getLogger(MyController.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;
    public static final String MY_TOPIC = "my.topic";

    @Autowired
    public MyController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping(value = {"/resources/{id}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> endpoint(@PathVariable String id) {
        log.info("Sending '{}' to: '{}' ", id, MY_TOPIC);
        kafkaTemplate.send(MY_TOPIC, id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @KafkaListener(topics = MY_TOPIC)
    protected void consume(ConsumerRecord<?, String> input) {
        log.info("Received '{}'", input.value());
    }

}
