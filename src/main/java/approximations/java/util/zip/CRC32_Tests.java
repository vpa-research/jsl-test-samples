package approximations.java.util.zip;

import approximations.Test;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;

@Test
@SuppressWarnings({"unused"})
public final class CRC32_Tests {

    // internal variables

    // constructors

    // CRC32Automaton::CRC32
    @Test
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
    @Test
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
    @Test
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
    @Test(executionMax = 3, disabled = true, reason = "jdk.internal.misc.Unsafe not approximated yet")
    public static int test_update_0(final int execution) {
        CRC32 crc32 = new CRC32();
        switch (execution) {
            case 0:
                ByteBuffer bytebuffer0 = ByteBuffer.wrap("aaaa".getBytes());
                crc32.update(bytebuffer0);
                long crc0 = crc32.getValue();
                if (crc0 == 0) return -1;
                break;
            case 1:
                ByteBuffer bytebuffer1 = ByteBuffer.wrap(new byte[]{});
                crc32.update(bytebuffer1);
                long crc1 = crc32.getValue();
                if (crc1 != 0) return -1;
                break;
            case 2:
                ByteBuffer bytebuffer2 = ByteBuffer.allocateDirect(10);
                crc32.update(bytebuffer2);
                long crc2 = crc32.getValue();
                if (crc2 == 0) return -1;
                break;
            case 3:
                ByteBuffer bytebuffer3 = ByteBuffer.allocateDirect(10);
                bytebuffer3.put((byte) 3);
                crc32.update(bytebuffer3);
                long crc3 = crc32.getValue();
                if (crc3 == 0) return -1;
                break;
            default:
                return -1;
        }
        return execution;
    }


    // CRC32Automaton::update (byte[])
    @Test(executionMax = 1, disabled = true, reason = "available only since Java 9")
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
//                try {
//                    crc32.update(null);
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
    @Test(executionMax = 2)
    public static int test_update_2(final int execution) {
        byte[] byteArray = "somearrayfottest".getBytes();
        CRC32 crc32 = new CRC32();
        switch (execution) {
            case 0:
                crc32.update(byteArray, 2, 5);
                long crc0 = crc32.getValue();
                if (crc0 == 0) return -1;
                crc32.reset();
                crc0 = crc32.getValue();
                if (crc0 != 0) return -1;
                break;
            case 1:
                try {
                    crc32.update(null, 0, 1);
                    return -1;
                } catch (NullPointerException e) {
                    break;
                }
            case 2:
                try {
                    crc32.update(byteArray, 100, 1);
                    return -1;
                } catch (ArrayIndexOutOfBoundsException e) {
                    break;
                }
            default:
                return -1;
        }
        return execution;
    }


    // CRC32Automaton::update (int)
    @Test(executionMax = 1)
    public static int test_update_3(final int execution) {
        CRC32 crc32 = new CRC32();
        switch (execution) {
            case 0:
                crc32.update(1);
                long crc = crc32.getValue();
                if (crc == 0) return -1;
                crc32.reset();
                crc = crc32.getValue();
                if (crc != 0) return -1;
                break;
            case 1:
                crc32.update(-1);
                long crc1 = crc32.getValue();
                if (crc1 == 0) return -1;
                crc32.reset();
                crc1 = crc32.getValue();
                if (crc1 != 0) return -1;
                break;
            default:
                return -1;
        }
        return execution;
    }

}