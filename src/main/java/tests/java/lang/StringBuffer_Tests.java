package tests.java.lang;


import java.util.ArrayList;
import java.util.Date;

public final class StringBuffer_Tests {

    private static final int MAX_CODE_POINT = 1114111;
    private static final int MIN_CODE_POINT = 0;

    public static void main(String[] args) {
        System.out.println("test_StringBuffer_0");
        System.out.println(test_StringBuffer_0(0));

        System.out.println("\ntest_StringBuffer_1");
        System.out.println(test_StringBuffer_1(0));
        System.out.println(test_StringBuffer_1(1));

        System.out.println("\ntest_StringBuffer_2");
        System.out.println(test_StringBuffer_2(0));
        System.out.println(test_StringBuffer_2(1));

        System.out.println("\ntest_StringBuffer_3");
        System.out.println(test_StringBuffer_3(0));

        System.out.println("\ntest_append_10");
        System.out.println(test_append_10(0));
        System.out.println(test_append_10(1));
        System.out.println(test_append_10(2));
        System.out.println(test_append_10(3));


        System.out.println("\ntest_append_11");
        System.out.println(test_append_11(0));
        System.out.println(test_append_11(1));
        System.out.println(test_append_11(2));
        System.out.println(test_append_11(3));


        System.out.println("\ntest_append_12");
        System.out.println(test_append_12(0));
        System.out.println(test_append_12(1));
        System.out.println(test_append_12(2));
        System.out.println(test_append_12(3));


        System.out.println("\ntest_append_0");
        System.out.println(test_append_0(0));
        System.out.println(test_append_0(1));
        System.out.println(test_append_0(2));
        System.out.println(test_append_0(3));

        System.out.println("\ntest_append_1");
        System.out.println(test_append_1(0));
        System.out.println(test_append_1(1));
        System.out.println(test_append_1(2));
        System.out.println(test_append_1(3));


        System.out.println("\ntest_append_3");
        System.out.println(test_append_3(0));
        System.out.println(test_append_3(1));
        System.out.println(test_append_3(2));
        System.out.println(test_append_3(3));


        System.out.println("\ntest_append_4");
        System.out.println(test_append_4(0));
        System.out.println(test_append_4(1));
        System.out.println(test_append_4(2));
        System.out.println(test_append_4(3));
    }

    // internal variables

    // constructors

    // StringBufferAutomaton::StringBuffer
    public static int test_StringBuffer_0(final int execution) {
        if (execution == 0) {
            StringBuffer stringBuffer = new StringBuffer();
            if (stringBuffer.length() == 0)
                return 0;
            else
                return -1;
        }
        return -1;
    }


    // StringBufferAutomaton::StringBuffer (CharSequence)
    public static int test_StringBuffer_1(final int execution) {

        CharSequence sequence = "12";

        if (execution == 0) {
            StringBuffer stringBuffer = new StringBuffer(sequence);
            if (stringBuffer != null && stringBuffer.length() == 2 && stringBuffer.charAt(0) == '1' && stringBuffer.charAt(1) == '2')
                return 0;
            else
                return -1;
        }

        if (execution == 1) {
            sequence = null;
            try {
                StringBuffer stringBuffer = new StringBuffer(sequence);
            } catch (NullPointerException e) {
                return 1;
            }
            return -1;
        }
        return -1;
    }


    // StringBufferAutomaton::StringBuffer (String)
    public static int test_StringBuffer_2(final int execution) {
        String sequence = "12";

        if (execution == 0) {
            StringBuffer stringBuffer = new StringBuffer(sequence);
            if (stringBuffer != null && stringBuffer.length() == 2 && stringBuffer.charAt(0) == '1' && stringBuffer.charAt(1) == '2')
                return 0;
            else
                return -1;
        }

        if (execution == 1) {
            sequence = null;
            try {
                StringBuffer stringBuffer = new StringBuffer(sequence);
            } catch (NullPointerException e) {
                return 1;
            }
            return -1;
        }
        return -1;
    }


    // StringBufferAutomaton::StringBuffer (int)
    public static int test_StringBuffer_3(final int execution) {
        int capacity = 4;
        StringBuffer stringBuffer = new StringBuffer(capacity);

        if (execution == 0) {
            if (stringBuffer.length() == 0)
                return 0;
            else
                return -1;
        }

        return -1;
    }


