package embinmc.lib.util.codec;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.*;
import embinmc.lib.util.Util;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings({"unused"})
public final class CodecUtil {
    public static final Codec<Unit> DFU_UNIT = MapCodec.unitCodec(Unit.INSTANCE);
    public static final Codec<Integer> POSITIVE_INT = rangedInt(1, Integer.MAX_VALUE, v -> "Value \"" + v + "\" must be positive");
    public static final Codec<Integer> NON_NEGATIVE_INT = rangedInt(0, Integer.MAX_VALUE, v -> "Value \"" + v + "\" cannot be negative");
    public static final Codec<Integer> NON_POSITIVE_INT = rangedInt(Integer.MIN_VALUE, 0, v -> "Value \"" + v + "\" cannot be positive");
    public static final Codec<Integer> NEGATIVE_INT = rangedInt(Integer.MIN_VALUE, -1, v -> "Value \"" + v + "\" must be negative");
    public static final Codec<JsonElement> JSON = convertOpsToCodec(JsonOps.INSTANCE);

    public static Codec<Integer> rangedInt(int min, int max, Function<Integer, String> failMessage) {
        return Codec.INT.validate(
                value -> value >= min && value <= max ?
                        DataResult.success(value) :
                        DataResult.error(() -> failMessage.apply(value))
        );
    }

    @Deprecated
    public static Codec<Integer> rangedInt(int min, int max) {
        return rangedInt(min, max, value -> "Value must be within range [" + min + ";" + max + "], got: " + value);
    }

    public static <T> Codec<List<T>> listOrSingle(Codec<T> entryCodec) {
        return Codec.either(entryCodec.listOf(), entryCodec).xmap(
            either -> either.map(Util::itself, List::of),
            list -> list.size() == 1 ? Either.right(list.getFirst()) : Either.left(list)
        );
    }

    public static <T> Codec<List<T>> listOrSingle(Codec<T> entryCodec, int maxSize, boolean allowEmpty) {
        return Codec.either(entryCodec.listOf(allowEmpty ? 0 : 1, maxSize), entryCodec).xmap(
                either -> either.map(Util::itself, List::of),
                list -> list.size() == 1 ? Either.right(list.getFirst()) : Either.left(list)
        );
    }

    public static <T> MapCodec<T> withEitherKey(Codec<T> codec, String mainKey, String secondaryKey) {
        return Codec.mapEither(codec.fieldOf(secondaryKey), codec.fieldOf(mainKey)).xmap(Either::unwrap, Either::right);
    }

    public static <T> MapCodec<T> withEitherOptionalKey(Codec<T> codec, String mainKey, String secondaryKey, T defaultValue) {
        return CodecUtil.withEitherKey(codec, mainKey, secondaryKey).orElseGet(() -> defaultValue);
    }

    public static <T> MapCodec<Optional<T>> withEitherOptionalKey(Codec<T> codec, String mainKey, String secondaryKey) {
        return CodecUtil.withEitherKey(codec, mainKey, secondaryKey)
                .flatXmap(v -> DataResult.success(Optional.of(v)), CodecUtil::validateExistence)
                .orElseGet(Optional::empty);
    }

    public static <T> Codec<List<T>> nonEmptyList(Codec<List<T>> codec) {
        return codec.validate(list -> list.isEmpty() ? DataResult.error(() -> "List cannot be empty") : DataResult.success(list));
    }

    public static <K, V> Codec<Map<K, V>> nonEmptyMap(Codec<Map<K, V>> codec) {
        return codec.validate(map -> map.isEmpty() ? DataResult.error(() -> "Map cannot be empty") : DataResult.success(map));
    }

    public static <T> Codec<T> convertOpsToCodec(DynamicOps<T> ops) {
        return Codec.PASSTHROUGH.xmap(dynamic -> dynamic.convert(ops).getValue(), t -> new Dynamic<>(ops, t));
    }

    public static <O, T> MapCodec<O> specializedOptional(MapCodec<Optional<T>> mapCodec, Function<Optional<T>, O> to, Function<O, Optional<T>> from) {
        return mapCodec.xmap(to, from);
    }

    public static <O, T> MapCodec<O> specializedOptional(MapCodec<Optional<T>> mapCodec, Function<T, O> to, Function<O, T> from, Supplier<O> empty, Predicate<O> ifEmpty) {
        return CodecUtil.specializedOptional(mapCodec,
                optional -> optional.map(to).orElseGet(empty),
                special -> ifEmpty.test(special) ? Optional.of(from.apply(special)) : Optional.empty()
        );
    }

    public static MapCodec<OptionalInt> optionalInt(MapCodec<Optional<Integer>> codec) {
        return CodecUtil.specializedOptional(codec, OptionalInt::of, OptionalInt::getAsInt, OptionalInt::empty, OptionalInt::isEmpty);
    }

    public static MapCodec<OptionalLong> optionalLong(MapCodec<Optional<Long>> codec) {
        return CodecUtil.specializedOptional(codec, OptionalLong::of, OptionalLong::getAsLong, OptionalLong::empty, OptionalLong::isEmpty);
    }

    public static MapCodec<OptionalDouble> optionalDouble(MapCodec<Optional<Double>> codec) {
        return CodecUtil.specializedOptional(codec, OptionalDouble::of, OptionalDouble::getAsDouble, OptionalDouble::empty, OptionalDouble::isEmpty);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static <T> DataResult<T> validateExistence(Optional<T> value) {
        if (value.isPresent()) return DataResult.success(value.orElseThrow());
        return DataResult.error(() -> "No value");
    }

    public static <Id, Element> Codec<Element> idResolvedCodec(final Codec<Id> value, Function<Id, Element> fromId, Function<Element, Id> toId) {
        return value.flatXmap(id -> {
            Element element = fromId.apply(id);
            return element != null ? DataResult.success(element) : DataResult.error(() -> "Unknown id: " + id);
        }, element -> {
            Id id = toId.apply(element);
            return id != null ? DataResult.success(id) : DataResult.error(() -> "Can't get id of element: " + element);
        });
    }
}
