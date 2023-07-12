package tests.java.util;

import java.util.OptionalDouble;

public final class OptionalDoubleTests {
    public static void main(String[] args) {
        final var o = OptionalDouble.of(5.d);

        if (o.orElse(2.d) != 5.d)
            throw new RuntimeException();
    }
}
