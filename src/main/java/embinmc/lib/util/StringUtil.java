package embinmc.lib.util;

@SuppressWarnings({"unused"})
public final class StringUtil {

    /**
     * Cuts a value off the end of a string
     * <p>
     * Returns <code>string</code> unmodified if <code>string</code> doesn't end with <code>end</code>
     *
     * @param string String to modify
     * @param end Value to remove from <code>string</code>
     * @return <code>string</code> with <code>end</code> removed from the end of it
     */
    public static String removeEndFromString(String string, String end) {
        if (string.endsWith(end)) return string.substring(0, string.length() - end.length());
        return string;
    }

    /**
     * Cuts a value off the start of a string
     * <p>
     * Returns <code>string</code> unmodified if <code>string</code> doesn't start with <code>end</code>
     *
     * @param string String to modify
     * @param start Value to remove from <code>string</code>
     * @return <code>string</code> with <code>start</code> removed from the start of it
     */
    public static String removeStartFromString(String string, String start) {
        if (string.startsWith(start)) return string.substring(start.length());
        return string;
    }
}
