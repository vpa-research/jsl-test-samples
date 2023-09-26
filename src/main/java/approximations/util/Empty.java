package approximations.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class Empty {
    public static final Consumer<Object> CONSUMER = o -> {
    };

    public static final Supplier<?> SUPPLIER = () -> null;
}
