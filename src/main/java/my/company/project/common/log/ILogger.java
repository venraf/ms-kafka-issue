package my.company.project.common.log;

interface ILogger {

    void debug(String message);

    void debug(Throwable throwable);

    void debug(String message, Throwable throwable);

    void debug(String message, Object... params);

    void info(String message);

    void info(Throwable throwable);

    void info(String message, Throwable throwable);

    void info(String message, Object... params);

    void warn(String message);

    void warn(Throwable throwable);

    void warn(String message, Throwable throwable);

    void warn(String message, Object... params);

    void error(String message);

    void error(Throwable throwable);

    void error(String message, Throwable throwable);

    void error(String message, Object... params);

    boolean isDebugEnabled();

    boolean isInfoEnabled();

    boolean isWarnEnabled();

    boolean isErrorEnabled();
}
