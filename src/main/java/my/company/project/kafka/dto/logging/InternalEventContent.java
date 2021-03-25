package my.company.project.kafka.dto.logging;

public class InternalEventContent extends EventContent {
    private String logItem;

    public InternalEventContent() {
    }

    public InternalEventContent(String logItem) {
        this.logItem = logItem;
    }

    public String getLogItem() {
        return logItem;
    }

    public void setLogItem(String logItem) {
        this.logItem = logItem;
    }

    @Override
    public String toString() {
        return "InternalEventContent{" +
                "logItem='" + logItem + '\'' +
                '}';
    }
}
