package my.company.project.common.configuration;

import my.company.project.common.interceptor.LoggerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomWebMvcConfigurerAdapter implements WebMvcConfigurer {

    private final LoggerInterceptor requestLoggerInterceptor;

    @Autowired
    public CustomWebMvcConfigurerAdapter(LoggerInterceptor requestLoggerInterceptor) {
        this.requestLoggerInterceptor = requestLoggerInterceptor;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor((HandlerInterceptor) requestLoggerInterceptor);
    }

}
