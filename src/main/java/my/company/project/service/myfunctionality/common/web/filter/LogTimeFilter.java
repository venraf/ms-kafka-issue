package my.company.project.service.myfunctionality.common.web.filter;

import my.company.project.common.log.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component
@Order(1)
public class LogTimeFilter implements Filter {

    private final Logger log = new Logger(this.getClass());
    public static final String QUESTION_MARK = "?";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        long duration = System.currentTimeMillis() - startTime;

        StringBuilder b = new StringBuilder("");
        if (servletRequest instanceof HttpServletRequest) {
            String url = ((HttpServletRequest) servletRequest).getRequestURL().toString();
            String queryString = ((HttpServletRequest) servletRequest).getQueryString();
            if (url != null) {
                b.append(url);
                if (queryString != null) {
                    b.append(QUESTION_MARK);
                    b.append(queryString);
                }
            }

        }
        log.info("#ElapsedTime: {},{}", b.toString(), duration);
    }

    @Override
    public void destroy() {
    }

}
