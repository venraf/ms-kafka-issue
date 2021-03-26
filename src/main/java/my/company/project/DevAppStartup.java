package my.company.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Profile;
import org.springframework.jmx.support.RegistrationPolicy;

@Profile("DEV")
@SpringBootApplication(
        scanBasePackages = {"my.company.project"}
)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@ServletComponentScan
public class DevAppStartup extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DevAppStartup.class, args);
    }

}

