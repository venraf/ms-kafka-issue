package my.company.project.kafka.config;

import my.company.project.kafka.dto.DeadLetterObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Date;

@Component
public class DeadLetterProducerConfig {

	KafkaTemplate<Object, Object> kafkaTemplateObject;
	KafkaProperties kafkaProperties;

	@Autowired
	public DeadLetterProducerConfig(
			KafkaTemplate<Object, Object> kafkaTemplateObject,
			KafkaProperties kafkaProperties) {
		this.kafkaTemplateObject = kafkaTemplateObject;
		this.kafkaProperties = kafkaProperties;
	}

	public void sendDeadLetter(ConsumerRecord<?, ?> record, Exception exception) {
		DeadLetterObject letter = new DeadLetterObject();
		letter.setTopic(record.topic());
		letter.setSerializedValueSize(BigInteger.valueOf(record.serializedValueSize()));
		letter.setException(exception.getMessage());
		letter.setTimestamp(new Date());
		kafkaTemplateObject.send("utilities.dead", letter);
	}
}
