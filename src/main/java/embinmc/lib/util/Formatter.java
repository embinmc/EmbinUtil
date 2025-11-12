package embinmc.lib.util;

@FunctionalInterface
public interface Formatter<T> {
    String format(T value);
}
