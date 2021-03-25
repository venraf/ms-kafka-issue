package my.company.project.common.messaging.logging.producer;

import my.company.project.kafka.dto.logging.Event;

public interface LoggerProducer {
    boolean produce(Event event);
}
