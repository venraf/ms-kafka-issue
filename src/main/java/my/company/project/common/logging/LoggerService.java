package my.company.project.common.logging;

public interface LoggerService {
    void logRequestEvent(String eventName, String eventSource, String httpMethod, String url, Object headerParams, Object body, String sourceIPAddress, String logGroupName, String transactionId, String merchantTransactionId, String clientTransactionId, String errorMessage);

    void logResponseEvent(String eventName, String eventSource, int httpStatus, String url, Object headerParamList, Object body, String sourceIPAddress, String logGroupName, String transactionId, String merchantTransactionId, String clientTransactionId, String errorMessage);

    void logExternalRequestEvent(String eventName, String eventSource, String httpMethod, String url, Object headerParams, Object body, String sourceIPAddress, String logGroupName, String transactionId, String merchantTransactionId, String clientTransactionId, String errorMessage);

    void logExternalResponseEvent(String eventName, String eventSource, int httpStatus, String url, Object headerParamList, Object body, String sourceIPAddress, String logGroupName, String transactionId, String merchantTransactionId, String clientTransactionId, String errorMessage);

    void logInternalEvent(String eventName, String eventSource, String logItem, String sourceIPAddress, String logGroupName, String transactionId, String merchantTransactionId, String clientTransactionId, String errorMessage);
}
