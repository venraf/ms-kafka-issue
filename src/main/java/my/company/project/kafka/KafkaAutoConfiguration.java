package my.company.project.kafka;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;

@Configuration
@EnableConfigurationProperties({
        KafkaConsumerProperties.class
})
public class KafkaAutoConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public KafkaHelper kafkaHelper(KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry, KafkaConsumerProperties properties) {
        return new KafkaHelper(kafkaListenerEndpointRegistry, properties);
    }

}
