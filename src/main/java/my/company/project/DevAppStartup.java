package my.company.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Profile;
import org.springframework.jmx.support.RegistrationPolicy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Profile("DEV")
@SpringBootApplication(
        scanBasePackages = {"my.company.project"}
)
@EnableSwagger2
@EnableFeignClients(basePackages = {"my.company.project.common", "my.company.project.integration"})
//@EnableTransactionManagementLoggingEventRepository
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@ServletComponentScan
public class DevAppStartup extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DevAppStartup.class, args);
    }

}

