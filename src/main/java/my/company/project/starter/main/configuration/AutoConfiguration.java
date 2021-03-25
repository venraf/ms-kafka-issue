package my.company.project.starter.main.configuration;

import my.company.project.service.myfunctionality.common.config.SpringConfig;
import my.company.project.starter.main.filter.CustomLog4j2RequestFilter;
import my.company.project.starter.main.properties.ApplicationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties({ApplicationProperties.class})
@Import({
        SpringConfig.class,
})
public class AutoConfiguration {

    @Bean(destroyMethod = "close")
    public ShutdownConfigBean shutdownConfigBean() {
        return new ShutdownConfigBean();
    }


    @Bean
    public CustomLog4j2RequestFilter customLog4j2RequestFilter() {
        return new CustomLog4j2RequestFilter();
    }

}
