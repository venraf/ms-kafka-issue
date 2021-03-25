package my.company.project.common.utils;

import org.springframework.util.ObjectUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {

    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String SQUARE_BRACKET_OPEN = "[";
    public static final String SQUARE_BRACKET_CLOSED = "]";

    public static class Constants {
        public static final int STARTING_INDEX = 0;
        public static final int STACKTRACE_SIZE_750_CHAR = 750;
        public static final int STACKTRACE_SIZE_1000_CHAR = 1000;
        public static final int MAX_STACKTRACE_SIZE = STACKTRACE_SIZE_750_CHAR;
    }

    public static String getStackTraceAsString(Throwable e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();
        stackTrace = stackTrace.substring(Constants.STARTING_INDEX, Constants.MAX_STACKTRACE_SIZE);
        return stackTrace;
    }

    public static String concatExceptionMessageAndStackTrace(Throwable e, String stackTrace) {
        if (!ObjectUtils.isEmpty(e.getMessage())) {
            stackTrace = new StringBuilder(EMPTY)
                    .append(e.getMessage())
                    .append(SPACE)
                    .append(SQUARE_BRACKET_OPEN)
                    .append(stackTrace)
                    .append(SQUARE_BRACKET_CLOSED)
                    .toString();
        }
        return stackTrace;
    }

    public static String getMessageAndStackTrace(Throwable e) {
        String stackTrace = ExceptionUtils.getStackTraceAsString(e);
        stackTrace = ExceptionUtils.concatExceptionMessageAndStackTrace(e, stackTrace);
        return stackTrace;
    }

}
