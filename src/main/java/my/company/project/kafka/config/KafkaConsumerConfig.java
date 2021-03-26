package my.company.project.kafka.config;

import my.company.project.logging.MyLogger;
import my.company.project.kafka.dto.DeadLetterObject;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.Map;
import java.util.UUID;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    public static final String TRUSTED_PACKAGES = "my.company.*";
    private final MyLogger log = new MyLogger(this.getClass());

    protected DeadLetterProducerConfig deadLetter;
    protected KafkaProperties kafkaProperties;
    protected KafkaTemplate<String, Object> template;

    @Autowired
    public KafkaConsumerConfig(
            DeadLetterProducerConfig deadLetter,
            KafkaProperties kafkaProperties,
            KafkaTemplate<String, Object> template) {
        this.deadLetter = deadLetter;
        this.kafkaProperties = kafkaProperties;
        this.template = template;
    }

    @Bean
    public ConsumerFactory<String, DeadLetterObject> deadLetterConsumerFactory() {
        Map<String, Object> configs = kafkaProperties.buildConsumerProperties();
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configs);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DeadLetterObject> deadLetterKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DeadLetterObject> factory = new ConcurrentKafkaListenerContainerFactory<String, DeadLetterObject>();
        factory.setConsumerFactory(deadLetterConsumerFactory());
        factory.setAutoStartup(false);
        SeekToCurrentErrorHandler errorHandler = new SeekToCurrentErrorHandler(new FixedBackOff(0L, 0L));
        errorHandler.addNotRetryableException(IllegalArgumentException.class);
        factory.setErrorHandler(errorHandler);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Object> objectBroadcastConsumerFactory() {
        Map<String, Object> configs = kafkaProperties.buildConsumerProperties();
        JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
        jsonDeserializer.addTrustedPackages(TRUSTED_PACKAGES);
        ErrorHandlingDeserializer<Object> errorHandling = new ErrorHandlingDeserializer<>(jsonDeserializer);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId() + "_" + UUID.randomUUID().toString());
        return new DefaultKafkaConsumerFactory<String, Object>(configs, new StringDeserializer(), errorHandling);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> objectBroadcastKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
        factory.setConsumerFactory(objectBroadcastConsumerFactory());
        factory.setAutoStartup(false);
        SeekToCurrentErrorHandler errorHandler = new SeekToCurrentErrorHandler((record, exception) -> {
            if (exception instanceof ListenerExecutionFailedException && ((ListenerExecutionFailedException) exception).contains(IllegalArgumentException.class))
                log.error("could not parse record. Not in trusted packages: {}", record.toString());
            else {
                log.info("sending deadletter");
                deadLetter.sendDeadLetter(record, exception);
            }
        }, new FixedBackOff(0L, 0L));
        errorHandler.addNotRetryableException(IllegalArgumentException.class);
        errorHandler.addNotRetryableException(ClassNotFoundException.class);
        factory.setErrorHandler(errorHandler);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Object> objectConsumerFactory() {
        Map<String, Object> configs = kafkaProperties.buildConsumerProperties();
        JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
        jsonDeserializer.addTrustedPackages(TRUSTED_PACKAGES);
        ErrorHandlingDeserializer<Object> errorHandling = new ErrorHandlingDeserializer<>(jsonDeserializer);
        return new DefaultKafkaConsumerFactory<String, Object>(configs, new StringDeserializer(), errorHandling);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> objectKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
        factory.setConsumerFactory(objectConsumerFactory());
        factory.setAutoStartup(false);

        SeekToCurrentErrorHandler errorHandler = new SeekToCurrentErrorHandler((record, exception) -> {
            if (exception instanceof ListenerExecutionFailedException && ((ListenerExecutionFailedException) exception).contains(IllegalArgumentException.class))
                log.error("could not parse record. Not in trusted packages: {}", record.toString());
            else {
                log.info("sending deadletter");
                deadLetter.sendDeadLetter(record, exception);
            }
        }, new FixedBackOff(0L, 0L));
        errorHandler.addNotRetryableException(IllegalArgumentException.class);
        errorHandler.addNotRetryableException(ClassNotFoundException.class);
        factory.setErrorHandler(errorHandler);
        return factory;
    }
}