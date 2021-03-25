package my.company.project.common.interceptor.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LocalLoggerInterceptorProperties implements LoggerInterceptorProperties {

    private boolean incomingRequestToTrace;
    private boolean outcomingResponseToTrace;

    @Autowired
    public LocalLoggerInterceptorProperties(
            @Value("${logging.http.trace.request.enabled:false}") boolean incomingRequestToTrace,
            @Value("${logging.http.trace.response.enabled:false}") boolean outcomingResponseToTrace) {
        this.incomingRequestToTrace = incomingRequestToTrace;
        this.outcomingResponseToTrace = outcomingResponseToTrace;
    }

    @Override
    public boolean isIncomingRequestToTrace() {
        return incomingRequestToTrace;
    }

    public void setIncomingRequestToTrace(boolean incomingRequestToTrace) {
        this.incomingRequestToTrace = incomingRequestToTrace;
    }

    @Override
    public boolean isOutcomingResponseToTrace() {
        return outcomingResponseToTrace;
    }

    public void setOutcomingResponseToTrace(boolean outcomingResponseToTrace) {
        this.outcomingResponseToTrace = outcomingResponseToTrace;
    }
}
