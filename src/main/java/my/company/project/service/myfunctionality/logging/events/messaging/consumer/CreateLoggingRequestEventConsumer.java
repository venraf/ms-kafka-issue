package my.company.project.service.myfunctionality.logging.events.messaging.consumer;

import my.company.project.event.config.MyKafkaListener;
import my.company.project.kafka.dto.logging.InternalEvent;
import my.company.project.common.log.Logger;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;
import static my.company.project.common.constant.Topic.Constants.*;
import static my.company.project.common.constant.EventVsTopic.LoggingConstants.*;
import static my.company.project.common.constant.EventVsTopic.EventType.*;
@Service
public class CreateLoggingRequestEventConsumer {

    private final Logger log = new Logger(this.getClass());

    @MyKafkaListener(topics = LOGGING_SERVICE_REQUEST_TOPIC_AS_VALUE)
    protected void consume(ConsumerRecord<?, InternalEvent> input) {
        log.info(RECEIVED_NEW_EVENT, CREATE_REQUEST_LOGGING, input.value());
    }
}
