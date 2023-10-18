package approximations.java.util.zip;

import approximations.Test;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;

public final class CRC32_Tests {

    // internal variables

    // constructors

    // CRC32Automaton::CRC32
    public static int test_CRC32_0(final int execution) {
        CRC32 crc32 = new CRC32();
        if (execution == 0) {
            long crc = crc32.getValue();
            if (crc != 0) return -1;
            else return execution;
        }
        return -1;
    }


    // static methods

    // methods

    // CRC32Automaton::getValue
    public static int test_getValue_0(final int execution) {
        CRC32 crc32 = new CRC32();
        if (execution == 0) {
            long crc = crc32.getValue();
            if (crc != 0) return -1;
            else return execution;
        }
        return -1;
    }


    // CRC32Automaton::reset
    public static int test_reset_0(final int execution) {
        CRC32 crc32 = new CRC32();
        if (execution == 0) {
            crc32.update(123456);
            crc32.reset();
            long crc = crc32.getValue();
            if (crc != 0) return -1;
            else return execution;
        }
        return -1;
    }


    // CRC32Automaton::update (ByteBuffer)
    public static int test_update_0(final int execution) {
        return -1;
    }


    // CRC32Automaton::update (byte[])
    @Test(executionMax = 2, disabled = true, reason = "available only since Java 9")
    public static int test_update_1(final int execution) {
//        CRC32 crc32 = new CRC32();
//        switch (execution) {
//            case 0:
//                byte[] byteArray = "somearray".getBytes();
//                crc32.update(byteArray);
//                long crc0 = crc32.getValue();
//                if (crc0 == 0) return -1;
//                crc32.reset();
//                crc0 = crc32.getValue();
//                if (crc0 != 0) return -1;
//                break;
//            case 1:
//                byte[] byteArray1 = null;
//                try {
//                    crc32.update(byteArray1);
//                    return -1;
//                } catch (NullPointerException e) {
//                    break;
//                }
//            default:
//                return -1;
//        }
        return execution;
    }


    // CRC32Automaton::update (byte[], int, int)
    public static int test_update_2(final int execution) {
        return -1;
    }


    // CRC32Automaton::update (int)
    public static int test_update_3(final int execution) {
        return -1;
    }

}