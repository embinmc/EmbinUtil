package embinmc.lib.util.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"unused"})
public final class LoggerUtil {
    private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    public static Logger getLogger() {
        return LoggerFactory.getLogger(STACK_WALKER.getCallerClass());
    }

    public static Logger getLogger(String logger) {
        return LoggerFactory.getLogger(logger);
    }

    public static Logger getBasicLogger() {
        return LoggerFactory.getLogger(STACK_WALKER.getCallerClass().getSimpleName());
    }
}
