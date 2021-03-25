package my.company.project.common.logging.factory;

import my.company.project.common.logging.config.EventBeanConfig;
import my.company.project.kafka.dto.logging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class EventFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    public EventFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private void fillBean(String eventName, String eventSource, EventContent eventContent, String sourceIPAddress, String logGroupName, String transactionId, String merchantTransactionId, String clientTransactionId, Event bean, String errorMessage) {
        bean.setEventName(eventName);
        bean.setEventSource(eventSource);

        bean.setSourceIPAddress(sourceIPAddress);
        bean.setLogGroupName(logGroupName);
        bean.setTransactionId(transactionId);
        bean.setMerchantTransactionId(merchantTransactionId);
        bean.setClientTransactionId(clientTransactionId);
        bean.setErrorMessage(errorMessage);

        if (eventContent instanceof ResponseEventContent) {
            ((ResponseEvent) bean).setResponseEventContent((ResponseEventContent) eventContent);
        } else if (eventContent instanceof RequestEventContent) {
            ((RequestEvent) bean).setRequestEventContent((RequestEventContent) eventContent);
        } else {
            ((InternalEvent) bean).setInternalEventContent((InternalEventContent) eventContent);
        }


    }

    public Event createInternalEvent(
            String eventName,
            String eventSource,
            String logItem,
            String sourceIPAddress,
            String logGroupName,
            String transactionId,
            String merchantTransactionId,
            String clientTransactionId, String errorMessage) {

        Event bean = applicationContext.getBean(EventBeanConfig.BEAN_NAME.INTERNAL_EVENT_DEFAULT, Event.class);
        EventContent eventContent = createInternalContent(bean, logItem);

        fillBean(eventName, eventSource, eventContent, sourceIPAddress, logGroupName, transactionId, merchantTransactionId, clientTransactionId, bean, errorMessage);

        return bean;
    }

    public Event createRequestEvent(
            String eventName,
            String eventSource,
            String httpMethod,
            String url,
            Object headerParam,
            Object body,
            String sourceIPAddress,
            String logGroupName,
            String transactionId,
            String merchantTransactionId,
            String clientTransactionId, String errorMessage) {

        Event bean = applicationContext.getBean(EventBeanConfig.BEAN_NAME.AX_REQUEST_EVENT_DEFAULT, Event.class);
        EventContent eventContent = createRequestContent(bean, httpMethod, url, headerParam, body);

        fillBean(eventName, eventSource, eventContent, sourceIPAddress, logGroupName, transactionId, merchantTransactionId, clientTransactionId, bean, errorMessage);

        return bean;
    }


    public Event createResponseEvent(
            String eventName,
            String eventSource,
            int httpStatus,
            String url,
            Object headerParam,
            Object body,
            String sourceIPAddress,
            String logGroupName,
            String transactionId,
            String merchantTransactionId,
            String clientTransactionId,
            String errorMessage) {

        Event bean = applicationContext.getBean(EventBeanConfig.BEAN_NAME.AX_RESPONSE_EVENT_DEFAULT, Event.class);
        EventContent eventContent = createResponseContent(bean, httpStatus, url, headerParam, body);

        fillBean(eventName, eventSource, eventContent, sourceIPAddress, logGroupName, transactionId, merchantTransactionId, clientTransactionId, bean, errorMessage);

        return bean;
    }


    public Event createExternalRequestEvent(
            String eventName,
            String eventSource,
            String httpMethod,
            String url,
            Object headerParam,
            Object body,
            String sourceIPAddress,
            String logGroupName,
            String transactionId,
            String merchantTransactionId,
            String clientTransactionId, String errorMessage) {

        Event bean = applicationContext.getBean(EventBeanConfig.BEAN_NAME.EXTERNAL_REQUEST_EVENT_DEFAULT, Event.class);
        EventContent eventContent = createRequestContent(bean, httpMethod, url, headerParam, body);

        fillBean(eventName, eventSource, eventContent, sourceIPAddress, logGroupName, transactionId, merchantTransactionId, clientTransactionId, bean, errorMessage);

        return bean;
    }


    public Event createExternalResponseEvent(
            String eventName,
            String eventSource,
            int httpStatus,
            String url,
            Object headerParam,
            Object body,
            String sourceIPAddress,
            String logGroupName,
            String transactionId,
            String merchantTransactionId,
            String clientTransactionId, String errorMessage) {

        Event bean = applicationContext.getBean(EventBeanConfig.BEAN_NAME.EXTERNAL_RESPONSE_EVENT_DEFAULT, Event.class);
        EventContent eventContent = createResponseContent(bean, httpStatus, url, headerParam, body);

        fillBean(eventName, eventSource, eventContent, sourceIPAddress, logGroupName, transactionId, merchantTransactionId, clientTransactionId, bean, errorMessage);

        return bean;
    }

    public EventContent createRequestContent(Event event, String httpMethod, String url, Object headerParam, Object body) {
        RequestEventContent request = getRequestEventContent(event);
        request.setHttpMethod(httpMethod);
        request.setUrl(url);
        request.setHeaderParamList(headerParam);
        request.setBody(body);
        return request;
    }

    private RequestEventContent getRequestEventContent(Event event) {
        RequestEvent requestEvent = (RequestEvent) event;
        if (ObjectUtils.isEmpty(requestEvent.getRequestEventContent())) {
            return new RequestEventContent();
        }
        return requestEvent.getRequestEventContent();
    }

    public EventContent createResponseContent(Event event, int httpStatus, String url, Object headerParam, Object body) {
        ResponseEventContent response = getResponseEventContent(event);
        response.setHttpStatus(httpStatus);
        response.setUrl(url);
        response.setHeaderParamList(headerParam);
        response.setBody(body);
        return response;
    }

    private ResponseEventContent getResponseEventContent(Event event) {
        ResponseEvent responseEvent = (ResponseEvent) event;
        if (ObjectUtils.isEmpty(responseEvent.getResponseEventContent())) {
            return new ResponseEventContent();
        }
        return responseEvent.getResponseEventContent();
    }

    public EventContent createInternalContent(Event event, String logItem) {
        InternalEventContent internalContent = getInternalEventContent(event);
        internalContent.setLogItem(logItem);
        return internalContent;
    }

    private InternalEventContent getInternalEventContent(Event event) {
        InternalEvent internalEvent = (InternalEvent) event;
        if (ObjectUtils.isEmpty(internalEvent.getInternalEventContent())) {
            return new InternalEventContent();
        } else {
            return internalEvent.getInternalEventContent();
        }
    }
}
