package my.company.project.common.interceptor;

import my.company.project.common.configuration.LoggingProperties;
import my.company.project.common.log.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

//Not used
//@Component
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final String UTF_8 = "UTF-8";
    private static final String HTTP_REQUEST_URI_METHOD_HEADERS_BODY = "Http request: uri=[{}], method=[{}], headers=[{}], body=[{}]";
    private static final String HTTP_RESPONSE_URI_METHOD_HEADERS_BODY = "Http response: status-code=[{}], status-text=[{}], headers=[{}], body=[{}]";

    private final Logger log = new Logger(this.getClass());

    private LoggingProperties loggingProperties;

    public LoggingRequestInterceptor(LoggingProperties LoggingProperties) {
        this.loggingProperties = LoggingProperties;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {

        if (loggingProperties.isHttpLoggingRequestEnabled()) {
            String bodyToPrint = null;
            if (body != null) {
                bodyToPrint = new String(body, UTF_8);

            }
            log.info(HTTP_REQUEST_URI_METHOD_HEADERS_BODY, request.getURI(), request.getMethod(),
                    request.getHeaders(), bodyToPrint);
        }
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {

        if (loggingProperties.isHttpLoggingResponseEnabled()) {

            String bodyToPrint = null;

            if (response != null) {

                bodyToPrint = convertOctetStream(response, bodyToPrint);
            }

            log.info(HTTP_RESPONSE_URI_METHOD_HEADERS_BODY, response.getStatusCode(), response.getStatusText(),
                    response.getHeaders(), bodyToPrint);
        }

    }

    private String convertOctetStream(ClientHttpResponse response, String bodyToPrint)
            throws UnsupportedEncodingException, IOException {
        StringBuilder inputStringBuilder = null;

        if (response != null) {
            HttpHeaders headers = response.getHeaders();

            if (headers != null) {
                MediaType contentType = headers.getContentType();
                BufferedReader bufferedReader = null;
                if (contentType != null) {
                    try {
                        if (!contentType.equals(MediaType.APPLICATION_OCTET_STREAM)) {
                            inputStringBuilder = new StringBuilder();
                            bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), UTF_8));
                            String line = bufferedReader.readLine();

                            while (line != null) {
                                inputStringBuilder.append(line);
                                inputStringBuilder.append('\n');
                                line = bufferedReader.readLine();
                            }

                            bodyToPrint = inputStringBuilder.toString();
                        }
                    } catch (IOException ex) {
                        throw ex;
                    } finally {
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException ex) {
                                throw ex;
                            }
                        }
                    }
                }
            }
        }

        return bodyToPrint;
    }

}