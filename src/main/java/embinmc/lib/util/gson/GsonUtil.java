package embinmc.lib.util.gson;

import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import embinmc.lib.util.ListUtil;
import embinmc.lib.util.logger.LoggerUtil;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

@SuppressWarnings({"unused"})
public final class GsonUtil {
    private static final Gson GSON = new GsonBuilder().create();
    private static final Logger LOGGER = LoggerUtil.getLogger();
    public static final List<String> JSON_EXTENSIONS = ListUtil.mutableOf(".json");

    public static JsonObject fromInputStream(InputStream inputStream, Charset charset) {
        return GsonUtil.GSON.fromJson(new InputStreamReader(inputStream, charset), JsonObject.class);
    }

    public static JsonObject fromInputStream(InputStream inputStream) {
        return GsonUtil.fromInputStream(inputStream, StandardCharsets.UTF_8);
    }

    public static String formattedJsonString(FormattingStyle style, JsonElement json) {
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(stringWriter);
        jsonWriter.setFormattingStyle(style);
        jsonWriter.setStrictness(Strictness.LENIENT);
        try {
            Streams.write(json, jsonWriter);
        } catch (IOException e) {
            GsonUtil.LOGGER.error("Failed to format json", e);
            return json.toString();
        }
        return stringWriter.toString();
    }

    public static String formattedJsonString(JsonElement json) {
        return GsonUtil.formattedJsonString(FormattingStyle.PRETTY, json);
    }

    public static String addJsonExtension(String extension) {
        String finalExtension = extension.startsWith(".") ? extension : "." + extension;
        GsonUtil.JSON_EXTENSIONS.add(finalExtension);
        return finalExtension;
    }
}
