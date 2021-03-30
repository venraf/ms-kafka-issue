package my.company.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Map;

@Configuration
@EnableTransactionManagement
public class KafkaProducerConfig {

    KafkaProperties kafkaProperties;

    @Autowired
    public KafkaProducerConfig(
            KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        Map<String, Object> configs = kafkaProperties.buildProducerProperties();
        ProducerFactory<String, Object> defaultKafkaProducerFactory = new DefaultKafkaProducerFactory<>(configs);
        return new KafkaTemplate<>(defaultKafkaProducerFactory);
    }
}
