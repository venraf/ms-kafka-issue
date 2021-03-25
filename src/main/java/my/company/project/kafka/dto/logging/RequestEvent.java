package my.company.project.kafka.dto.logging;

public class RequestEvent extends Event {

    private RequestEventContent requestEventContent;

    public RequestEvent() {
    }

    public RequestEvent(Builder builder) {
        super();
        this.eventType = builder.eventType;
        this.eventTime = builder.eventTime;
        this.eventId = builder.eventId;
        this.eventName = builder.eventName;
        this.eventSource = builder.eventSource;
        this.requestEventContent = builder.requestEventContent;
        this.sourceIPAddress = builder.sourceIPAddress;
        this.logGroupName = builder.logGroupName;
        this.traceId = builder.traceId;
        this.transactionId = builder.transactionId;
        this.merchantTransactionId = builder.merchantTransactionId;
        this.clientTransactionId = builder.clientTransactionId;
        this.errorMessage = builder.errorMessage;
        this.spanId = builder.spanId;
    }

    public RequestEventContent getRequestEventContent() {
        return requestEventContent;
    }

    public void setRequestEventContent(RequestEventContent requestEventContent) {
        this.requestEventContent = requestEventContent;
    }

    public static class Builder {

        private EventType eventType;
        private String eventTime;
        private String eventId;
        private String eventName;
        private String eventSource;
        private RequestEventContent requestEventContent;
        private String sourceIPAddress;
        private String logGroupName;
        private String traceId;
        private String spanId;
        private String transactionId;
        private String merchantTransactionId;
        private String clientTransactionId;
        public String errorMessage;

        public Builder(String traceId) {
            this.traceId = traceId;
        }


        public Builder withEventType(EventType eventType) {
            this.eventType = eventType;
            return this;
        }

        public Builder withEventTime(String evenTime) {
            this.eventTime = evenTime;
            return this;
        }

        public Builder withEventId(String evenId) {
            this.eventId = evenId;
            return this;
        }

        public Builder withEventName(String evenName) {
            this.eventName = evenName;
            return this;
        }

        public Builder withEventSource(String evenSource) {
            this.eventSource = evenSource;
            return this;
        }

        public Builder withEventContent(RequestEventContent requestEventContent) {
            this.requestEventContent = requestEventContent;
            return this;
        }

        public Builder withSourceIPAddress(String sourceIPAddress) {
            this.sourceIPAddress = sourceIPAddress;
            return this;
        }

        public Builder withLogGroupName(String logGroupName) {
            this.logGroupName = logGroupName;
            return this;
        }

        public Builder withTraceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        public Builder withSpanId(String spanId) {
            this.spanId = spanId;
            return this;
        }

        public Builder withTransactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder withMerchantTransactionId(String merchantTransactionId) {
            this.merchantTransactionId = merchantTransactionId;
            return this;
        }

        public Builder withClientTransactionId(String clientTransactionId) {
            this.clientTransactionId = clientTransactionId;
            return this;
        }

        public Builder withErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public RequestEvent build() {
            return new RequestEvent(this);
        }
    }

}
