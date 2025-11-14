package embinmc.lib.util;

import java.util.Random;
import java.util.random.RandomGenerator;

@SuppressWarnings({"unused"})
public final class MathUtil {
    public static final Random RANDOM = Random.from(RandomGenerator.getDefault());

    public static boolean isIntBetween(int num, int min, int max) {
        return (num >= min) && (num <= max);
    }

    public static int powInt(int a, int b) {
        final int[] base = {1};
        Util.repeat(b, _ -> base[0] = base[0] * a);
        return base[0];
    }

    /**
     * @param min Value is inclusive
     * @param max Value is inclusive
     */
    public static int randomInt(int min, int max) {
        return MathUtil.RANDOM.nextInt(min, max + 1);
    }

    public static double lerp(double amount, double min, double max) {
        return ((max - min) * amount) + min;
    }

    public static double lerpOf(double value, double min, double max) {
        return (value - min) / (max - min);
    }

    public static boolean isPositive(int value) {
        return value > 0;
    }

    public static boolean isNegative(int value) {
        return value < 0;
    }

    public static boolean isPositive(double value) {
        return value > 0.0D;
    }

    public static boolean isNegative(double value) {
        return value < 0.0D;
    }

    public static int floor(double value) {
        return (int) Math.floor(value);
    }

    public static int modulo(int value, int mod) {
        int modded = value % mod;
        if (modded < 0) modded = modded + mod;
        return modded;
    }
}
