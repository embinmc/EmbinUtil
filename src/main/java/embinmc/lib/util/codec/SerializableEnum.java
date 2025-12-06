package embinmc.lib.util.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

@SuppressWarnings({"unused"})
public interface SerializableEnum<T extends Enum<T>> {
    String asString();
    Codec<T> codec();

    default boolean isString(String string) {
        return this.asString().equals(string);
    }

    static <T extends Enum<T> & SerializableEnum<T>> Codec<T> getCodec(Class<T> clazz) {
        return Codec.STRING.comapFlatMap(s -> validate(s, clazz), T::asString);
    }

    private static <T extends Enum<T> & SerializableEnum<T>> DataResult<T> validate(String value, Class<T> clazz) {
        try {
            return DataResult.success(T.valueOf(clazz, value.toUpperCase()));
        } catch (IllegalArgumentException _) {
            return DataResult.error(() -> "\"%s\" is not a valid %s!".formatted(value, clazz.getSimpleName()));
        }
    }
}