    // static methods

    // methods

    // StringBufferAutomaton::append (CharSequence)
    public static int test_append_0(final int execution) {
        StringBuffer stringBuffer = new StringBuffer();
        CharSequence sequence_1 = null;
        stringBuffer.append(sequence_1);
        if (execution == 0) {
            if (stringBuffer.length() == 4 && stringBuffer.toString().equals("null"))
                return 0;
            else
                return -1;
        }

        CharSequence sequence_2 = "anystring1";
        stringBuffer.append(sequence_2);
        if (execution == 1) {
            if (stringBuffer.length() == 14 && stringBuffer.charAt(12) == 'g')
                return 1;
            else
                return -1;
        }

        CharSequence sequence_3 = "слово";
        stringBuffer.append(sequence_3);
        if (execution == 2) {
            if (stringBuffer.length() == 19 && stringBuffer.charAt(17) == sequence_3.charAt(3))
                return 2;
            else
                return -1;
        }

        CharSequence sequence_4 = new String(new int[]{80_000}, 0, 1);
        stringBuffer.append(sequence_4);
        if (execution == 3) {
            if (stringBuffer.length() == 21 && stringBuffer.charAt(12) == 'g')
                return 3;
            else
                return -1;
        }

        return -1;
    }


    // StringBufferAutomaton::append (CharSequence, int, int)
    public static int test_append_1(final int execution) {
        StringBuffer stringBuffer = new StringBuffer("test");
        CharSequence sequence_1 = null;
        stringBuffer.append(sequence_1, 1, 3);
        if (execution == 0) {
            if (stringBuffer.length() == 6 && stringBuffer.charAt(5) == 'l')
                return 0;
            else
                return -1;
        }

        CharSequence sequence_2 = "anystring1";
        stringBuffer.append(sequence_2, 0, 0);
        if (execution == 1) {
            if (stringBuffer.length() == 6 && stringBuffer.charAt(5) == 'l')
                return 1;
            else
                return -1;
        }

        CharSequence sequence_3 = "anystring1";
        if (execution == 2) {
            try {
                stringBuffer.append(sequence_3, 0, 1000);
            } catch (IndexOutOfBoundsException e) {
                return 2;
            }
            return -1;
        }

        CharSequence sequence_4 = new String(new int[]{80_000}, 0, 1);
        stringBuffer.append(sequence_4, 0, 1);
        if (execution == 3) {
            if (stringBuffer.length() == 7)
                return 3;
            else
                return -1;
        }

        return -1;
    }


    // StringBufferAutomaton::append (Object)
    public static int test_append_2(final int execution) {
        StringBuffer stringBuffer = new StringBuffer();
        Object sequence_1 = null;
        stringBuffer.append(sequence_1);
        if (execution == 0) {
            if (stringBuffer.length() == 4 && stringBuffer.toString().equals("null"))
                return 0;
            else
                return -1;
        }

        Date date = new Date();
        Object sequence_2 = date;
        stringBuffer.append(sequence_2);
        if (execution == 1) {
            if (stringBuffer.toString().equals("null" + date))
                return 1;
            else
                return -1;
        }

        Object sequence_3 = "слово";
        stringBuffer.append(sequence_3);
        if (execution == 2) {
            if (stringBuffer.charAt(stringBuffer.length() - 1) == sequence_3.toString().charAt(4))
                return 2;
            else
                return -1;
        }

        ArrayList<Integer> a = new ArrayList<>();
        a.add(2);
        a.add(-200);
        Object sequence_4 = a;
        stringBuffer.append(sequence_4);
        if (execution == 3) {
            if (stringBuffer.charAt(stringBuffer.length() - 1) == ']')
                return 3;
            else
                return -1;
        }

        return -1;
    }


