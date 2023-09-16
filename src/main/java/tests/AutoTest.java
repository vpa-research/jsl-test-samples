package tests;

import sun.misc.Unsafe;
import tests.java.util.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import static java.lang.annotation.ElementType.*;

public final class AutoTest {
    private static final Set<Class<?>> TEST_CLASSES = new LinkedHashSet<>(Arrays.<Class<?>>asList(new Class[]{
            ArrayList_Tests.class,
            ArrayListSpliterator_Tests.class,
            OptionalDouble_Tests.class,
            OptionalInt_Tests.class,
    }));
    private static final boolean QUIT_ON_FAIL = false;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(value = {METHOD})
    public @interface Test {
        int maxExecution() default 0;

        Class[] exceptions() default {};
    }

    private AutoTest() {
        suppressIllegalAccessWarning();
    }

    private static void suppressIllegalAccessWarning() {
        try {
            final var theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            final var u = (Unsafe) theUnsafe.get(null);

            final var cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            final var logger = cls.getDeclaredField("logger");
            u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
        } catch (Exception ignore) {
        }
    }

    private void run() {
        for (var clazz : TEST_CLASSES)
            runTestClass(clazz);

        System.out.println("[i] Done.");
    }

    private static boolean isSuitableTestMethod(final Method method) {
        final var mods = method.getModifiers();

        if (method.isSynthetic())
            return false;

        return !Modifier.isAbstract(mods) &&
                Modifier.isStatic(mods) &&
                Modifier.isPublic(mods);
    }

    private void runTestClass(final Class<?> clazz) {
        System.out.printf("[c] Executing tests from '%s'%n", clazz.getCanonicalName());

        final var methods = clazz.getDeclaredMethods();
        Arrays.sort(methods, Comparator.comparing(Method::getName));

        var executed = 0;
        var failed = 0;
        for (var tm : methods)
            if (isSuitableTestMethod(tm)) {
                if (!runTestMethod(tm))
                    ++failed;
                ++executed;
            }

        System.out.printf("[c] Results: %d failing out of %d in '%s'%n",
                failed, executed, clazz.getCanonicalName());
    }

    private boolean runTestMethod(final Method testMethod) {
        final var testMethodName = testMethod.getName();

        final var allowedExceptions = getAllowedExceptions(testMethod);

        Throwable exception = null;
        try {
            final var maxExecution = getExecutionLimit(testMethod) + 1;
            if (maxExecution <= 0)
                throw new AssertionError("Invalid execution value: " + maxExecution);

            for (int exe = 0; exe < maxExecution; exe++) {
                System.out.printf("[~] (%d/%d) %s...", exe + 1, maxExecution, testMethodName);

                final var res = (int) testMethod.invoke(null, exe);
                if (res != exe) {
                    final var msg = "Expected " + exe + " but got " + res;
                    throw new AssertionError(msg);
                }

                System.out.println("ok");
            }
        } catch (InvocationTargetException e) {
            exception = e.getCause();
        } catch (Throwable e) {
            exception = e;
        }

        if (exception != null) {
            System.out.println("FAILED.");

            final var newTraceSize = patchException(exception, testMethod);
            if (newTraceSize > 0)
                exception.printStackTrace(System.out);
            else
                System.out.println(exception.getMessage());

            if (QUIT_ON_FAIL)
                System.exit(-1);
            else
                return false;
        }
        return true;
    }

    private int getExecutionLimit(final Method testMethod) {
        final var test = testMethod.getAnnotation(Test.class);
        return test != null
                ? test.maxExecution()
                : 0;
    }

    private Collection<Class<?>> getAllowedExceptions(final Method testMethod) {
        final var test = testMethod.getAnnotation(Test.class);
        return test != null
                ? Arrays.<Class<?>>asList(test.exceptions())
                : List.of();
    }

    private int patchException(final Throwable exception, final Method testMethod) {
        // grab the old trace
        final var trace = exception.getStackTrace();

        final var boundClass = testMethod.getDeclaringClass().getCanonicalName();
        final var boundMethod = testMethod.getName();

        final var thisClass = getClass().getCanonicalName();

        // construct a new one by filtering out everything below the target test method
        final var newTraceList = new ArrayList<StackTraceElement>();
        for (var ste : trace) {
            final var className = ste.getClassName();

            // hide this class
            if (thisClass.equals(className))
                continue;

            newTraceList.add(ste);

            if (boundClass.equals(className) && boundMethod.equals(ste.getMethodName()))
                // skip the rest
                break;
        }
        final var newTrace = newTraceList.toArray(StackTraceElement[]::new);

        try {
            final var f = Throwable.class.getDeclaredField("stackTrace");
            f.setAccessible(true);
            f.set(exception, newTrace);
        } catch (Exception ignore) {
        }

        return newTrace.length;
    }

    public static void main(String[] args) {
        new AutoTest().run();
    }
}
