package tests.java.util;

import java.util.ArrayList;

public final class ArrayListTests {
    public static void main(String[] args) {
        final var arr = new ArrayList<Integer>();
        arr.add(123);

        if (arr.get(0) != 123)
            throw new RuntimeException();
    }
}