    // StringBufferAutomaton::append (String)
    public static int test_append_3(final int execution) {
        StringBuffer stringBuffer = new StringBuffer();
        String sequence_1 = null;
        stringBuffer.append(sequence_1);
        if (execution == 0) {
            if (stringBuffer.length() == 4 && stringBuffer.toString().equals("null"))
                return 0;
            else
                return -1;
        }

        String sequence_2 = "anystring1";
        stringBuffer.append(sequence_2);
        if (execution == 1) {
            if (stringBuffer.length() == 14 && stringBuffer.charAt(12) == 'g')
                return 1;
            else
                return -1;
        }

        String sequence_3 = "слово";
        stringBuffer.append(sequence_3);
        if (execution == 2) {
            if (stringBuffer.length() == 19 && stringBuffer.charAt(17) == sequence_3.charAt(3))
                return 2;
            else
                return -1;
        }

        String sequence_4 = new String(new int[]{80_000}, 0, 1);
        stringBuffer.append(sequence_4);
        if (execution == 3) {
            if (stringBuffer.length() == 21 && stringBuffer.charAt(12) == 'g')
                return 3;
            else
                return -1;
        }

        return -1;
    }


    // StringBufferAutomaton::append (StringBuffer)
    public static int test_append_4(final int execution) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer sequence_1 = null;
        stringBuffer.append(sequence_1);
        if (execution == 0) {
            if (stringBuffer.length() == 4 && stringBuffer.toString().equals("null"))
                return 0;
            else
                return -1;
        }

        StringBuffer sequence_2 = new StringBuffer("anystring1");
        stringBuffer.append(sequence_2);
        if (execution == 1) {
            if (stringBuffer.length() == 14 && stringBuffer.charAt(12) == 'g')
                return 1;
            else
                return -1;
        }

        StringBuffer sequence_3 = new StringBuffer("слово");
        stringBuffer.append(sequence_3);
        System.out.println(stringBuffer.length());
        if (execution == 2) {
            if (stringBuffer.length() == 19 && stringBuffer.charAt(17) == sequence_3.charAt(3))
                return 2;
            else
                return -1;
        }

        StringBuffer sequence_4 = new StringBuffer(new String(new int[]{80_000}, 0, 1));
        stringBuffer.append(sequence_4);
        if (execution == 3) {
            if (stringBuffer.length() == 21 && stringBuffer.charAt(12) == 'g')
                return 3;
            else
                return -1;
        }

