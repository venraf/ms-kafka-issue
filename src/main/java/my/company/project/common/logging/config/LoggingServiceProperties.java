package my.company.project.common.logging.config;

import my.company.project.common.constant.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingServiceProperties {

    public static class Constants {

        public static final String LOGGING_SERVICE_RESPONSE_PRODUCER_ENABLED_PROP_KEY = "logging.service.response.producer.enabled";
        public static final String LOGGING_SERVICE_REQUEST_PRODUCER_ENABLED_PROP_KEY = "logging.service.request.producer.enabled";
        public static final String LOGGING_SERVICE_GENERIC_PRODUCER_ENABLED_PROP_KEY = "logging.service.generic.producer.enabled";

        public static final String TOPIC_PRODUCER_RESPONSE_ENABLED_PLACEHOLDER = "${" + LOGGING_SERVICE_RESPONSE_PRODUCER_ENABLED_PROP_KEY + ": false}";
        public static final String TOPIC_PRODUCER_REQUEST_ENABLED_PLACEHOLDER = "${" + LOGGING_SERVICE_REQUEST_PRODUCER_ENABLED_PROP_KEY + ": false}";
        public static final String TOPIC_PRODUCER_GENERIC_ENABLED_VALUE = "${" + LOGGING_SERVICE_GENERIC_PRODUCER_ENABLED_PROP_KEY + ": false}";

    }

    private final String generalLoggingTopic;
    private final String requestLoggingTopic;
    private final String responseLoggingTopic;

    private final boolean loggingRequestEventProducerEnabled;
    private final boolean loggingResponseEventProducerEnabled;
    private final boolean loggingGenericEventProducerEnabled;

    public LoggingServiceProperties(
            @Value(Topic.Constants.LOGGING_SERVICE_GENERIC_TOPIC_AS_VALUE) String generalLoggingTopic,
            @Value(Topic.Constants.LOGGING_SERVICE_REQUEST_TOPIC_AS_VALUE) String requestLoggingTopic,
            @Value(Topic.Constants.LOGGING_SERVICE_RESPONSE_AS_VALUE) String responseLoggingTopic,
            @Value(Constants.TOPIC_PRODUCER_REQUEST_ENABLED_PLACEHOLDER) boolean loggingRequestEventProducerEnabled,
            @Value(Constants.TOPIC_PRODUCER_RESPONSE_ENABLED_PLACEHOLDER) boolean loggingResponseEventProducerEnabled,
            @Value(Constants.TOPIC_PRODUCER_GENERIC_ENABLED_VALUE) boolean loggingGenericEventProducerEnabled
    ) {
        String a = Topic.GENERIC_LOGGING.getPropertyValue();
        this.generalLoggingTopic = generalLoggingTopic;
        this.requestLoggingTopic = requestLoggingTopic;
        this.responseLoggingTopic = responseLoggingTopic;
        this.loggingRequestEventProducerEnabled = loggingRequestEventProducerEnabled;
        this.loggingResponseEventProducerEnabled = loggingResponseEventProducerEnabled;
        this.loggingGenericEventProducerEnabled = loggingGenericEventProducerEnabled;
    }

    public String getGeneralLoggingTopic() {
        return generalLoggingTopic;
    }

    public String getRequestLoggingTopic() {
        return requestLoggingTopic;
    }

    public String getResponseLoggingTopic() {
        return responseLoggingTopic;
    }

    public boolean isLoggingRequestEventProducerEnabled() {
        return loggingRequestEventProducerEnabled;
    }

    public boolean isLoggingResponseEventProducerEnabled() {
        return loggingResponseEventProducerEnabled;
    }

    public boolean isLoggingGenericEventProducerEnabled() {
        return loggingGenericEventProducerEnabled;
    }

    @Override
    public String toString() {
        return "LoggingServiceProperties{" +
                "generalLoggingTopic='" + generalLoggingTopic + '\'' +
                ", requestLoggingTopic='" + requestLoggingTopic + '\'' +
                ", responseLoggingTopic='" + responseLoggingTopic + '\'' +
                ", loggingRequestEventProducerEnabled=" + loggingRequestEventProducerEnabled +
                ", loggingResponseEventProducerEnabled=" + loggingResponseEventProducerEnabled +
                ", loggingGenericEventProducerEnabled=" + loggingGenericEventProducerEnabled +
                '}';
    }
}
