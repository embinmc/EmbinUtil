package embinmc.lib.util;

import java.util.Objects;
import java.util.function.Consumer;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public final class Repeater {
    private int index;
    private int timesRepeated;
    private Consumer<Integer> action;

    private Repeater(int repeatAmount, Consumer<Integer> action, int timesRepeated) {
        this.index = repeatAmount;
        this.action = Objects.requireNonNull(action);
        this.timesRepeated = timesRepeated;
    }

    public static Repeater create(int repeatAmount, Consumer<Integer> action) {
        return new Repeater(repeatAmount, action, 0);
    }

    public void doAction() {
        if (this.canContinue()) {
            this.action.accept(this.index);
            this.index--;
            this.timesRepeated++;
        }
    }

    public boolean canContinue() {
        return this.index > 0;
    }

    public Repeater setRepeatAmount(int repeatAmount) {
        this.index = repeatAmount;
        return this;
    }

    public Repeater overrideAction(Consumer<Integer> action) {
        this.action = action;
        return this;
    }

    public int getTimesRepeated() {
        return this.timesRepeated;
    }
}
