package my.company.project.service;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping
@Validated
public class MyController {

    private Logger log = LoggerFactory.getLogger(MyController.class);
    public static final String MY_TOPIC = "my.topic";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public MyController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping(
            value = {"/resources/{id}"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MyResponse> endpoint(
            @PathVariable String id) {
        log.info("Called!");

        MyEvent myEvent = new MyEvent(id);

        log.debug("Sending event '{}'={} to: {} ", "MyEvent", myEvent, MY_TOPIC);
        kafkaTemplate.send(MY_TOPIC, new MyEvent(id));

        return new ResponseEntity<>(new MyResponse(id), HttpStatus.OK);
    }

    @KafkaListener(autoStartup = "false",
            clientIdPrefix = "myClientId",
            groupId = "GroupId",
            properties= {ConsumerConfig.MAX_POLL_RECORDS_CONFIG+":200",
                    ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG+":200000"},
            topics = "my.topic"
    )
    protected void consume(ConsumerRecord<?, MyEvent> input) {
        log.info("Received new event='{}': {}", "MyEvent", input.value());
    }

    public class MyResponse {

        private String id;

        public MyResponse() {
        }

        public MyResponse(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "MyResponse{" +
                    "id='" + id + '\'' +
                    '}';
        }
    }
    public class MyEvent {
        private String id;

        public MyEvent() {
        }

        public MyEvent(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "MyEvent{" +
                    "id='" + id + '\'' +
                    '}';
        }
    }

}