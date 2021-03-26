package my.company.project.kafka.annotation;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@KafkaListener(autoStartup = "false",
        clientIdPrefix = "${my.company.kafka.consumer.listeners-prefix:${company.application.name}}-${random.uuid}",
        properties= {ConsumerConfig.MAX_POLL_RECORDS_CONFIG+":200",
                ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG+":200000"}
        )
public @interface KafkaGenericListener {

    String id() default "";

    boolean idIsGroup() default true;

    String[] topics() default {};

    String topicPattern() default "";

    TopicPartition[] topicPartitions() default {};

    String errorHandler() default "";

    String concurrency() default "";
}
