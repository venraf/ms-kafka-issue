package my.company.project.common.logging.config;

import my.company.project.common.constant.ContextConstants;
import my.company.project.common.service.tracing.TraceService;
import my.company.project.common.utils.DateUtils;
import my.company.project.common.utils.uuid.UUIDGenerator;
import my.company.project.kafka.dto.logging.*;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Configuration
public class EventBeanConfig {


    public static class BEAN_NAME {
        public static final String INTERNAL_EVENT_DEFAULT = "internalEventDefault";
        public static final String AX_REQUEST_EVENT_DEFAULT = "axRequestEventDefault";
        public static final String AX_RESPONSE_EVENT_DEFAULT = "axResponseEventDefault";
        public static final String EXTERNAL_REQUEST_EVENT_DEFAULT = "externalRequestEventDefault";
        public static final String EXTERNAL_RESPONSE_EVENT_DEFAULT = "externalResponseEventDefault";
        public static final String INTERNAL_EVENT = "internalEvent";
        public static final String AX_REQUEST_EVENT = "axRequestEvent";
        public static final String AX_RESPONSE_EVENT = "axResponseEvent";
        public static final String EXTERNAL_REQUEST_EVENT = "externalRequestEvent";
        public static final String EXTERNAL_RESPONSE_EVENT = "externalResponseEvent";
    }


    private final TraceService traceService;
    private final DateUtils dateUtils;

    @Autowired
    public EventBeanConfig(TraceService traceService, DateUtils dateUtils) {
        this.traceService = traceService;
        this.dateUtils = dateUtils;
    }

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(BEAN_NAME.INTERNAL_EVENT_DEFAULT)
    public Event getInternalEventDefault() {
        Event event = getGenericEvent(new InternalEventContent(), EventType.INTERNAL);
        return event;
    }

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(BEAN_NAME.AX_REQUEST_EVENT_DEFAULT)
    public Event getRequestEventDefault() {
        return getGenericEvent(new RequestEventContent(), EventType.AX_REQUEST);
    }

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(BEAN_NAME.AX_RESPONSE_EVENT_DEFAULT)
    public Event getResponseEventDefault() {
        return getGenericEvent(new ResponseEventContent(), EventType.AX_RESPONSE);
    }

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(BEAN_NAME.EXTERNAL_REQUEST_EVENT_DEFAULT)
    public Event getExternalRequestEventDefault() {
        return getGenericEvent(new RequestEventContent(), EventType.EXTERNAL_REQUEST);
    }

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(BEAN_NAME.EXTERNAL_RESPONSE_EVENT_DEFAULT)
    public Event getExternalResponseEventDefault() {
        return getGenericEvent(new ResponseEventContent(), EventType.EXTERNAL_RESPONSE);
    }

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(BEAN_NAME.INTERNAL_EVENT)
    public Event getInternalEvent() {
        Event event = getGenericEvent(new InternalEventContent(), EventType.INTERNAL);
        return event;
    }

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(BEAN_NAME.AX_REQUEST_EVENT)
    public Event getRequestEvent() {
        Event event = getGenericEvent(new RequestEventContent(), EventType.AX_REQUEST);
        return event;
    }

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(BEAN_NAME.AX_RESPONSE_EVENT)
    public Event getResponseEvent() {
        Event event = getGenericEvent(new ResponseEventContent(), EventType.AX_RESPONSE);
        return event;
    }

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(BEAN_NAME.EXTERNAL_REQUEST_EVENT)
    public Event getExternalRequestEvent() {
        Event event = getGenericEvent(new RequestEventContent(), EventType.EXTERNAL_REQUEST);
        return event;
    }

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(BEAN_NAME.EXTERNAL_RESPONSE_EVENT)
    public Event getExternalResponseEvent() {
        Event event = getGenericEvent(new ResponseEventContent(), EventType.EXTERNAL_RESPONSE);
        return event;
    }


    private Event getGenericEvent(EventContent eventContent, EventType eventType) {
        String currentTraceId = traceService.getCurrentTraceId();
        String currentSpanId = traceService.getCurrentSpanId();


        if (!ObjectUtils.isEmpty(eventContent)) {
            if (eventContent instanceof RequestEventContent) {
                return new RequestEvent.Builder(currentTraceId)
                        .withSpanId(currentSpanId)
                        .withEventId(UUIDGenerator.next())
                        .withEventTime(dateUtils.getDateAsString(new Date(), dateUtils.DEFAULT_PATTERN))
                        .withSourceIPAddress(MDC.get(ContextConstants.IP_CLIENT))
                        .withEventContent((RequestEventContent) eventContent)
                        .withEventType(eventType)
                        .build();
            } else if (eventContent instanceof ResponseEventContent) {
                return new ResponseEvent.Builder(currentTraceId)
                        .withSpanId(currentSpanId)
                        .withEventId(UUIDGenerator.next())
                        .withEventTime(dateUtils.getDateAsString(new Date(), dateUtils.DEFAULT_PATTERN))
                        .withSourceIPAddress(MDC.get(ContextConstants.IP_CLIENT))
                        .withEventContent((ResponseEventContent) eventContent)
                        .withEventType(eventType)
                        .build();
            } else {
                return new InternalEvent.Builder(currentTraceId)
                        .withSpanId(currentSpanId)
                        .withEventId(UUIDGenerator.next())
                        .withEventTime(dateUtils.getDateAsString(new Date(), dateUtils.DEFAULT_PATTERN))
                        .withSourceIPAddress(MDC.get(ContextConstants.IP_CLIENT))
                        .withEventContent((InternalEventContent) eventContent)
                        .withEventType(eventType)
                        .build();
            }
        }

        return new InternalEvent.Builder(currentTraceId)
                .withSpanId(currentSpanId)
                .withEventId(UUIDGenerator.next())
                .withEventTime(dateUtils.getDateAsString(new Date(), dateUtils.DEFAULT_PATTERN))
                .withSourceIPAddress(MDC.get(ContextConstants.IP_CLIENT))
                .withEventContent((InternalEventContent) eventContent)
                .withEventType(eventType)
                .build();

    }

}