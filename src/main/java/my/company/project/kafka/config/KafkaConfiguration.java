package my.company.project.kafka.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"my.company.project.*"})
public class KafkaConfiguration {
}