        return -1;
    }


    // StringBufferAutomaton::append (boolean)
    public static int test_append_5(final int execution) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(false);
        if (execution == 0) {
            if (stringBuffer.length() == 5 && stringBuffer.toString().equals("false"))
                return 0;
            else
                return -1;
        }

        stringBuffer.append(true);
        if (execution == 1) {
            if (stringBuffer.length() == 9 && stringBuffer.toString().equals("falsetrue"))
                return 1;
            else
                return -1;
        }


        return -1;
    }


    // StringBufferAutomaton::append (char)
    public static int test_append_6(final int execution) {
        StringBuffer stringBuffer = new StringBuffer();
        char ch1 = 'b';
        stringBuffer.append(ch1);
        if (execution == 0) {
            if (stringBuffer.length() == 1 && stringBuffer.charAt(0) == 'b')
                return 0;
            else
                return -1;
        }

        char ch2 = 'д';
        stringBuffer.append(ch2);
        if (execution == 1) {
            if (stringBuffer.length() == 2 && stringBuffer.charAt(1) == 'д')
                return 1;
            else
                return -1;
        }

        char ch3 = '\\';
        stringBuffer.append(ch3);
        if (execution == 2) {
            if (stringBuffer.length() == 3 && stringBuffer.charAt(2) == '\\')
                return 2;
            else
                return -1;
        }

        return -1;
    }


    // StringBufferAutomaton::append (char[])
    public static int test_append_7(final int execution) {
        StringBuffer stringBuffer = new StringBuffer();
        char[] ch1 = {'b', 'a', 'c'};
        stringBuffer.append(ch1);
        if (execution == 0) {
            if (stringBuffer.length() == 3 && stringBuffer.charAt(2) == 'c')
                return 0;
            else
                return -1;
        }

        char[] ch2 = {'!', '?', '.'};
        stringBuffer.append(ch2);
        if (execution == 1) {
            if (stringBuffer.length() == 6 && stringBuffer.charAt(5) == '.')
                return 1;
            else
                return -1;
        }

        char[] ch3 = {'в'};
        stringBuffer.append(ch3);
        if (execution == 2) {
            if (stringBuffer.length() == 7 && stringBuffer.charAt(6) == 'в')
                return 2;
            else
                return -1;
        }

        return -1;
    }


    // StringBufferAutomaton::append (char[], int, int)
    public static int test_append_8(final int execution) {
        StringBuffer stringBuffer = new StringBuffer();
        char[] ch1 = {'b', 'a', 'c'};
        stringBuffer.append(ch1, 1, 2);
        if (execution == 0) {
            if (stringBuffer.length() == 2 && stringBuffer.charAt(1) == 'c')
                return 0;
            else
                return -1;
        }

        char[] ch2 = {'!', 'в', '.'};
        stringBuffer.append(ch2, 0, 3);
        if (execution == 1) {
            if (stringBuffer.length() == 5 && stringBuffer.charAt(4) == '.')
                return 1;
            else
                return -1;
        }

        char[] ch3 = {'а', 'б', 'в', 'г'};
        if (execution == 2) {
            try {
                stringBuffer.append(ch3, 1, -1);
                return -1;
            } catch (IndexOutOfBoundsException e) {
                return 2;
            }

        }

        char[] ch4 = {'1', '2', '3'};
        stringBuffer.append(ch4, 1, 5);
        if (execution == 3) {
            try {
                stringBuffer.append(ch4, 1, -1);
                return -1;
            } catch (IndexOutOfBoundsException e) {
                return 3;
            }
        }

        return -1;
    }


    // StringBufferAutomaton::append (double)
    public static int test_append_9(final int execution) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(1.0);
        if (execution == 0) {
            if (stringBuffer.length() == 3 && stringBuffer.charAt(0) == '1' && stringBuffer.charAt(1) == '.')
                return 0;
            else
                return -1;
        }

        stringBuffer.append(23.34);
        if (execution == 1) {
            if (stringBuffer.length() == 8 && stringBuffer.charAt(5) == '.' && stringBuffer.charAt(7) == '4')
                return 1;
            else
                return -1;
        }

        stringBuffer.append(Double.MAX_VALUE);
        if (execution == 2) {
            if (stringBuffer.length() == 30 && stringBuffer.charAt(26) == 'E' && stringBuffer.charAt(29) == '8')
                return 2;
            else
                return -1;
        }

        stringBuffer.append(12e-1);
        if (execution == 3) {
            if (stringBuffer.length() == 33 && stringBuffer.charAt(31) == '.' && stringBuffer.charAt(32) == '2')
                return 3;
            else
                return -1;
        }

        return -1;
    }


    // StringBufferAutomaton::append (float)
    public static int test_append_10(final int execution) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(1.0F);
        if (execution == 0) {
            if (stringBuffer.length() == 3 && stringBuffer.charAt(0) == '1' && stringBuffer.charAt(1) == '.')
                return 0;
            else
                return -1;
        }

        stringBuffer.append(23.34F);
        if (execution == 1) {
            if (stringBuffer.length() == 8 && stringBuffer.charAt(5) == '.' && stringBuffer.charAt(7) == '4')
                return 1;
            else
                return -1;
        }

        stringBuffer.append(Float.MAX_VALUE);
        if (execution == 2) {
            if (stringBuffer.length() == 20 && stringBuffer.charAt(17) == 'E' && stringBuffer.charAt(19) == '8')
                return 2;
            else
                return -1;
        }

        stringBuffer.append(12e-1F);
        if (execution == 3) {
            if (stringBuffer.length() == 23 && stringBuffer.charAt(21) == '.' && stringBuffer.charAt(22) == '2')
                return 3;
            else
                return -1;
        }

        return -1;
    }


    // StringBufferAutomaton::append (int)
    public static int test_append_11(final int execution) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(1);

        if (execution == 0) {
            if (stringBuffer.length() == 1 && stringBuffer.charAt(0) == '1')
                return 0;
            else
                return -1;
        }

        stringBuffer.append(23);
        if (execution == 1) {
            if (stringBuffer.length() == 3 && stringBuffer.charAt(0) == '1' && stringBuffer.charAt(2) == '3')
                return 1;
            else
                return -1;
        }

        stringBuffer.append(Integer.MAX_VALUE);
        if (execution == 2) {
            if (stringBuffer.length() == 13 && stringBuffer.charAt(10) == '6' && stringBuffer.charAt(12) == '7')
                return 2;
            else
                return -1;
        }

        stringBuffer.append(-10);

        if (execution == 3) {
            if (stringBuffer.length() == 16 && stringBuffer.charAt(13) == '-' && stringBuffer.charAt(15) == '0')
                return 3;
            else
                return -1;
        }

        return -1;
    }


    // StringBufferAutomaton::append (long)
    public static int test_append_12(final int execution) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(1L);

        if (execution == 0) {
            if (stringBuffer.length() == 1 && stringBuffer.charAt(0) == '1')
                return 0;
            else
                return -1;
        }

        stringBuffer.append(23L);
        if (execution == 1) {
            if (stringBuffer.length() == 3 && stringBuffer.charAt(0) == '1' && stringBuffer.charAt(2) == '3')
                return 1;
            else
                return -1;
        }

        stringBuffer.append(Long.MAX_VALUE);
        if (execution == 2) {
            if (stringBuffer.length() == 22 && stringBuffer.charAt(18) == '5' && stringBuffer.charAt(21) == '7')
                return 2;
            else
                return -1;
        }

        stringBuffer.append(-10L);

        if (execution == 3) {
            if (stringBuffer.length() == 25 && stringBuffer.charAt(22) == '-' && stringBuffer.charAt(23) == '1')
                return 3;
            else
                return -1;
        }
        return -1;
    }


    // StringBufferAutomaton::appendCodePoint (int)
    public static int test_appendCodePoint_0(final int execution) {
        StringBuffer stringBuffer = new StringBuffer();

        if (execution == 0) {
            try {
                stringBuffer.appendCodePoint(-1);
                return -1;
            } catch (IllegalArgumentException e) {
                return 0;
            }
        }

        stringBuffer.appendCodePoint(65);
        if (execution == 1) {
            if (stringBuffer.length() == 1 && stringBuffer.charAt(0) == 'A')
                return 1;
            else
                return -1;
        }

        stringBuffer.appendCodePoint(1074);
        if (execution == 2) {
            if (stringBuffer.length() == 2 && stringBuffer.charAt(1) == 'в')
                return 2;
            else
                return -1;
        }

        stringBuffer.appendCodePoint(32);

        if (execution == 3) {
            if (stringBuffer.length() == 3 && stringBuffer.charAt(2) == ' ')
                return 3;
            else
                return -1;
        }
        return -1;
    }


    // StringBufferAutomaton::capacity
    public static int test_capacity_0(final int execution) {
        StringBuffer stringBuffer = new StringBuffer();
        if (execution == 0) {
            if (stringBuffer.length() == 0)
                return 0;
            else
                return -1;
        }

        stringBuffer.append("test1");
        if (execution == 1) {
            if (stringBuffer.length() == 5)
                return 1;
            else
                return -1;
        }
        char[] space100 = new char[100];
        stringBuffer.append(space100);
        if (execution == 2) {
            if (stringBuffer.length() == 105)
                return 2;
            else
                return -1;
        }

        stringBuffer.append('в');
        if (execution == 3) {
            if (stringBuffer.length() == 106)
                return 3;
            else
                return -1;
        }

        return -1;
    }


    // StringBufferAutomaton::charAt (int)
    public static int test_charAt_0(final int execution) {
        StringBuffer stringBuffer = new StringBuffer("text for тест 0!");

        if (execution == 0) {
            try {
                stringBuffer.charAt(100);
                return -1;
            } catch (IndexOutOfBoundsException e) {
                return 0;
            }
        }

        if (execution == 1) {
            try {
                stringBuffer.charAt(-2);
                return -1;
            } catch (IndexOutOfBoundsException e) {
                return 1;
            }
        }


        if (execution == 2) {
            if (stringBuffer.charAt(9) == 'т')
                return 2;
            else
                return -1;
        }

        stringBuffer.append('в');
        if (execution == 3) {
            if (stringBuffer.charAt(0) == 't')
                return 3;
            else
                return -1;
        }

        return -1;
    }


    // StringBufferAutomaton::chars
    public static int test_chars_0(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::codePointAt (int)
    public static int test_codePointAt_0(final int execution) {
        StringBuffer stringBuffer = new StringBuffer();

        if (execution == 0) {
            try {
                stringBuffer.codePointAt(0);
                return -1;
            } catch (IndexOutOfBoundsException e) {
                return 0;
            }
        }

        stringBuffer.append("testtesttesttest");
        if (execution == 1) {
            try {
                stringBuffer.codePointAt(-1);
                return -1;
            } catch (IndexOutOfBoundsException e) {
                return 1;
            }
        }


        if (execution == 2) {
            if (stringBuffer.codePointAt(2) <= 1114111 && stringBuffer.codePointAt(2) >= 0)
                return 2;
            else
                return -1;
        }


        return -1;
    }


    // StringBufferAutomaton::codePointBefore (int)
    public static int test_codePointBefore_0(final int execution) {
        StringBuffer stringBuffer = new StringBuffer();

        if (execution == 0) {
            try {
                stringBuffer.codePointBefore(0);
                return -1;
            } catch (IndexOutOfBoundsException e) {
                return 0;
            }
        }


        if (execution == 1) {
            try {
                stringBuffer.codePointBefore(-1);
                return -1;
            } catch (IndexOutOfBoundsException e) {
                return 1;
            }
        }

        stringBuffer.append("test");
        if (execution == 2) {
            if (stringBuffer.codePointBefore(4) <= 1114111 && stringBuffer.codePointBefore(4) >= 0)
                return 2;
            else
                return -1;
        }

        if (execution == 3) {
            if (stringBuffer.codePointBefore(1) <= 1114111 && stringBuffer.codePointBefore(1) >= 0)
                return 3;
            else
                return -1;
        }

        return -1;
    }


    // StringBufferAutomaton::codePointCount (int, int)
    public static int test_codePointCount_0(final int execution) {
        StringBuffer stringBuffer = new StringBuffer();

        if (execution == 0) {
            try {
                stringBuffer.codePointCount(0, 2);
                return -1;
            } catch (IndexOutOfBoundsException e) {
                return 0;
            }
        }

        stringBuffer.append("test");
        if (execution == 1) {
            try {
                stringBuffer.codePointCount(4, 2);
                return -1;
            } catch (IndexOutOfBoundsException e) {
                return 1;
            }
        }

        if (execution == 2) {
            try {
                stringBuffer.codePointCount(-1, 2);
                return -1;
            } catch (IndexOutOfBoundsException e) {
                return 2;
            }
        }

        if (execution == 3) {
            int startIndex = 2;
            int endIndex = 6;
            if (stringBuffer.codePointCount(startIndex, endIndex) >= (endIndex - startIndex) &&
                    stringBuffer.codePointCount(startIndex, endIndex) <= (endIndex - startIndex) * 2)
                return 3;
            else
                return -1;
        }

        return -1;
    }


    // StringBufferAutomaton::codePoints
    public static int test_codePoints_0(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::compareTo (StringBuffer)
    public static int test_compareTo_0(final int execution) {
        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer("aaaaaaaaaa");
        StringBuffer stringBuffer3 = new StringBuffer("bbbbb");
        StringBuffer stringBuffer4 = stringBuffer1;
        StringBuffer stringBuffer5 = new StringBuffer();

        if (execution == 0) {
            if (stringBuffer1.compareTo(stringBuffer4) == 0)
                return 0;
            else
                return -1;
        }

        if (execution == 1) {
            if (stringBuffer2.compareTo(stringBuffer1) == 10)
                return 1;
            else
                return -1;
        }

        if (execution == 2) {
            if (stringBuffer3.compareTo(stringBuffer2) == -5)
                return 2;
            else
                return -1;
        }

        if (execution == 3) {
            if (stringBuffer1.compareTo(stringBuffer5) == 0)
                return 3;
            else
                return -1;
        }

        return -1;
    }


    // StringBufferAutomaton::delete (int, int)
    public static int test_delete_0(final int execution) {
        StringBuffer stringBuffer = new StringBuffer("0123456789");

        if (execution == 0) {
            try {
                stringBuffer.delete(-1, 2);
                return -1;
            } catch (StringIndexOutOfBoundsException e) {
                return 0;
            }
        }

        if (execution == 1) {
            try {
                stringBuffer.delete(1, 12);
                return -1;
            } catch (StringIndexOutOfBoundsException e) {
                return 1;
            }
        }


        if (execution == 2) {
            try {
                stringBuffer.delete(2, 1);
                return -1;
            } catch (StringIndexOutOfBoundsException e) {
                return 2;
            }
        }

        stringBuffer.delete(3, 7);
        if (execution == 3) {
            if (stringBuffer.length() == 6 && stringBuffer.charAt(3) == '7')
                return 3;
            else
                return -1;
        }

        return -1;
    }


    // StringBufferAutomaton::deleteCharAt (int)
    public static int test_deleteCharAt_0(final int execution) {
        StringBuffer stringBuffer = new StringBuffer("0123456789");

        if (execution == 0) {
            try {
                stringBuffer.deleteCharAt(-1);
                return -1;
            } catch (StringIndexOutOfBoundsException e) {
                return 0;
            }
        }

        if (execution == 1) {
            try {
                stringBuffer.deleteCharAt(10);
                return -1;
            } catch (StringIndexOutOfBoundsException e) {
                return 1;
            }
        }

        stringBuffer.deleteCharAt(6);
        if (execution == 2) {
            if (stringBuffer.length() == 9 && stringBuffer.charAt(6) == 7)
                return 2;
            else
                return -1;

        }

        stringBuffer.deleteCharAt(8);
        if (execution == 3) {
            if (stringBuffer.length() == 8 && stringBuffer.charAt(7) == '8')
                return 3;
            else
                return -1;
        }

        return -1;
    }


    // StringBufferAutomaton::ensureCapacity (int)
    public static int test_ensureCapacity_0(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::getChars (int, int, char[], int)
    public static int test_getChars_0(final int execution) {
        StringBuffer stringBuffer = new StringBuffer("0123456789");
        char[] chars = {'a','b', 'c', 'd', 'e', 'f'};

        if (execution == 0) {
            try {
                stringBuffer.getChars(1, 12, chars, 1);
                return -1;
            } catch (StringIndexOutOfBoundsException e) {
                return 0;
            }
        }

        if (execution == 1) {
            try {
                stringBuffer.getChars(1, 5, chars, 4);
                return -1;
            } catch (IndexOutOfBoundsException e) {
                return 1;
            }
        }

        if (execution == 2) {
            stringBuffer.getChars(2, 4, chars, 1);
            if (chars[1] == stringBuffer.charAt(2) && chars[2] == stringBuffer.charAt(3))
                return 2;
            else
                return -1;

        }

        return -1;
    }


    // StringBufferAutomaton::indexOf (String)
    public static int test_indexOf_0(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::indexOf (String, int)
    public static int test_indexOf_1(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::insert (int, CharSequence)
    public static int test_insert_0(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::insert (int, CharSequence, int, int)
    public static int test_insert_1(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::insert (int, Object)
    public static int test_insert_2(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::insert (int, String)
    public static int test_insert_3(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::insert (int, boolean)
    public static int test_insert_4(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::insert (int, char)
    public static int test_insert_5(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::insert (int, char[])
    public static int test_insert_6(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::insert (int, char[], int, int)
    public static int test_insert_7(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::insert (int, double)
    public static int test_insert_8(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::insert (int, float)
    public static int test_insert_9(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::insert (int, int)
    public static int test_insert_10(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::insert (int, long)
    public static int test_insert_11(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::lastIndexOf (String)
    public static int test_lastIndexOf_0(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::lastIndexOf (String, int)
    public static int test_lastIndexOf_1(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::length
    public static int test_length_0(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::offsetByCodePoints (int, int)
    public static int test_offsetByCodePoints_0(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::replace (int, int, String)
    public static int test_replace_0(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::reverse
    public static int test_reverse_0(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::setCharAt (int, char)
    public static int test_setCharAt_0(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::setLength (int)
    public static int test_setLength_0(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::subSequence (int, int)
    public static int test_subSequence_0(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::substring (int)
    public static int test_substring_0(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::substring (int, int)
    public static int test_substring_1(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::toString
    public static int test_toString_0(final int execution) {
        return -1;
    }


    // StringBufferAutomaton::trimToSize
    public static int test_trimToSize_0(final int execution) {
        return -1;
    }

}

