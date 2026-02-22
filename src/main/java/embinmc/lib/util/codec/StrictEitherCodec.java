package embinmc.lib.util.codec;

import com.mojang.serialization.*;

import java.util.stream.Stream;

public class StrictEitherCodec<T> extends MapCodec<T> {
    protected final String typeField;
    protected final MapCodec<T> typed, fuzzy;

    public StrictEitherCodec(String typeField, MapCodec<T> typed, MapCodec<T> fuzzy) {
        this.typeField = typeField;
        this.typed = typed;
        this.fuzzy = fuzzy;
    }

    @Override
    public <T1> Stream<T1> keys(DynamicOps<T1> dynamicOps) {
        return Stream.concat(this.typed.keys(dynamicOps), this.fuzzy.keys(dynamicOps))
                .distinct(); // ensure no duplicates
    }

    @Override
    public <T1> DataResult<T> decode(DynamicOps<T1> ops, MapLike<T1> mapLike) {
        return mapLike.get(this.typeField) != null ? this.typed.decode(ops, mapLike) : this.fuzzy.decode(ops, mapLike);
    }

    @Override
    public <T1> RecordBuilder<T1> encode(T t, DynamicOps<T1> dynamicOps, RecordBuilder<T1> recordBuilder) {
        return this.fuzzy.encode(t, dynamicOps, recordBuilder);
    }
}
