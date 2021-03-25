package my.company.project.common.interceptor.configuration;

public interface LoggerInterceptorProperties {
    boolean isIncomingRequestToTrace();
    boolean isOutcomingResponseToTrace();
}