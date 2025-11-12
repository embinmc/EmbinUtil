package embinmc.lib.util.function;

import java.util.Optional;

@FunctionalInterface
public interface OptionalFunction<A, R> {
    Optional<R> apply(A a);
}
