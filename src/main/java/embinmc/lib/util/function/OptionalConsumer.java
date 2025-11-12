package embinmc.lib.util.function;

import java.util.Optional;

public interface OptionalConsumer<A> {
    void accept(Optional<A> a);
}
