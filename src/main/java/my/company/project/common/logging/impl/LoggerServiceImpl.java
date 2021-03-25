package my.company.project.common.logging.impl;

import my.company.project.common.logging.LoggerService;
import my.company.project.common.logging.factory.EventFactory;
import my.company.project.common.messaging.logging.producer.LoggerProducer;
import my.company.project.kafka.dto.logging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoggerServiceImpl implements LoggerService {

    @Resource
    private final EventFactory eventFactory;
    private final LoggerProducer loggerProducer;

    @Autowired
    public LoggerServiceImpl(
            EventFactory eventFactory,
            LoggerProducer loggerProducer) {
        this.eventFactory = eventFactory;
        this.loggerProducer = loggerProducer;
    }

    public EventContent fillRequest(Event event, String httpMethod, String url, Object headerParam, Object body) {
        RequestEventContent request = ((RequestEvent) event).getRequestEventContent();
        request.setHttpMethod(httpMethod);
        request.setUrl(url);
        request.setHeaderParamList(headerParam);
        request.setBody(body);
        return request;
    }

    public EventContent fillResponse(Event event, int httpStatus, String url, Object headerParam, Object body) {
        ResponseEventContent response = ((ResponseEvent) event).getResponseEventContent();
        response.setHttpStatus(httpStatus);
        response.setUrl(url);
        response.setHeaderParamList(headerParam);
        response.setBody(body);
        return response;
    }

    public EventContent fillInternal(Event event, String logItem) {
        InternalEventContent internalContent = ((InternalEvent) event).getInternalEventContent();
        internalContent.setLogItem(logItem);
        return internalContent;
    }

    @Override
    public void logRequestEvent(String eventName, String eventSource, String httpMethod, String url, Object headerParams, Object body, String sourceIPAddress, String logGroupName, String transactionId, String merchantTransactionId, String clientTransactionId, String errorMessage) {
        Event event = eventFactory.createRequestEvent(eventName, eventSource, httpMethod, url, headerParams, body, sourceIPAddress, logGroupName, transactionId, merchantTransactionId, clientTransactionId, errorMessage);
        loggerProducer.produce(event);
    }

    @Override
    public void logResponseEvent(String eventName, String eventSource, int httpStatus, String url, Object headerParamList, Object body, String sourceIPAddress, String logGroupName, String transactionId, String merchantTransactionId, String clientTransactionId, String errorMessage) {
        Event event = eventFactory.createResponseEvent(eventName, eventSource, httpStatus, url, headerParamList, body, sourceIPAddress, logGroupName, transactionId, merchantTransactionId, clientTransactionId, errorMessage);
        loggerProducer.produce(event);
    }

    @Override
    public void logExternalRequestEvent(String eventName, String eventSource, String httpMethod, String url, Object headerParams, Object body, String sourceIPAddress, String logGroupName, String transactionId, String merchantTransactionId, String clientTransactionId, String errorMessage) {
        Event event = eventFactory.createExternalRequestEvent(eventName, eventSource, httpMethod, url, headerParams, body, sourceIPAddress, logGroupName, transactionId, merchantTransactionId, clientTransactionId, errorMessage);
        loggerProducer.produce(event);
    }

    @Override
    public void logExternalResponseEvent(String eventName, String eventSource, int httpStatus, String url, Object headerParamList, Object body, String sourceIPAddress, String logGroupName, String transactionId, String merchantTransactionId, String clientTransactionId, String errorMessage) {
        Event event = eventFactory.createExternalResponseEvent(eventName, eventSource, httpStatus, url, headerParamList, body, sourceIPAddress, logGroupName, transactionId, merchantTransactionId, clientTransactionId, errorMessage);
        loggerProducer.produce(event);
    }


    @Override
    public void logInternalEvent(String eventName, String eventSource, String logItem, String sourceIPAddress, String logGroupName, String transactionId, String merchantTransactionId, String clientTransactionId, String errorMessage) {
        Event event = eventFactory.createInternalEvent(eventName, eventSource, logItem, sourceIPAddress, logGroupName, transactionId, merchantTransactionId, clientTransactionId, errorMessage);
        loggerProducer.produce(event);
    }
}
