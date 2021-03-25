package my.company.project.common.interceptor;

import my.company.project.common.configuration.LoggingProperties;
import my.company.project.common.constant.ContextConstants;
import my.company.project.common.http.wrapper.HttpServletResponseCopier;
import my.company.project.common.http.wrapper.MyRequestWrapper;
import my.company.project.common.interceptor.configuration.LocalLoggerInterceptorProperties;
import my.company.project.common.interceptor.configuration.LoggerInterceptorProperties;
import my.company.project.common.log.Logger;
import my.company.project.common.logging.LoggerService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;


/**
 * 1. CLIENT PERFORM THE CALL WITH REQUEST
 * 2. THE INTERCEPTOR INTERCEPT THE REQUEST WITH PRE-HANDLE METHOD (AND RETURN TRUE)
 * 3. THE CONTROLLER PROCESSES THE REQUEST
 * 4. THE INTERCEPTOR INTERCEPT THE RESPONSE WITH POST-HANDLE METHOD (AND RETURN TRUE)
 * 5. THE CLIENT GETS THE VIEW (RESPONSE IN THIS CASE)
 * 6. AFTER THAT THE VIEW IS RENDERED THE METHOD AFTER COMPLETION IS CALLED
 */


@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter {

    public static final String BODY_NOT_AVAILABLE = "{}";
    public static final String CARRIAGE_RETURN = "\n";
    public static final String AX_LOG_GROUP_NAME = "AX";
    private final Logger log = new Logger(this.getClass());

    private LoggerInterceptorProperties loggerInterceptorProperties;
    private LoggingProperties loggingProperties;
    private final LoggerService loggerService;

    @Value("${info.alias}")
    private String applicationAlias;

    public LoggerInterceptor(LoggerService loggerService) {
        super();
        this.loggerService = loggerService;
        this.loggerInterceptorProperties = new LocalLoggerInterceptorProperties(false, false);
    }

    @Autowired
    public LoggerInterceptor(LoggingProperties loggingProperties,
                             LoggerService loggerService,
                             LoggerInterceptorProperties loggerInterceptorProperties
    ) {
        super();
        this.loggingProperties = loggingProperties;
        this.loggerService = loggerService;
        this.loggerInterceptorProperties = loggerInterceptorProperties;
    }


    /**
     * This method is used to intercept the request before it’s handed over to the handler method.
     * <p>
     * This method should return ‘true’ to let Spring know to process the request through another spring interceptor or
     * to send it to handler method if there are no further spring interceptors.
     * <p>
     * If this method returns ‘false’ Spring framework assumes that request has been handled by the spring interceptor
     * itself and no further processing is needed. We should use response object to send response to the client request
     * in this case.
     * <p>
     * Execution order: 1
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        if (loggingProperties != null && loggingProperties.isLoggerHttpHandlerInterceptorRequestEnabled()) {

            String ip = getClientIpFromRequest(request);
            MDC.put(ContextConstants.IP_CLIENT, ip);
            String body = getBody(request);
            StringBuffer requestURL = request.getRequestURL();
            String method = request.getMethod();
            String headerNamesAndValues = this.getHeaderNamesAndValues(request);
            String bodyWithoutCarriageReturn = removeCarriageReturn(body);
            String bodyWithoutCarriageReturnAndNormalizedSpace = normalizedSpace(bodyWithoutCarriageReturn);

            log.info("Incoming request: [{}][{}][{}][{}][{}]", method, requestURL, headerNamesAndValues, bodyWithoutCarriageReturnAndNormalizedSpace, ip);

            if (loggerInterceptorProperties.isIncomingRequestToTrace()) {
                loggerService.logRequestEvent("AxIncomingRequest", applicationAlias, method, requestURL.toString(), headerNamesAndValues, body, ip, "AX", null, null, null, null);
            }
        }

        return true;

    }

    private String getBody(HttpServletRequest request) {
        String body;
        MyRequestWrapper req;
        try {
            if (request instanceof MyRequestWrapper) {
                req = (MyRequestWrapper) request;
            } else {
                ContentCachingRequestWrapper requestWrapper = (ContentCachingRequestWrapper) request;
                req = (MyRequestWrapper) requestWrapper.getRequest();
            }
            body = req.getBody();
        } catch (Exception e) {
            body = BODY_NOT_AVAILABLE;
            log.info("Cannot retrieve request body from HttpServletRequest");
        }
        return body;
    }

    private String normalizedSpace(String bodyWithoutCarriageReturn) {
        return StringUtils.normalizeSpace(bodyWithoutCarriageReturn);
    }

    private String removeCarriageReturn(String body) {

        String bodyWithoutCarriageReturn = StringUtils.remove(body, CARRIAGE_RETURN);
        return bodyWithoutCarriageReturn;
    }

    /**
     * This HandlerInterceptor interceptor method is called when HandlerAdapter has invoked the handler but
     * DispatcherServlet is yet to render the view. This method can be used to add additional attribute to the
     * ModelAndView object to be used in the view pages. We can use this spring interceptor method to determine the time
     * taken by handler method to process the client request.
     * <p>
     * Execution order: 2
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (loggingProperties != null && loggingProperties.isLoggerHttpHandlerInterceptorRequestEnabled()) {

            String body = getBodyResponse(response, BODY_NOT_AVAILABLE);
            String ip = MDC.get(ContextConstants.IP_CLIENT);
            ip = ip == null ? getClientIpFromRequest(request) : ip;
            StringBuffer requestURL = request.getRequestURL();
            String method = request.getMethod();
            String headerNamesAndValues = this.getHeaderNamesAndValues(request);
            String bodyWithoutCarriageReturn = removeCarriageReturn(body);
            String bodyWithoutCarriageReturnAndNormalizedSpace = normalizedSpace(bodyWithoutCarriageReturn);
            int status = response.getStatus();
            String exceptionMessage = BODY_NOT_AVAILABLE;

            log.info("Outgoing response...       : [{}][{}][{}][{}][{}][{}][{}]", status, method, requestURL, headerNamesAndValues, bodyWithoutCarriageReturnAndNormalizedSpace, ip, exceptionMessage);

            if (loggerInterceptorProperties.isOutcomingResponseToTrace()) {
                loggerService.logResponseEvent("AxOutgoingResponse", applicationAlias, status, requestURL.toString(), headerNamesAndValues, body, ip, AX_LOG_GROUP_NAME, null, null, null, null);
            }
        }
    }

    // Execution order: 3
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (loggingProperties.isLoggerHttpHandlerInterceptorRequestEnabled()) {

            String body = getBodyResponse(response, BODY_NOT_AVAILABLE);
            String ip = MDC.get(ContextConstants.IP_CLIENT);
            ip = ip == null ? getClientIpFromRequest(request) : ip;
            StringBuffer requestURL = request.getRequestURL();
            String method = request.getMethod();
            String headerNamesAndValues = this.getHeaderNamesAndValues(request);
            String bodyWithoutCarriageReturn = removeCarriageReturn(body);
            String bodyWithoutCarriageReturnAndNormalizedSpace = normalizedSpace(bodyWithoutCarriageReturn);
            int status = response.getStatus();
            String exceptionMessage = BODY_NOT_AVAILABLE;
            String exceptionStackTrace = null;

            if (ex != null) {
                exceptionMessage = ex.getMessage() != null ? ex.getMessage() : "Error description not available";
                StringWriter sw = new StringWriter();
                ex.printStackTrace(new PrintWriter(sw));
                exceptionStackTrace = sw.toString();
                StringBuilder sb = new StringBuilder(exceptionMessage).append(" [").append(exceptionStackTrace).append("]");
                exceptionStackTrace = sb.toString();
            }

            log.info("Outgoing response... done! : [{}][{}][{}][{}][{}][{}][{}]", status, method, requestURL, headerNamesAndValues, bodyWithoutCarriageReturnAndNormalizedSpace, ip, exceptionMessage);

            if (ex != null) {
                loggerService.logResponseEvent("AxOutgoingResponse", applicationAlias, status, requestURL.toString(), headerNamesAndValues, body, ip, AX_LOG_GROUP_NAME, null, null, null, exceptionStackTrace);
            } else {
                if (loggerInterceptorProperties.isOutcomingResponseToTrace()) {
                    loggerService.logResponseEvent("AxOutgoingResponse", applicationAlias, status, requestURL.toString(), headerNamesAndValues, body, ip, AX_LOG_GROUP_NAME, null, null, null, exceptionStackTrace);
                }
            }

            MDC.remove(ContextConstants.IP_CLIENT);
        }
    }

    private String getBodyResponse(HttpServletResponse response, String body) {
        try {
            if (response instanceof ContentCachingResponseWrapper) {
                ContentCachingResponseWrapper responseWrapper = (ContentCachingResponseWrapper) response;
                byte[] bytes = responseWrapper.getContentAsByteArray();
                body = new String(bytes);
            } else if (response instanceof HttpServletResponseCopier) {
                HttpServletResponseCopier responseWrapper = (HttpServletResponseCopier) response;
                byte[] bytes = responseWrapper.getCopy();
                body = new String(bytes);
            } else {
                log.warn("Response not recognized or not available");
            }
        } catch (Exception e) {
            log.error("Cannot retrieve request body from HttpServletRequest", e);
        }
        return body;
    }

    private String getClientIpFromRequest(HttpServletRequest request) {

        String ip = "";
        boolean found = false;

        // Parameter format: Header X-Forwarded-For: ip_client, ip_proxy1, ip_proxy2
        if ((ip = request.getHeader(ContextConstants.X_FORWARDED_FOR)) != null) {
            // ip_client
            ip = ip.split(",")[0];

            found = true;
        }

        if (!found) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    private String getHeaderNamesAndValues(HttpServletRequest request) {

        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder sb = new StringBuilder();

        while (headerNames.hasMoreElements()) {
            String curr = headerNames.nextElement();
            sb.append(curr);
            sb.append("=");
            sb.append(request.getHeader(curr));
            if (headerNames.hasMoreElements()) {
                sb.append(";");
            }
        }

        return sb.toString();
    }

    private String getParameters(HttpServletRequest request) {
        StringBuffer posted = new StringBuffer();
        Enumeration<?> e = request.getParameterNames();
        if (e != null) {
            posted.append("?");
        }
        while (e.hasMoreElements()) {
            if (posted.length() > 1) {
                posted.append("&");
            }
            String curr = (String) e.nextElement();
            posted.append(curr + "=");
            if (curr.contains("password") || curr.contains("pass") || curr.contains("pwd")) {
                posted.append("*****");
            } else {
                posted.append(request.getParameter(curr));
            }
        }
        String ip = request.getHeader(ContextConstants.X_FORWARDED_FOR);
        String ipAddr = (ip == null) ? getRemoteAddr(request) : ip;
        if (ipAddr != null && !ipAddr.equals("")) {
            posted.append("&_psip=" + ipAddr);
        }
        return posted.toString();
    }

    private String getRemoteAddr(HttpServletRequest request) {
        String ipFromHeader = request.getHeader(ContextConstants.X_FORWARDED_FOR);
        if (ipFromHeader != null && ipFromHeader.length() > 0) {
            log.debug("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }
}