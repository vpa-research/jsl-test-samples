package approximations.java.util;

import java.util.OptionalDouble;

public final class OptionalDouble_Tests {

    public static int test_empty_0(final int execution) {
        final OptionalDouble o = OptionalDouble.empty();

        if (o.orElse(2.d) == 2.d)
            return 0;

        return -1;
    }

    public static int test_of_0(final int execution) {
        final OptionalDouble o = OptionalDouble.of(5.d);

        if (o.orElse(2.d) == 5.d)
            return 0;

        return -1;
    }

}
