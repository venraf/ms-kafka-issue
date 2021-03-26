package my.company.project.kafka.controller;

import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/kafka/settings")
public class KafkaController {

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    public KafkaController(KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry) {
        this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
    }

    @GetMapping("/listeners")
    public Collection<String> listeners() {
        return this.kafkaListenerEndpointRegistry.getListenerContainerIds()
                .parallelStream().map(id ->
                        this.kafkaListenerEndpointRegistry.getListenerContainer(id).getAssignedPartitions()
                                .parallelStream().findFirst().map(TopicPartition::topic).orElse("")
                ).collect(Collectors.toList());
    }

    @GetMapping("/listeners/{name}")
    public ListenerModel listeners(@PathVariable("name") String name) {
        Optional<MessageListenerContainer> container = this.getFromPartitionName(name);
        if (!container.isPresent()) return null;
        MessageListenerContainer messageListenerContainer = container.get();
        ListenerModel listenerModel = new ListenerModel();
        listenerModel.setId(name);
        listenerModel.setPause(messageListenerContainer.isContainerPaused());
        listenerModel.setContainerProperties(messageListenerContainer.getContainerProperties());
        listenerModel.setPartitions(messageListenerContainer.getAssignedPartitions().parallelStream().map(
                topicPartition -> topicPartition.toString()).collect(Collectors.toList()));
        return listenerModel;
    }

    @PutMapping("/listeners/{name}")
    public void listeners(@PathVariable("name") String name,
                          @RequestParam(value = "resume", required = false) boolean resume,
                          @RequestParam(value = "pause", required = false) boolean pause) {
        Optional<MessageListenerContainer> container = this.getFromPartitionName(name);
        if (!container.isPresent()) return;
        MessageListenerContainer messageListenerContainer = container.get();
        if (pause) messageListenerContainer.pause();
        if (resume) messageListenerContainer.resume();
    }

    private Optional<MessageListenerContainer> getFromPartitionName(String name) {
        return this.kafkaListenerEndpointRegistry.getListenerContainerIds()
                .parallelStream().filter(id ->
                        this.kafkaListenerEndpointRegistry.getListenerContainer(id).getAssignedPartitions()
                                .parallelStream().findFirst().map(TopicPartition::topic).orElse("").equalsIgnoreCase(name)
                ).findFirst().map(id -> this.kafkaListenerEndpointRegistry.getListenerContainer(id));
    }
}
