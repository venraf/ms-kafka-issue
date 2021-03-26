package my.company.project.kafka.controller;

public class ListenerStatusModel {

    private boolean pause;
    private boolean resume;

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isResume() {
        return resume;
    }

    public void setResume(boolean resume) {
        this.resume = resume;
    }
}
