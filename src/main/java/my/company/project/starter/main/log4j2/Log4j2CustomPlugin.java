package my.company.project.starter.main.log4j2;

import my.company.project.common.log.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;

import java.util.Properties;

@Plugin(name = "company", category = "Lookup")
public class Log4j2CustomPlugin implements StrLookup {

    private final Properties properties;
    private final Logger logger;

    public Log4j2CustomPlugin() {
        this.properties = new Properties();
        this.logger = new Logger(this.getClass());
        try {
            this.properties.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (Throwable e) {
            this.logger.error(">>> Failed to load properties from 'application.properties'");
            this.logger.error(">>> Keep in mind that this file is mandatory in order to have log4j2 running");
            this.logger.error(e);
        }
    }

    @Override
    public String lookup(String s) {
        return this.properties.getProperty(s);
    }

    @Override
    public String lookup(LogEvent logEvent, String s) {
        return this.properties.getProperty(s);
    }
}
