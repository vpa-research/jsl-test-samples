package tests.java.util;

//import generated.java.lang.StringBuilder;

public final class StringBuilder_Tests {

    public static void main(String[] args) {
        System.out.println("test_StringBuilder_0");
        System.out.println(test_StringBuilder_0(0));

        System.out.println("\ntest_StringBuilder_1");
        System.out.println(test_StringBuilder_1(0));
        System.out.println(test_StringBuilder_1(1));

        System.out.println("\ntest_StringBuilder_2");
        System.out.println(test_StringBuilder_2(0));
        System.out.println(test_StringBuilder_2(1));

        System.out.println("\ntest_StringBuilder_3");
        System.out.println(test_StringBuilder_3(0));

        System.out.println("\ntest_append_0");
        System.out.println(test_append_0(0));
        System.out.println(test_append_0(1));
        System.out.println(test_append_0(2));
        System.out.println(test_append_0(3));
    }


    // internal variables

    // constructors

    // StringBuilderAutomaton::StringBuilder
    public static int test_StringBuilder_0(final int execution) {

        StringBuilder stringBuilder = new StringBuilder();
        if (execution == 0) {
            if (stringBuilder.length() == 0)
                return 0;
            else
                return -1;
        }
        return -1;
    }


    // StringBuilderAutomaton::StringBuilder (CharSequence)
    public static int test_StringBuilder_1(final int execution) {

        CharSequence sequence = "12";

        if (execution == 0) {
            StringBuilder stringBuilder = new StringBuilder(sequence);
            if (stringBuilder != null && stringBuilder.length() == 2 && stringBuilder.charAt(0) == '1' && stringBuilder.charAt(1) == '2')
                return 0;
            else
                return -1;
        }

        if (execution == 1) {
            sequence = null;
            try {
                StringBuilder stringBuilder = new StringBuilder(sequence);
            } catch (NullPointerException e) {
                return 1;
            }
            return -1;
        }
        return -1;
    }


    // StringBuilderAutomaton::StringBuilder (String)
    public static int test_StringBuilder_2(final int execution) {

        String sequence = "12";

        if (execution == 0) {
            StringBuilder stringBuilder = new StringBuilder(sequence);
            if (stringBuilder != null && stringBuilder.length() == 2 && stringBuilder.charAt(0) == '1' && stringBuilder.charAt(1) == '2')
                return 0;
            else
                return -1;
        }

        if (execution == 1) {
            sequence = null;
            try {
                StringBuilder stringBuilder = new StringBuilder(sequence);
            } catch (NullPointerException e) {
                return 1;
            }
            return -1;
        }
        return -1;
    }


    // StringBuilderAutomaton::StringBuilder (int)
    public static int test_StringBuilder_3(final int execution) {

        int capacity = 3;
        StringBuilder stringBuilder = new StringBuilder(capacity);

        if (execution == 0) {
            if (stringBuilder.length() == 0)
                return 0;
            else
                return -1;
        }

        return -1;
    }


    // static methods

    // methods

    // StringBuilderAutomaton::append (CharSequence)
    public static int test_append_0(final int execution) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(1);

        if (execution == 0) {
            if (stringBuilder.length() == 1 && stringBuilder.charAt(0) == '1')
                return 0;
            else
                return -1;
        }

        stringBuilder.append(23);
        if (execution == 1) {
            if (stringBuilder.length() == 3 && stringBuilder.charAt(0) == '1' && stringBuilder.charAt(2) == '3')
                return 0;
            else
                return -1;
        }

        stringBuilder.append(Integer.MAX_VALUE);
        if (execution == 2) {
            if (stringBuilder.length() == 13 && stringBuilder.charAt(10) == '6' && stringBuilder.charAt(12) == '7')
                return 0;
            else
                return -1;
        }

        stringBuilder.append(-10);
        System.out.println(stringBuilder);

        if (execution == 3) {
            if (stringBuilder.length() == 16 && stringBuilder.charAt(13) == '-' && stringBuilder.charAt(15) == '0')
                return 0;
            else
                return -1;
        }

        return -1;
    }


    // StringBuilderAutomaton::append (CharSequence, int, int)
    public static int test_append_1(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::append (Object)
    public static int test_append_2(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::append (String)
    public static int test_append_3(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::append (StringBuffer)
    public static int test_append_4(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::append (boolean)
    public static int test_append_5(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::append (char)
    public static int test_append_6(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::append (char[])
    public static int test_append_7(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::append (char[], int, int)
    public static int test_append_8(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::append (double)
    public static int test_append_9(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::append (float)
    public static int test_append_10(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::append (int)
    public static int test_append_11(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::append (long)
    public static int test_append_12(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::appendCodePoint (int)
    public static int test_appendCodePoint_0(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::compareTo (StringBuilder)
    public static int test_compareTo_0(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::delete (int, int)
    public static int test_delete_0(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::deleteCharAt (int)
    public static int test_deleteCharAt_0(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::indexOf (String)
    public static int test_indexOf_0(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::indexOf (String, int)
    public static int test_indexOf_1(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::insert (int, CharSequence)
    public static int test_insert_0(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::insert (int, CharSequence, int, int)
    public static int test_insert_1(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::insert (int, Object)
    public static int test_insert_2(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::insert (int, String)
    public static int test_insert_3(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::insert (int, boolean)
    public static int test_insert_4(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::insert (int, char)
    public static int test_insert_5(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::insert (int, char[])
    public static int test_insert_6(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::insert (int, char[], int, int)
    public static int test_insert_7(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::insert (int, double)
    public static int test_insert_8(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::insert (int, float)
    public static int test_insert_9(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::insert (int, int)
    public static int test_insert_10(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::insert (int, long)
    public static int test_insert_11(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::lastIndexOf (String)
    public static int test_lastIndexOf_0(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::lastIndexOf (String, int)
    public static int test_lastIndexOf_1(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::replace (int, int, String)
    public static int test_replace_0(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::reverse
    public static int test_reverse_0(final int execution) {
        return -1;
    }


    // StringBuilderAutomaton::toString
    public static int test_toString_0(final int execution) {
        return -1;
    }

}
