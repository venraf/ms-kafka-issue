package my.company.project.kafka.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "my.company.kafka.consumer")
public class KafkaConsumerProperties {

    private String listenersPrefix;
    private Integer startupIncrementalDelay = 3;
    private Integer startupRuns = 7;
    private boolean initAtStartup = true;

    public Integer getStartupIncrementalDelay() {
        return startupIncrementalDelay;
    }

    public void setStartupIncrementalDelay(Integer startupIncrementalDelay) {
        this.startupIncrementalDelay = startupIncrementalDelay;
    }

    public Integer getStartupRuns() {
        return startupRuns;
    }

    public void setStartupRuns(Integer startupRuns) {
        this.startupRuns = startupRuns;
    }

    public String getListenersPrefix() {
        return listenersPrefix;
    }

    public void setListenersPrefix(String listenersPrefix) {
        this.listenersPrefix = listenersPrefix;
    }

    public boolean isInitAtStartup() {
        return initAtStartup;
    }

    public void setInitAtStartup(boolean initAtStartup) {
        this.initAtStartup = initAtStartup;
    }
}
