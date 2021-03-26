package my.company.project.service;

import my.company.project.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping(value = "/${info.api.version}")
@Validated
public class MyController {

    private final Logger log = new Logger(this.getClass());
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
        sendMessageOnTopic(new MyEvent(id), "my.topic");
        return new ResponseEntity<>(new MyResponse(id), HttpStatus.OK);
    }

    private void sendMessageOnTopic(MyEvent event, String topicName) {

        log.debug("Sending event '{}'={} to: {} ", "MyEvent", event, topicName);

        ListenableFuture<SendResult<String, Object>> listenableFuture = kafkaTemplate.send(topicName, event);

        listenableFuture.addCallback(
                new ListenableFutureCallback<SendResult<String, Object>>() {
                    @Override
                    public void onSuccess(SendResult<String, Object> result) {
                        log.debug("The event '{}'={} has been sent to the topic={} correctly! ",
                                "MyEvent", event, topicName);
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        log.debug("The event '{}'={} hasn't been sent to the topic={}. ",
                                "MyEvent", event, topicName, ex);
                    }
                });

    }
}