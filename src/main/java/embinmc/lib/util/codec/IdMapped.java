package embinmc.lib.util.codec;

import com.mojang.serialization.Codec;
import embinmc.lib.util.annotation.NotNull;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class IdMapped<Id, Element> {
    private final Map<Id, Element> idToElement = new LinkedHashMap<>(16);
    private final Map<Element, Id> elementToId = new LinkedHashMap<>(16);

    public Codec<Element> codec(Codec<Id> idCodec) {
        return CodecUtil.idResolvedCodec(idCodec, this.idToElement::get, this.elementToId::get);
    }

    public Collection<Element> elements() {
        return this.idToElement.values();
    }

    public IdMapped<Id, Element> put(final @NotNull Id id, final @NotNull Element element) {
        Objects.requireNonNull(id, "id is null");
        Objects.requireNonNull(element, "element is null");
        this.idToElement.put(id, element);
        this.elementToId.put(element, id);
        return this;
    }
}
