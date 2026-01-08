package embinmc.lib.util.function;

/**
 * Functionally identical to {@link Runnable}, but with a more clear name.
 * <p> {@link VoidMethod#execute()} is called instead of {@link Runnable#run()}.
 * @deprecated You should really just use {@link Runnable} instead.
 */
@FunctionalInterface @Deprecated
public interface VoidMethod {
    void execute();
}
