package embinmc.lib.util.codec;

import com.mojang.serialization.*;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public class FuzzyCodec<T> extends MapCodec<T> {
    protected final Collection<MapCodec<? extends T>> codecs;
    protected final Function<T, ? extends MapEncoder<? extends T>> encoderFunc;

    public FuzzyCodec(Collection<MapCodec<? extends T>> codecs, Function<T, ? extends MapEncoder<? extends T>> encoderFunc) {
        this.codecs = codecs;
        this.encoderFunc = encoderFunc;
    }

    @Override
    public <T1> Stream<T1> keys(DynamicOps<T1> dynamicOps) {
        return this.codecs.stream().flatMap(codec -> codec.keys(dynamicOps)).distinct();
    }

    @Override @SuppressWarnings({"unchecked"})
    public <T1> DataResult<T> decode(DynamicOps<T1> dynamicOps, MapLike<T1> mapLike) {
        for (MapDecoder<? extends T> decoder : this.codecs) {
            DataResult<? extends T> result = decoder.decode(dynamicOps, mapLike);
            if (result.result().isPresent()) {
                try {
                    return (DataResult<T>) result;
                } catch (ClassCastException _) {
                }
            }
        }
        return DataResult.error(() -> "Can't find matching codec");
    }

    @Override @SuppressWarnings({"unchecked"})
    public <T1> RecordBuilder<T1> encode(T t, DynamicOps<T1> dynamicOps, RecordBuilder<T1> recordBuilder) {
        MapEncoder<T> encoder = (MapEncoder<T>) this.encoderFunc.apply(t);
        return encoder.encode(t, dynamicOps, recordBuilder);
    }
}
