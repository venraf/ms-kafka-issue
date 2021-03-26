package my.company.project.service;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyResponse {

    private String id;

    public MyResponse() {
    }

    public MyResponse(String id) {
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
        return "FlowDetailsResponse{" +
                "id='" + id + '\'' +
                '}';
    }
}
