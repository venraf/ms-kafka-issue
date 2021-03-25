package my.company.project.kafka.dto.logging;

public class RequestEventContent extends EventContent {

    private String httpMethod;
    private String url;
    private Object headerParamList;
    private Object body;

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getHeaderParamList() {
        return headerParamList;
    }

    public void setHeaderParamList(Object headerParamList) {
        this.headerParamList = headerParamList;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "RequestEventContent{" +
                "httpMethod='" + httpMethod + '\'' +
                ", url='" + url + '\'' +
                ", headerParamList=" + headerParamList +
                ", body=" + body +
                '}';
    }
}
