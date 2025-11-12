package embinmc.lib.util.function;

import java.util.Optional;

@FunctionalInterface
public interface POptionalFunction<A, R> {
    R apply(Optional<A> a);
}
