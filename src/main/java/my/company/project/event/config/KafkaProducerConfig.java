package my.company.project.event.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
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
		KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<>(producerFactory());
		return kafkaTemplate;
	}


	private ProducerFactory<String, Object> producerFactory() {
		DefaultKafkaProducerFactory<String, Object> defaultKafkaProducerFactory =
				new DefaultKafkaProducerFactory<>(producerConfigs());
		//defaultKafkaProducerFactory.transactionCapable();
		//defaultKafkaProducerFactory.setTransactionIdPrefix("tx-");
		return defaultKafkaProducerFactory;
	}

	private Map<String, Object> producerConfigs() {

		Map<String, Object> configs = kafkaProperties.buildProducerProperties();
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		//configs.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
		//configs.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
		//configs.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "1234");
		return configs;
	}

	/*
	@Bean
	public KafkaTransactionManager<String, Object> kafkaTransactionManager() {
		KafkaTransactionManager<String, Object> kafkaTransactionManager = new KafkaTransactionManager<>(producerFactory());
		return kafkaTransactionManager;
	}
	*/

	// ------------------------------------------- dead letter ---------------------------------------------------------
	@Bean
	public KafkaTemplate<Object, Object> kafkaTemplateDeadLetter() {
		return new KafkaTemplate<>(producerFactoryDeadLetter());
	}

	@Bean
	public ProducerFactory<Object, Object> producerFactoryDeadLetter() {

		Map<String, Object> configs = kafkaProperties.buildProducerProperties();
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		configs.put(ProducerConfig.CLIENT_ID_CONFIG, configs.get(ProducerConfig.CLIENT_ID_CONFIG) + "_DeadLetter");
		return new DefaultKafkaProducerFactory<>(configs);
	}
}
