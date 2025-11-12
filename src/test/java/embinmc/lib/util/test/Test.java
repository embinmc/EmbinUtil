package embinmc.lib.util.test;

import embinmc.lib.util.MathUtil;
import embinmc.lib.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Test {
    public static void main(String[] args) {
        final int testSize = 10;
        List<Integer> test = new ArrayList<>(testSize);
        Util.repeat(testSize, i -> {
            System.out.println("Index: " + i);
            test.add(i);
        });
        int index = 1;
        for (int i : test) {
            System.out.println("Checking index: " + index);
            if (i != index) {
                throw new RuntimeException("Failed at index " + index);
            }
            index++;
        }

        test(() -> MathUtil.isIntBetween(5, 4, 6));
        test(() -> MathUtil.powInt(2, 15) == 32768);
        test(() -> MathUtil.powInt(2, 16) == 65536);
        test(() -> MathUtil.powInt(2, 8) == 256);
        test(() -> MathUtil.powInt(10, 5) == 100_000);

        System.out.println("test success maybe");
    }

    private static void test(Supplier<Boolean> test) {
        if (test.get()) return;
        throw new RuntimeException("Test failed!");
    }
}
