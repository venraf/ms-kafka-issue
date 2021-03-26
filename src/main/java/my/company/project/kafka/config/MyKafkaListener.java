package my.company.project.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.core.annotation.AliasFor;
import org.springframework.kafka.annotation.KafkaListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@KafkaListener(containerFactory = "objectKafkaListenerContainerFactory", autoStartup = "false",
		properties= {ConsumerConfig.MAX_POLL_RECORDS_CONFIG+":200",
				ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG+":200000"},
		clientIdPrefix = "${spring.kafka.client-id}-${random.uuid}"
)
public @interface MyKafkaListener {

	@AliasFor(annotation = KafkaListener.class, attribute = "groupId")
	String groupId() default "";

	@AliasFor(annotation = KafkaListener.class, attribute = "topics")
	String[] topics();
}