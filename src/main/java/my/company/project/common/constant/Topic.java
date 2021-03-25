package my.company.project.common.constant;

import static my.company.project.common.constant.Topic.Constants.*;

public enum Topic {

    GENERIC_LOGGING(LOGGING_SERVICE_GENERIC, LOGGING_SERVICE_GENERIC_TOPIC_AS_VALUE),
    REQUEST_LOGGING(LOGGING_SERVICE_REQUEST, LOGGING_SERVICE_REQUEST_TOPIC_AS_VALUE),
    RESPONSE_LOGGING(LOGGING_SERVICE_RESPONSE, LOGGING_SERVICE_RESPONSE_AS_VALUE);

    private final String propertyKey;
    private final String propertyValue;

    public static class Constants {
        public static final String LOGGING_SERVICE_GENERIC  = "logging.service.generic.topic.name";
        public static final String LOGGING_SERVICE_REQUEST  = "logging.service.request.topic.name";
        public static final String LOGGING_SERVICE_RESPONSE = "logging.service.response.topic.name";

        public static final String LOGGING_SERVICE_GENERIC_TOPIC_AS_VALUE = "${" + LOGGING_SERVICE_GENERIC + ":@null}";
        public static final String LOGGING_SERVICE_REQUEST_TOPIC_AS_VALUE = "${" + LOGGING_SERVICE_REQUEST + ":@null}";
        public static final String LOGGING_SERVICE_RESPONSE_AS_VALUE = "${" + LOGGING_SERVICE_RESPONSE + ":@null}";
    }


    Topic(String propertyKey, String propertyValue) {
        this.propertyKey = propertyKey;
        this.propertyValue = propertyValue;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    @Override
    public String toString() {
        return getPropertyValue();
    }
}
