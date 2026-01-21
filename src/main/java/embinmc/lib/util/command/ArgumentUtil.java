package embinmc.lib.util.command;

import com.mojang.brigadier.StringReader;
import embinmc.lib.util.Util;
import it.unimi.dsi.fastutil.chars.CharPredicate;

public final class ArgumentUtil {
    private ArgumentUtil() {
        Util.enforceNoInstance(ArgumentUtil.class);
    }

    public static String greedyRead(final StringReader reader, final CharPredicate charPredicate) {
        final int start = reader.getCursor();
        while (reader.canRead() && charPredicate.test(reader.peek())) {
            reader.skip();
        }
        return reader.getString().substring(start, reader.getCursor());
    }

    public static String greedyRead(final StringReader reader) {
        return ArgumentUtil.greedyRead(reader, c -> c != ' ');
    }
}
