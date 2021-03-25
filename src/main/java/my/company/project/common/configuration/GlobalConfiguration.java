package my.company.project.common.configuration;

import my.company.project.common.interceptor.LoggerInterceptor;
import my.company.project.common.interceptor.configuration.LoggerInterceptorProperties;
import my.company.project.common.logging.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfiguration {

    @Autowired
    @Bean
    public LoggerInterceptor requestLoggerInterceptor(
            LoggingProperties loggingProperties,
            LoggerService loggerService,
            LoggerInterceptorProperties loggerInterceptorProperties) {
        return new LoggerInterceptor(loggingProperties, loggerService, loggerInterceptorProperties);
    }

//    @Bean
//    public WebMvcConfigurer webMvcConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addInterceptors(InterceptorRegistry registry) {
//                registry.addInterceptor((HandlerInterceptor) requestLoggerInterceptor());
//            }
//        };
//    }
}
