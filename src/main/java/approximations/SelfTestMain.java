package approximations;

import sun.misc.Unsafe;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public final class SelfTestMain {
    private static final boolean QUIT_ON_FAIL = false;
    private static final boolean SUPPRESS_ILLEGAL_REFLECTION_ACCESS = false;

    private static final Class<?> TEST_METHOD_RETURN_TYPE = int.class;
    private static final Class<?>[] TEST_METHOD_PARAMETER_TYPES = new Class[]{
            int.class
    };
    public static final String CLASS_FILE_EXT = ".class";

    private static class TestInfrastructureException extends Throwable {
        TestInfrastructureException(final String format, final Object... objects) {
            super(objects.length != 0
                    ? String.format(format, objects)
                    : format);
        }

        @Override
        public synchronized Throwable fillInStackTrace() {
            return this;
        }
    }

    private static final class StatCounter {
        private int executed = 0;
        private int failed = 0;
        private int unique = 0;

        public void add(final StatCounter other) {
            executed += other.executed;
            failed += other.failed;
            unique += other.unique;
        }

        public int getExecuted() {
            return executed;
        }

        public int getFailed() {
            return failed;
        }

        public int getUniqueMethods() {
            return unique;
        }

        public void countUniqueMethod() {
            ++unique;
        }

        public void countExecution() {
            ++executed;
        }

        public void countFailure() {
            ++failed;
        }
    }

    private SelfTestMain() {
        if (SUPPRESS_ILLEGAL_REFLECTION_ACCESS)
            suppressIllegalAccessWarning();
    }

    private static void suppressIllegalAccessWarning() {
        try {
            final Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            final Unsafe u = (Unsafe) theUnsafe.get(null);

            final Class<?> cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            final Field logger = cls.getDeclaredField("logger");
            u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
        } catch (Exception ignore) {
        }
    }

    private void run(final Collection<Class<?>> testClasses) {
        final StatCounter stats = new StatCounter();

        for (Class<?> clazz : testClasses)
            stats.add(runTestClass(clazz));

        System.out.println("[i] Done.");
        renderStatistics(stats, testClasses);
    }

    private void renderStatistics(final StatCounter stats,
                                  final Collection<Class<?>> testClasses) {
        System.out.printf("[i] Final stats: %d failed out of %d executed in %d unique methods in %d classes.%n",
                stats.getFailed(),
                stats.getExecuted(),
                stats.getUniqueMethods(),
                testClasses.size()
        );
    }

    private static boolean isSuitableTestMethod(final Method method) {
        final int mods = method.getModifiers();

        if (method.isSynthetic())
            return false;

        return !Modifier.isAbstract(mods) &&
                Modifier.isStatic(mods) &&
                Modifier.isPublic(mods);
    }

    private StatCounter runTestClass(final Class<?> clazz) {
        System.out.printf("[c] Executing tests from '%s'%n", clazz.getCanonicalName());

        final Method[] methods = clazz.getDeclaredMethods();
        Arrays.sort(methods, Comparator.comparing(Method::getName));

        final StatCounter result = new StatCounter();
        for (Method tm : methods)
            if (isSuitableTestMethod(tm))
                result.add(runTestMethod(tm));

        System.out.printf("[c] Results: %d/%d failing in '%s'%n%n",
                result.getFailed(), result.getExecuted(), clazz.getCanonicalName());
        return result;
    }

    private StatCounter runTestMethod(final Method testMethod) {
        // preparations
        final String testMethodName = testMethod.getName();
        final StatCounter stats = new StatCounter();

        try {
            // sanity checks
            checkTestMethod(testMethod);

            // reading test configuration
            final Test metadata = getTestMetadata(testMethod);
            final boolean shouldIgnore = getTestDisabled(metadata);
            if (shouldIgnore) {
                System.out.printf("[-] (?\\?) #%s - DISABLED", testMethodName);
                return stats;
            }
            final int maxExecution = getTestExecutionLimit(metadata) + 1;
            if (maxExecution <= 0)
                throw new TestInfrastructureException("Invalid max execution value: %s", maxExecution - 1);
            final Collection<Class<?>> allowedExceptions = getTestAllowedExceptions(metadata);

            // running individual cases
            stats.countUniqueMethod();
            for (int exe = 0; exe < maxExecution; exe++) {
                System.out.printf("[~] (%d\\%d) #%s...", exe + 1, maxExecution, testMethodName);

                final Throwable exception = runTestCase(testMethod, exe);
                stats.countExecution();

                if (exception == null || isExceptionAllowed(exception, allowedExceptions)) {
                    System.out.println("ok");
                } else {
                    stats.countFailure();
                    System.out.println("FAILED.");

                    final int newTraceSize = patchException(exception, testMethod);
                    if (newTraceSize > 0)
                        exception.printStackTrace(System.out);
                    else
                        System.out.println(exception.getMessage());

                    if (QUIT_ON_FAIL)
                        System.exit(-1);
                }
            }
        } catch (TestInfrastructureException e) {
            System.out.printf("[~] (?\\?) #%s - INVALID", testMethodName);
            System.out.printf("[x] %s%n", e.getMessage());
            return stats;
        }

        // reply with statistics
        return stats;
    }

    private static void checkTestMethod(Method testMethod) throws TestInfrastructureException {
        // return value
        final Class<?> returnType = testMethod.getReturnType();
        if (returnType != TEST_METHOD_RETURN_TYPE)
            throw new TestInfrastructureException(
                    "Unexpected return type: expecting '%s' but got '%s'%n",
                    int.class.getCanonicalName(),
                    returnType.getCanonicalName()
            );

        // parameters
        final java.lang.reflect.Parameter[] parameters = testMethod.getParameters();
        if (parameters.length != TEST_METHOD_PARAMETER_TYPES.length)
            throw new TestInfrastructureException(
                    "Invalid test parameter count: expecting %d but got %d",
                    TEST_METHOD_PARAMETER_TYPES.length,
                    parameters.length
            );
        for (int i = 0; i < parameters.length; i++) {
            final Class<?> expected = TEST_METHOD_PARAMETER_TYPES[i];
            final Class<?> actual = parameters[i].getType();
            if (actual != expected)
                throw new TestInfrastructureException(
                        "Invalid type for parameter #%d: expecting '%s' but got '%s'",
                        expected.getCanonicalName(),
                        actual.getCanonicalName()
                );
        }
    }

    private static boolean isExceptionAllowed(final Throwable exception,
                                              final Collection<Class<?>> allowedExceptions) {
        final Class<?> caught = exception.getClass();
        for (Class<?> e : allowedExceptions)
            if (e == caught || e.isAssignableFrom(caught))
                return true;
        return false;
    }

    private Throwable runTestCase(final Method testMethod, final int execution) {
        try {
            final int res = (int) testMethod.invoke(null, execution);
            if (res != execution) {
                final String msg = "Expected " + execution + " but got " + res;
                throw new AssertionError(msg);
            }
            return null;
        } catch (InvocationTargetException e) {
            return e.getCause();
        } catch (Throwable e) {
            return e;
        }
    }

    private static Test getTestMetadata(final Method testMethod) {
        return testMethod.getAnnotation(Test.class);
    }

    private static int getTestExecutionLimit(final Test metadata) {
        return metadata != null
                ? metadata.executionMax()
                : 0;
    }

    private static Collection<Class<?>> getTestAllowedExceptions(final Test metadata) {
        return metadata != null
                ? Arrays.asList(metadata.exceptions())
                : Collections.emptyList();
    }

    private static boolean getTestDisabled(final Test metadata) {
        return metadata != null && metadata.disabled();
    }

    private int patchException(final Throwable exception, final Method testMethod) {
        // grab the old trace
        final StackTraceElement[] trace = exception.getStackTrace();

        final String boundClass = testMethod.getDeclaringClass().getCanonicalName();
        final String boundMethod = testMethod.getName();

        final String thisClass = getClass().getCanonicalName();

        // construct a new one by filtering out everything below the target test method
        final ArrayList<StackTraceElement> newTraceList = new ArrayList<>();
        for (StackTraceElement ste : trace) {
            final String className = ste.getClassName();

            // hide this class
            if (thisClass.equals(className))
                continue;

            newTraceList.add(ste);

            if (boundClass.equals(className) && boundMethod.equals(ste.getMethodName()))
                // skip the rest
                break;
        }
        final StackTraceElement[] newTrace = newTraceList.toArray(new StackTraceElement[0]);

        // overwrite the stack trace
        exception.setStackTrace(newTrace);

        // reply with the size of the trace
        return newTrace.length;
    }

    private static Collection<Class<?>> findTestClasses() {
        final Collection<Class<?>> result = new ArrayList<>();

        // collect everything
        final String packageName = SelfTestMain.class.getPackage().getName();
        try {
            findTestClasses(packageName, result);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // filter-out this class
        final String thisName = SelfTestMain.class.getCanonicalName();
        return result.stream()
                .filter(c -> !c.isSynthetic())
                .filter(c -> !c.isLocalClass())
                .filter(c -> !c.isAnnotation())
                .filter(c -> !c.isInterface())
                .filter(c -> !c.getCanonicalName().startsWith(thisName))
                .collect(Collectors.toList());
    }

    private static void findTestClasses(final String packageName,
                                        final Collection<Class<?>> out) throws IOException, ClassNotFoundException {
        final ClassLoader loader = SelfTestMain.class.getClassLoader();

        final Enumeration<URL> resources = loader.getResources(packageName.replace('.', '/'));
        while (resources.hasMoreElements()) {
            final File dir = new File(resources.nextElement().getFile());

            if (dir.exists())
                findTestClasses(dir, packageName, out);
        }
    }

    private static void findTestClasses(final File dir,
                                        final String packageName,
                                        final Collection<Class<?>> out) throws ClassNotFoundException {
        final File[] entries = dir.listFiles();
        if (entries == null)
            return;

        for (File entry : entries) {
            final String entryName = entry.getName();

            if (entry.isFile() && entryName.endsWith(CLASS_FILE_EXT)) {
                final String className = entryName.substring(0, entryName.length() - CLASS_FILE_EXT.length());
                final String canonicalName = packageName + "." + className;
                final Class<?> clazz = Class.forName(canonicalName);
                out.add(clazz);
            } else if (entry.isDirectory()) {
                findTestClasses(entry, packageName + "." + entryName, out);
            }
        }
    }

    public static void main(String[] args) {
        new SelfTestMain().run(findTestClasses());
    }
}
