package tests;

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
            //ArrayList_Tests.class,
            ArrayListSpliterator_Tests.class,
    }));
    private static final boolean QUIT_ON_ERROR = false;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(value = {CONSTRUCTOR, METHOD})
    public @interface Test {
        int maxExecution() default 0;
        Class[] exceptions() default {};
    }

    private AutoTest() {
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

        for (var tm : methods)
            if (isSuitableTestMethod(tm))
                runTestMethod(tm);

        System.out.println("[c] Ok");
    }

    private void runTestMethod(final Method testMethod) {
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
                if (res < 0)
                    throw new AssertionError("Unexpected return value: " + res);

                System.out.println("ok");
            }
        } catch (InvocationTargetException e) {
            exception = e.getCause();
        } catch (Throwable e) {
            exception = e;
        }

        if (exception != null) {
            System.out.println("FAILED.");

            patchException(exception);
            exception.printStackTrace(System.out);

            if (QUIT_ON_ERROR)
                System.exit(-1);
        }
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

    private void patchException(final Throwable exception) {
        final var trace = exception.getStackTrace();
        // TODO
    }

    public static void main(String[] args) {
        new AutoTest().run();
    }
}
