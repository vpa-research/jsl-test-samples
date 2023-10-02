package approximations.java.util;

import java.util.OptionalDouble;

public final class OptionalDouble_Tests {

    public static int test_of_0(final int execution) {
        final var o = OptionalDouble.of(5.d);

        if (o.orElse(2.d) == 5.d)
            return 0;

        return -1;
    }

}
