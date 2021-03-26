package my.company.project.kafka.controller;

import org.springframework.kafka.listener.ContainerProperties;

import java.util.Collection;
import java.util.LinkedList;

public class ListenerModel {

    private String id;
    private boolean pause;
    private Collection<String> partitions = new LinkedList<>();
    private ContainerProperties containerProperties;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public ContainerProperties getContainerProperties() {
        return containerProperties;
    }

    public void setContainerProperties(ContainerProperties containerProperties) {
        this.containerProperties = containerProperties;
    }

    public Collection<String> getPartitions() {
        return partitions;
    }

    public void setPartitions(Collection<String> partitions) {
        this.partitions = partitions;
    }
}
