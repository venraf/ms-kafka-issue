package my.company.project.common.constant;

public enum EventVsTopic {

    CREATE_INTERNAL_LOGGING(EventType.CREATE_INTERNAL_LOGGING, Topic.GENERIC_LOGGING),
    CREATE_REQUEST_LOGGING(EventType.CREATE_REQUEST_LOGGING, Topic.REQUEST_LOGGING),
    CREATE_RESPONSE_LOGGING(EventType.CREATE_RESPONSE_LOGGING, Topic.RESPONSE_LOGGING);

    private EventType type;
    private Topic topic;

    public class LoggingConstants {
        public static final String SENDING_EVENT_TO = "Sending event '{}'={} to: {} ";
        public static final String THE_EVENT_HAS_BEEN_SENT_TO_THE_TOPIC_CORRECTLY = "The event '{}'={} has been sent to the topic={} correctly! ";
        public static final String THE_EVENT_HASN_T_BEEN_SENT_TO_THE_TOPIC = "The event '{}'={} hasn't been sent to the topic={}. ";
        public static final String RECEIVED_NEW_EVENT = "Received new event='{}': {}";
    }

    public enum EventType {
        CREATE_INTERNAL_LOGGING("createLoggingInternalEvent"),
        CREATE_REQUEST_LOGGING("createLoggingRequestEvent"),
        CREATE_RESPONSE_LOGGING("createLoggingResponseEvent");

        private final String name;
        EventType(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    EventVsTopic(
            EventType type,
            Topic topic
    ) {
        this.type = type;
        this.topic = topic;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

}
