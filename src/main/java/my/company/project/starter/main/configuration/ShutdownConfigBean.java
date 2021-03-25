package my.company.project.starter.main.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.jmx.Server;

public class ShutdownConfigBean {

    public void close() {
        LogManager.shutdown();
        Server.unregisterMBeans();
    }
}
