package my.company.project.event.config;

import org.springframework.core.annotation.AliasFor;
import org.springframework.kafka.annotation.KafkaListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@KafkaListener(containerFactory = "deadLetterKafkaListenerContainerFactory", autoStartup = "false")
public @interface MyKafkaDeadLetterListener {

	@AliasFor(annotation = KafkaListener.class, attribute = "topics")
	String[] topics();

}