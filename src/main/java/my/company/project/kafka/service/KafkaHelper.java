package my.company.project.kafka.service;

import my.company.project.kafka.properties.KafkaConsumerProperties;
import my.company.project.logging.MyLogger;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class KafkaHelper {

    private final KafkaListenerEndpointRegistry registry;
    private final KafkaConsumerProperties properties;

    private final MyLogger LOGGER = new MyLogger(KafkaHelper.class);

    public KafkaHelper(KafkaListenerEndpointRegistry registry, KafkaConsumerProperties properties) {
        this.registry = registry;
        this.properties = properties;
    }

    /**
     * Start kafka listener
     */
    public void start() {

        if (!this.properties.isInitAtStartup()) return;

        final int runs = this.properties.getStartupRuns();
        final int incrementalDelay = this.properties.getStartupIncrementalDelay();

        Function<KafkaListenerEndpointRegistry, Boolean> kafkaStarter = kafkaListenerEndpointRegistry -> {
            registry.getAllListenerContainers().forEach(c -> {
                new Thread(() -> {
                    try {
                        c.getContainerProperties().setMissingTopicsFatal(false);
                        c.getContainerProperties().setIdleBetweenPolls(1000);
                        c.setAutoStartup(true);
                    } catch (Throwable e) {
                        LOGGER.warn("interrupt exception", e);
                    }
                    if (!c.isRunning()) {
                        c.start();
                    }
                }).start();
            });
            return true;
        };

        Function<Integer, Boolean> incrementalRuns = duration -> {
            new Thread(() -> {
                try {
                    int run = 1;
                    while (run <= runs) {
                        Thread.sleep(TimeUnit.SECONDS.toMillis(duration*run));
                        kafkaStarter.apply(registry);
                        run++;
                    }
                } catch (Throwable e) {
                    LOGGER.warn("interrupt exception", e);
                }
            }).start();
            return true;
        };

        incrementalRuns.apply(incrementalDelay);
    }

    /**
     * Stop kafka listener
     */
    public void stop() {
        registry.getAllListenerContainers().forEach(c -> {
            new Thread(() -> {
                c.setAutoStartup(false);
                if (c.isRunning()) {
                    LOGGER.warn("Stopping listener...");
                    c.stop();
                }
            }).start();
        });
    }
}
