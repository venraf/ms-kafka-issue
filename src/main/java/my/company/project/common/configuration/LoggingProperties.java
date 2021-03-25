package my.company.project.common.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class LoggingProperties {

    @Value("${logging.http.options.file.enabled}")
    private boolean httpLoggingLogOnFile;

    @Value("${logging.http.request.enabled}")
    private boolean httpLoggingRequestEnabled;

    @Value("${logging.http.response.enabled}")
    private boolean httpLoggingResponseEnabled;

    @Value("${logging.http.handler.interceptor.request.enabled}")
    private boolean loggerHttpHandlerInterceptorRequestEnabled;

    @Value("${logging.http.handler.interceptor.response.enabled}")
    private boolean loggerHttpHandlerInterceptorResponseEnabled;


    public boolean isLoggerHttpHandlerInterceptorRequestEnabled() {
        return loggerHttpHandlerInterceptorRequestEnabled;
    }

    public void setLoggerHttpHandlerInterceptorRequestEnabled(boolean loggerHttpHandlerInterceptorRequestEnabled) {
        this.loggerHttpHandlerInterceptorRequestEnabled = loggerHttpHandlerInterceptorRequestEnabled;
    }

    public boolean isLoggerHttpHandlerInterceptorResponseEnabled() {
        return loggerHttpHandlerInterceptorResponseEnabled;
    }

    public void setLoggerHttpHandlerInterceptorResponseEnabled(boolean loggerHttpHandlerInterceptorResponseEnabled) {
        this.loggerHttpHandlerInterceptorResponseEnabled = loggerHttpHandlerInterceptorResponseEnabled;
    }

    public boolean isHttpLoggingRequestEnabled() {
        return httpLoggingRequestEnabled;
    }

    public void setHttpLoggingRequestEnabled(boolean httpLoggingRequestEnabled) {
        this.httpLoggingRequestEnabled = httpLoggingRequestEnabled;
    }

    public boolean isHttpLoggingResponseEnabled() {
        return httpLoggingResponseEnabled;
    }

    public void setHttpLoggingResponseEnabled(boolean httpLoggingResponseEnabled) {
        this.httpLoggingResponseEnabled = httpLoggingResponseEnabled;
    }

    public boolean isHttpLoggingLogOnFile() {
        return httpLoggingLogOnFile;
    }

    public void setHttpLoggingLogOnFile(boolean httpLoggingLogOnFile) {
        this.httpLoggingLogOnFile = httpLoggingLogOnFile;
    }
}
