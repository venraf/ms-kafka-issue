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
public class CreateLoggingInternalEventConsumer {

    private final Logger log = new Logger(this.getClass());

    @MyKafkaListener(topics = LOGGING_SERVICE_GENERIC_TOPIC_AS_VALUE)
    protected void consume(ConsumerRecord<?, InternalEvent> input) {
        log.info(RECEIVED_NEW_EVENT, CREATE_INTERNAL_LOGGING, input.value());
    }

}
