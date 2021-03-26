package my.company.project.kafka.listener;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

public class KafkaPropertiesListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent applicationEnvironmentPreparedEvent) {
        ConfigurableEnvironment configurableEnvironment = applicationEnvironmentPreparedEvent.getEnvironment();
        Properties properties = new Properties();
        properties.put("spring.kafka.consumer.auto-offset-reset", "earliest");
        properties.put("spring.kafka.consumer.enable-auto-commit", "false");
        properties.put("spring.kafka.listener.ack-mode", "record");
        properties.put("spring.kafka.producer.client-id", "${my.company.kafka.producer.prefix:${company.application.name}}-${random.uuid}");
        properties.put("spring.kafka.producer.key-serializer", "org.springframework.kafka.support.serializer.JsonSerializer");
        properties.put("spring.kafka.producer.value-serializer", "org.springframework.kafka.support.serializer.JsonSerializer");
        configurableEnvironment.getPropertySources().addFirst(new PropertiesPropertySource("my.company.kafka.default", properties));
    }
}
