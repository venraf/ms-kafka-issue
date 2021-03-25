package my.company.project.common.interceptor;

import my.company.project.common.constant.ContextConstants;
import my.company.project.common.log.Logger;
import my.company.project.common.logging.LoggerService;
import my.company.project.common.utils.ExceptionUtils;
import feign.Request;
import feign.Response;
import feign.Util;
import feign.slf4j.Slf4jLogger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Iterator;

public class FeignCustomLogger extends Slf4jLogger {

    private final Logger log = new Logger(this.getClass());

    public static final String INFO_ALIAS_PROP_KEY = "info.alias";
    public static final String BINARY_DATA = "Binary data";
    public static final String NOT_DECODABLE = "Not decodable: ";
    public static final String N_A = "n.a.";
    public static final String LOG_GROUP_NAME = "AX2EX";
    public static final int ERROR_HTTP_STATUS = -1;
    public static final String AX_EXTERNAL_RESPONSE = "AxExternalResponse";
    public static final String AX_EXTERNAL_REQUEST = "AxExternalRequest";
    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String COLON_WITH_SPACE = ":" + SPACE;
    public static final String SEMICOLON = ";";
    public static final String LOG_PLACEHOLDER = "%s";
    public static final String HTTP_1_1 = "HTTP/1.1";
    public static final String ARROW = "--->";
    public static final String ARROW_BACK = "<---";
    public static final String ERROR = "ERROR";
    public static final String LOG_PARAM = "[" + LOG_PLACEHOLDER + "]";
    public static final String BYTE_BODY_LOG_PARAM = "[" + LOG_PLACEHOLDER + "-byte body]";
    public static final String MS_LOG_PARAM = "(" + LOG_PLACEHOLDER + "ms)";
    public static final String PREFIX_LOG_REQUEST = ARROW + SPACE + LOG_PLACEHOLDER + SPACE + LOG_PLACEHOLDER + SPACE + HTTP_1_1;
    public static final String PREFIX_LOG_RESPONSE = ARROW_BACK + SPACE + LOG_PLACEHOLDER + SPACE + LOG_PLACEHOLDER + SPACE + HTTP_1_1;
    public static final String PREFIX_LOG_ERROR = ARROW_BACK + SPACE + ERROR + SPACE + LOG_PLACEHOLDER + COLON_WITH_SPACE + LOG_PLACEHOLDER + MS_LOG_PARAM + LOG_PARAM;
    public static final String PREFIX_LOG_RESPONSE_WITH_MS = PREFIX_LOG_RESPONSE + SPACE + MS_LOG_PARAM;
    public static final String REQUEST_LOG_TEMPLATE = PREFIX_LOG_REQUEST + SPACE + LOG_PARAM + LOG_PARAM + BYTE_BODY_LOG_PARAM;
    public static final String RESPONSE_LOG_TEMPLATE = PREFIX_LOG_RESPONSE_WITH_MS + SPACE + LOG_PARAM + LOG_PARAM + BYTE_BODY_LOG_PARAM;

    private String logPrefix;

    private final LoggerService loggerService;

    @Value("${" + INFO_ALIAS_PROP_KEY + "}")
    private String applicationAlias;

    private boolean requestToTrace;
    private boolean responseToTrace;

    public FeignCustomLogger(String logPrefix, LoggerService loggerService) {
        super();
        this.logPrefix = logPrefix;
        this.loggerService = loggerService;
    }

