package my.company.project.starter.main.filter;

import org.apache.logging.log4j.ThreadContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CustomLog4j2RequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String correlationId = httpServletRequest.getHeader("X-CorrelationId");
        String requestUri = httpServletRequest.getRequestURI();
        if (correlationId != null) ThreadContext.put("X-CorrelationId", correlationId);
        if (requestUri != null) ThreadContext.put("Request-Uri", requestUri);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
