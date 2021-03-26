package my.company.project.service;

public class MyEvent {
    private String id;

    public MyEvent() {
    }

    public MyEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MyEvent{" +
                "id='" + id + '\'' +
                '}';
    }
}