    public FeignCustomLogger(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    public FeignCustomLogger(LoggerService loggerService,
                             boolean requestToTrace,
                             boolean responseToTrace) {
        this.loggerService = loggerService;
        this.requestToTrace = requestToTrace;
        this.responseToTrace = responseToTrace;

    }

    protected void logRequest(String configKey, Level logLevel, Request request) {

        String httpMethod = request.httpMethod().name();
        String url = request.url();

        if (logLevel.ordinal() >= Level.HEADERS.ordinal()) {

            StringBuilder headerParams = new StringBuilder(EMPTY);
            StringBuilder bodyContent;

            Iterator var4 = request.headers().keySet().iterator();


            String bodyText;
            while (var4.hasNext()) {
                bodyText = (String) var4.next();
                Iterator var6 = Util.valuesOrEmpty(request.headers(), bodyText).iterator();

                while (var6.hasNext()) {
                    String value = (String) var6.next();
                    headerParams.append(bodyText).append(COLON_WITH_SPACE).append(value);

                }

                if (var4.hasNext()) {
                    headerParams.append(SEMICOLON);
                }
            }

            bodyContent = new StringBuilder(EMPTY);
            int bodyLength = 0;
            if (request.body() != null) {
                bodyLength = request.length();
                if (logLevel.ordinal() >= Level.FULL.ordinal()) {
                    bodyText = request.charset() != null ? new String(request.body(), request.charset()) : null;
                    bodyContent.append(bodyText != null ? bodyText : BINARY_DATA);
                }
            }

            this.log(configKey, getRequestLogTemplate(), httpMethod, url, headerParams, bodyContent, bodyLength);

            if (requestToTrace) {
                loggerService.logExternalRequestEvent(AX_EXTERNAL_REQUEST, applicationAlias, httpMethod, url,
                        headerParams, bodyContent, null, LOG_GROUP_NAME, null,
                        null, null, null);
            }

        } else {
            this.log(configKey, getPrefixLogRequest(), httpMethod, url);

            if (requestToTrace) {
                loggerService.logExternalRequestEvent(AX_EXTERNAL_REQUEST, applicationAlias, httpMethod, url,
                        N_A, N_A, null, LOG_GROUP_NAME, null,
                        null, null, null);
            }
        }
    }

    protected void log(String configKey, String format, Object... args) {
        log.info(String.format(methodTag(configKey) + format, args));
    }

    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        String reason = response.reason() != null && logLevel.compareTo(Level.NONE) > 0 ? " " + response.reason() : "";
        int status = response.status();
        String url = response.request().url();
        String statusReason = getStatusReason(reason, status);

        StringBuilder headerParams = new StringBuilder(EMPTY);
        StringBuilder bodyContent = new StringBuilder(EMPTY);

        if (logLevel.ordinal() >= Level.HEADERS.ordinal()) {
            Iterator var8 = response.headers().keySet().iterator();

            while (var8.hasNext()) {
                String field = (String) var8.next();
                Iterator var10 = Util.valuesOrEmpty(response.headers(), field).iterator();

                while (var10.hasNext()) {
                    String value = (String) var10.next();
                    headerParams.append(field).append(COLON_WITH_SPACE).append(value);
                }

                if (var8.hasNext()) {
                    headerParams.append(SEMICOLON);
                }
            }

            int bodyLength = 0;

            if (response.body() != null && status != 204 && status != 205) {
                String partialBodyContent = "";
                if (logLevel.ordinal() >= Level.FULL.ordinal()) {
                    this.log(configKey, "");
                }

                byte[] bodyData = null;
                try {
                    bodyData = Util.toByteArray(response.body().asInputStream());
                } catch (Exception e) {

                    partialBodyContent = NOT_DECODABLE + e.getMessage();
                }
                bodyLength = bodyData != null ? bodyData.length : bodyLength;

                if (logLevel.ordinal() >= Level.FULL.ordinal() && bodyLength > 0) {
                    partialBodyContent = Util.decodeOrDefault(bodyData, Util.UTF_8, BINARY_DATA);
                    bodyContent.append(partialBodyContent != null ? partialBodyContent : BINARY_DATA);
                } else {
                    bodyContent.append(partialBodyContent);
                }

                //%s %s HTTP/1.1 (%sms) [%s][%s][%s-byte body]
                this.log(configKey, getResponseLogTemplate(), statusReason, url, elapsedTime, headerParams, bodyContent, bodyLength);

                loggerService.logExternalResponseEvent(AX_EXTERNAL_RESPONSE, applicationAlias, status, url,
                        headerParams, bodyContent, getSourceIpAddress(), LOG_GROUP_NAME, null,
                        null, null, null);

                return response.toBuilder().body(bodyData).build();
            }

            this.log(configKey, getResponseLogTemplate(), statusReason, url, elapsedTime, headerParams, bodyContent, bodyLength);

            if (responseToTrace) {
                loggerService.logExternalResponseEvent(AX_EXTERNAL_RESPONSE, applicationAlias, status, url,
                        headerParams, bodyContent, getSourceIpAddress(), LOG_GROUP_NAME, null,
                        null, null, null);
            }

        } else {
            this.log(configKey, getPrefixLogResponse(), statusReason, url, elapsedTime);

            if (responseToTrace) {
                loggerService.logExternalResponseEvent(AX_EXTERNAL_RESPONSE, applicationAlias, status, url,
                        headerParams, bodyContent, getSourceIpAddress(), LOG_GROUP_NAME, null,
                        null, null, null);
            }
        }

        return response;

    }

