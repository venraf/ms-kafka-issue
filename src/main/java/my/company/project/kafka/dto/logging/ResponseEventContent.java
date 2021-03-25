package my.company.project.kafka.dto.logging;

import java.util.List;

public class ResponseEventContent extends EventContent {

    private int httpStatus;
    private String url;
    private Object headerParamList;
    private Object body;

    public ResponseEventContent() {
    }

    public ResponseEventContent(int httpStatus, String url, List<HeaderParam> headerParamList, String body) {
        this.httpStatus = httpStatus;
        this.url = url;
        this.headerParamList = headerParamList;
        this.body = body;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
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
        return "ResponseEventContent{" +
                "httpStatus=" + httpStatus +
                ", url='" + url + '\'' +
                ", headerParamList=" + headerParamList +
                ", body=" + body +
                '}';
    }
}
