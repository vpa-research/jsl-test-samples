package approximations.java.util;

import approximations.Test;

import java.util.ArrayList;

public final class ArrayList_Tests {

    @Test(executionMax = 4, exceptions = java.lang.IndexOutOfBoundsException.class)
    public static int test_get_0(int execution) {
        ArrayList<Object> list = new ArrayList<>();
        if (execution == 0) {
            // IOB
            if (list.get(5) == null)
                return -1;
        }

        Object stub = new Object();
        for (int i = 0; i < 3; i++)
            list.add(new Object());

        if (execution == 1) {
            if (list.size() == 3)
                return 1;
            else
                return -1;
        }

        list.add(0, stub);
        list.add(stub);

        if (execution == 2) {
            if (list.size() == 5)
                return 2;
            else
                return -1;
        }

        if (execution == 3) {
            if (list.get(0) == list.get(list.size() - 1))
                return 3;
            else
                return -1;
        }

        if (execution == 4) {
            if (list.get(0) != list.get(1))
                return 4;
            else
                return -1;
        }

        return 0;
    }

}