    protected IOException logIOException(String configKey, Level logLevel, IOException ioe, long elapsedTime) {

        if (logLevel.ordinal() >= Level.FULL.ordinal()) {
            String stackTrace = ExceptionUtils.getStackTraceAsString(ioe);
            this.log(configKey, PREFIX_LOG_ERROR, ioe.getClass().getSimpleName(), ioe.getMessage(), elapsedTime, stackTrace);
            stackTrace = ExceptionUtils.concatExceptionMessageAndStackTrace(ioe, stackTrace);

            loggerService.logExternalResponseEvent(
                    AX_EXTERNAL_RESPONSE,
                    applicationAlias,
                    ERROR_HTTP_STATUS,
                    null,
                    null,
                    null, getSourceIpAddress(),
                    LOG_GROUP_NAME,
                    null,
                    null,
                    null,
                    stackTrace);
        } else {

            String stackTrace = ExceptionUtils.getMessageAndStackTrace(ioe);

            loggerService.logExternalResponseEvent(
                    AX_EXTERNAL_RESPONSE,
                    applicationAlias,
                    ERROR_HTTP_STATUS,
                    null,
                    null,
                    null,
                    getSourceIpAddress(),
                    LOG_GROUP_NAME,
                    null,
                    null,
                    null,
                    stackTrace);

            this.log(configKey, PREFIX_LOG_ERROR, ioe.getClass().getSimpleName(), ioe.getMessage(), elapsedTime, N_A);
        }

        return ioe;
    }

    private String getSourceIpAddress() {
        return MDC.get(ContextConstants.IP_CLIENT);
    }

    private String getPrefixLogResponse() {
        return logPrefix == null ? PREFIX_LOG_RESPONSE_WITH_MS : logPrefix + PREFIX_LOG_RESPONSE_WITH_MS;
    }

    private String getResponseLogTemplate() {
        return logPrefix == null ? RESPONSE_LOG_TEMPLATE : logPrefix + RESPONSE_LOG_TEMPLATE;
    }

    private String getPrefixLogRequest() {
        return logPrefix == null ? PREFIX_LOG_REQUEST : logPrefix + PREFIX_LOG_REQUEST;
    }

    private String getRequestLogTemplate() {
        return logPrefix == null ? REQUEST_LOG_TEMPLATE : logPrefix + PREFIX_LOG_REQUEST;
    }

    private String getStatusReason(String reason, int status) {
        return EMPTY + status + (reason == null ? EMPTY : reason);
    }

    public LoggerService getLoggerService() {
        return loggerService;
    }

    public boolean isRequestToTrace() {
        return requestToTrace;
    }

    public void setRequestToTrace(boolean requestToTrace) {
        this.requestToTrace = requestToTrace;
    }

    public boolean isResponseToTrace() {
        return responseToTrace;
    }

    public void setResponseToTrace(boolean responseToTrace) {
        this.responseToTrace = responseToTrace;
    }
}
