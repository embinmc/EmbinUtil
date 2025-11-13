package embinmc.lib.util.function;

import java.util.Optional;

@FunctionalInterface
public interface OptionalBiFunction<A, B, R> {
    Optional<R> apply(A a, B b);
}
