package my.company.project.kafka.dto;

import java.math.BigInteger;
import java.util.Date;

public class DeadLetterObject {

    private String topic;
    private BigInteger serializedValueSize;
    private Date timestamp;
    private String exception;

    public DeadLetterObject() {
    }

    public DeadLetterObject(String topic, BigInteger serializedValueSize, Date timestamp, String exception) {
        this.topic = topic;
        this.serializedValueSize = serializedValueSize;
        this.timestamp = timestamp;
        this.exception = exception;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public BigInteger getSerializedValueSize() {
        return serializedValueSize;
    }

    public void setSerializedValueSize(BigInteger serializedValueSize) {
        this.serializedValueSize = serializedValueSize;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}