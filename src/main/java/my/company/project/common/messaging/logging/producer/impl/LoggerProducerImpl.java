package my.company.project.common.messaging.logging.producer.impl;

import my.company.project.common.constant.EventVsTopic;
import my.company.project.common.constant.Topic;
import my.company.project.common.log.Logger;
import my.company.project.common.logging.config.LoggingServiceProperties;
import my.company.project.common.messaging.logging.producer.LoggerProducer;
import my.company.project.kafka.dto.logging.Event;
import my.company.project.kafka.dto.logging.InternalEvent;
import my.company.project.kafka.dto.logging.RequestEvent;
import my.company.project.kafka.dto.logging.ResponseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static my.company.project.common.constant.EventVsTopic.LoggingConstants.*;

@Service
public class LoggerProducerImpl implements LoggerProducer {

    private final Logger log = new Logger(this.getClass());

    public static final String THE_MESSAGE_WONT_BE_SENT_CAUSE_THE_PROPERTY_HAS_NOT_BE_SET = "The message wont be sent cause the property '{}' has not be set.";
    public static final String THE_MESSAGE_WONT_BE_SENT_BECAUSE_THE_LOGGING_SERVICE_HAS_BEEN_DISABLED = "The message wont be sent because the logging service has been disabled.";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final LoggingServiceProperties loggingServiceProperties;

    @Autowired
    public LoggerProducerImpl(
            KafkaTemplate<String, Object> kafkaTemplate,
            LoggingServiceProperties loggingServiceProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.loggingServiceProperties = loggingServiceProperties;
    }

    public boolean produce(Event event) {
        if (event instanceof RequestEvent) {
            RequestEvent requestEvent = (RequestEvent) event;
            return produceRequestEvent(requestEvent);
        } else if (event instanceof ResponseEvent) {
            ResponseEvent responseEvent = (ResponseEvent) event;
            return produceResponseEvent(responseEvent);
        } else {
            InternalEvent internalEvent = (InternalEvent) event;
            return produceInternalEvent(internalEvent);
        }
    }

    private boolean produceInternalEvent(InternalEvent event) {
        if (ObjectUtils.isEmpty(getLoggingServiceProperties().getGeneralLoggingTopic())) {
            log.warn(THE_MESSAGE_WONT_BE_SENT_CAUSE_THE_PROPERTY_HAS_NOT_BE_SET, Topic.GENERIC_LOGGING.getPropertyKey());
            return false;
        } else {
            if (!getLoggingServiceProperties().isLoggingGenericEventProducerEnabled()) {
                log.warn(THE_MESSAGE_WONT_BE_SENT_BECAUSE_THE_LOGGING_SERVICE_HAS_BEEN_DISABLED);
                return false;
            }
            sendMessageOnTopic(event, getLoggingServiceProperties().getGeneralLoggingTopic());
            return true;
        }
    }

    private boolean produceResponseEvent(ResponseEvent event) {
        if (ObjectUtils.isEmpty(getLoggingServiceProperties().getResponseLoggingTopic())) {
            log.warn(THE_MESSAGE_WONT_BE_SENT_CAUSE_THE_PROPERTY_HAS_NOT_BE_SET, Topic.RESPONSE_LOGGING.getPropertyKey());
            return false;
        } else {
            if (!getLoggingServiceProperties().isLoggingResponseEventProducerEnabled()) {
                log.warn(THE_MESSAGE_WONT_BE_SENT_BECAUSE_THE_LOGGING_SERVICE_HAS_BEEN_DISABLED);
                return false;
            }
            sendMessageOnTopic(event, getLoggingServiceProperties().getResponseLoggingTopic());
            return true;
        }
    }

    private boolean produceRequestEvent(RequestEvent event) {
        if (ObjectUtils.isEmpty(getLoggingServiceProperties().getRequestLoggingTopic())) {
            log.warn(THE_MESSAGE_WONT_BE_SENT_CAUSE_THE_PROPERTY_HAS_NOT_BE_SET, Topic.REQUEST_LOGGING.getPropertyKey());
            return false;
        } else {
            if (!getLoggingServiceProperties().isLoggingRequestEventProducerEnabled()) {
                log.warn(THE_MESSAGE_WONT_BE_SENT_BECAUSE_THE_LOGGING_SERVICE_HAS_BEEN_DISABLED);
                return false;
            }
            sendMessageOnTopic(event, getLoggingServiceProperties().getRequestLoggingTopic());
            return true;
        }
    }

    private void sendMessageOnTopic(RequestEvent event, String topicName) {

        log.debug(SENDING_EVENT_TO,
                EventVsTopic.CREATE_REQUEST_LOGGING.getType().getName(), event, topicName);

        ListenableFuture<SendResult<String, Object>> listenableFuture = getKafkaTemplate().send(topicName, event);

        listenableFuture.addCallback(
                new ListenableFutureCallback<SendResult<String, Object>>() {
                    @Override
                    public void onSuccess(SendResult<String, Object> result) {
                        log.debug(THE_EVENT_HAS_BEEN_SENT_TO_THE_TOPIC_CORRECTLY,
                                EventVsTopic.CREATE_REQUEST_LOGGING.getType().getName(), event, topicName);
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        log.debug(THE_EVENT_HASN_T_BEEN_SENT_TO_THE_TOPIC,
                                EventVsTopic.CREATE_REQUEST_LOGGING.getType().getName(), event, topicName, ex);
                    }
                });

    }

    private void sendMessageOnTopic(ResponseEvent event, String topicName) {

        log.debug(SENDING_EVENT_TO,
                EventVsTopic.CREATE_RESPONSE_LOGGING.getType().getName(), event, topicName);

        ListenableFuture<SendResult<String, Object>> listenableFuture = getKafkaTemplate().send(topicName, event);

        listenableFuture.addCallback(
                new ListenableFutureCallback<SendResult<String, Object>>() {
                    @Override
                    public void onSuccess(SendResult<String, Object> result) {
                        log.debug(THE_EVENT_HAS_BEEN_SENT_TO_THE_TOPIC_CORRECTLY,
                                EventVsTopic.CREATE_RESPONSE_LOGGING.getType().getName(), event, topicName);
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        log.debug(THE_EVENT_HASN_T_BEEN_SENT_TO_THE_TOPIC,
                                EventVsTopic.CREATE_RESPONSE_LOGGING.getType().getName(), event, topicName, ex);
                    }
                });
    }

    private void sendMessageOnTopic(InternalEvent event, String topicName) {

        log.debug(SENDING_EVENT_TO,
                EventVsTopic.CREATE_INTERNAL_LOGGING.getType().getName(), event, topicName);

        ListenableFuture<SendResult<String, Object>> listenableFuture = getKafkaTemplate().send(topicName, event);

        listenableFuture.addCallback(
                new ListenableFutureCallback<SendResult<String, Object>>() {
                    @Override
                    public void onSuccess(SendResult<String, Object> result) {
                        log.debug(THE_EVENT_HAS_BEEN_SENT_TO_THE_TOPIC_CORRECTLY,
                                EventVsTopic.CREATE_INTERNAL_LOGGING.getType().getName(), event, topicName);
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        log.debug(THE_EVENT_HASN_T_BEEN_SENT_TO_THE_TOPIC,
                                EventVsTopic.CREATE_INTERNAL_LOGGING.getType().getName(), event, topicName, ex);
                    }
                });
    }

    public KafkaTemplate<String, Object> getKafkaTemplate() {
        return kafkaTemplate;
    }

    public LoggingServiceProperties getLoggingServiceProperties() {
        return loggingServiceProperties;
    }
}
