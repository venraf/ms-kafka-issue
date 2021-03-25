package my.company.project.service.myfunctionality.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public SpringContext springContext() {
        return new SpringContext();
    }
}
