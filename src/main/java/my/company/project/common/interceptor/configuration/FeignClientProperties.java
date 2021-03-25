package my.company.project.common.interceptor.configuration;

public interface FeignClientProperties {
    boolean isRequestToTrace();
    boolean isResponseToTrace();
    int getNumbersOfRetry();
    long getRetryInterval();
}
