//package my.company.project.logging;
//
//import org.apache.logging.log4j.LogManager;
//
//public class MyLogger implements ILogger {
//
//    private final org.apache.logging.log4j.Logger LOGGER;
//
//    public MyLogger() {
//        this.LOGGER = LogManager.getLogger();
//    }
//
//    public MyLogger(Class c) {
//        this.LOGGER = LogManager.getLogger(c);
//    }
//
//    public MyLogger(String name) {
//        this.LOGGER = LogManager.getLogger(name);
//    }
//
//    public MyLogger(org.apache.logging.log4j.Logger logger) {
//        this.LOGGER = logger;
//    }
//
//    @Override
//    public void debug(String message) {
//        this.LOGGER.debug(message);
//    }
//
//    @Override
//    public void debug(Throwable throwable) {
//        this.LOGGER.debug(throwable);
//    }
//
//    @Override
//    public void debug(String message, Throwable throwable) {
//        this.LOGGER.debug(message, throwable);
//    }
//
//    @Override
//    public void debug(String message, Object... params) {
//        this.LOGGER.debug(message, params);
//    }
//
//    @Override
//    public void info(String message) {
//        this.LOGGER.info(message);
//    }
//
//    @Override
//    public void info(Throwable throwable) {
//        this.LOGGER.info(throwable);
//    }
//
//    @Override
//    public void info(String message, Throwable throwable) {
//        this.LOGGER.info(message, throwable);
//    }
//
//    @Override
//    public void info(String message, Object... params) {
//        this.LOGGER.info(message, params);
//    }
//
//    @Override
//    public void warn(String message) {
//        this.LOGGER.warn(message);
//    }
//
//    @Override
//    public void warn(Throwable throwable) {
//        this.LOGGER.warn(throwable);
//    }
//
//    @Override
//    public void warn(String message, Throwable throwable) {
//        this.LOGGER.warn(message, throwable);
//    }
//
//    @Override
//    public void warn(String message, Object... params) {
//        this.LOGGER.warn(message, params);
//    }
//
//    @Override
//    public void error(String message) {
//        this.LOGGER.error(message);
//    }
//
//    @Override
//    public void error(Throwable throwable) {
//        this.LOGGER.error(throwable);
//    }
//
//    @Override
//    public void error(String message, Throwable throwable) {
//        this.LOGGER.error(message, throwable);
//    }
//
//    @Override
//    public void error(String message, Object... params) {
//        this.LOGGER.error(message, params);
//    }
//
//    @Override
//    public boolean isDebugEnabled() {
//        return this.LOGGER.isDebugEnabled();
//    }
//
//    @Override
//    public boolean isInfoEnabled() {
//        return this.LOGGER.isInfoEnabled();
//    }
//
//    @Override
//    public boolean isWarnEnabled() {
//        return this.LOGGER.isWarnEnabled();
//    }
//
//    @Override
//    public boolean isErrorEnabled() {
//        return this.LOGGER.isErrorEnabled();
//    }
//}
