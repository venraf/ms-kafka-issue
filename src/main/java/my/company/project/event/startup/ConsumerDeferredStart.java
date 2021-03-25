package my.company.project.event.startup;

import my.company.project.common.log.Logger;
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConsumerDeferredStart {

	private final Logger log = new Logger(this.getClass());

	@Autowired
	KafkaListenerEndpointRegistry	kafkaRegistry;

	@Autowired
	KafkaProperties								kafkaProperties;

	@Async
	@EventListener(ApplicationReadyEvent.class)
	public void listenerStarter() throws InterruptedException {
		try {
			kafkaRegistry.getListenerContainers().forEach(listener -> {
				listener.getContainerProperties().setMissingTopicsFatal(false);
				listener.getContainerProperties().setClientId(kafkaProperties.getClientId() + "_" + UUID.randomUUID().toString());
				log.info("Starting listener: " + listener.getListenerId() + ", clientId: " + listener.getContainerProperties().getClientId());
				listener.start();
			});
		} catch (TimeoutException e) {
			log.error("Cant connect to kafka node: {}", e.getMessage());
		}
	}
}
