package my.company.project.kafka.configuration;

import my.company.project.kafka.controller.KafkaController;
import my.company.project.kafka.properties.KafkaConsumerProperties;
import my.company.project.kafka.properties.KafkaProducerProperties;
import my.company.project.kafka.service.KafkaHelper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;

@Configuration
@EnableConfigurationProperties({
        KafkaConsumerProperties.class,
        KafkaProducerProperties.class
})
@Import(KafkaController.class)
public class KafkaAutoConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public KafkaHelper kafkaHelper(KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry, KafkaConsumerProperties properties) {
        return new KafkaHelper(kafkaListenerEndpointRegistry, properties);
    }

}
