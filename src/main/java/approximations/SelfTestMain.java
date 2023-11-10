package approximations;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.util.*;
import java.util.stream.Collectors;

public final class SelfTestMain {
    private static final boolean SUPPRESS_ILLEGAL_REFLECTION_ACCESS = false;
    private static final String CLASS_FILE_EXT = ".class";
    private static final Class<?> TEST_METHOD_RETURN_TYPE = int.class;
    private static final Class<?>[] TEST_METHOD_PARAMETER_TYPES = new Class[]{
            int.class
    };
    private static final Class<? extends Annotation> TEST_CLASS_ANNOTATION = Test.class;
    private static final Class<? extends Annotation> TEST_METHOD_ANNOTATION = Test.class;

    private static final String PROP_CLASS_NAME_PREFIX = "prefix";
    private static final String PROP_STOP_ON_FIRST_FAIL = "stop-on-fail";
    private static final String PROP_RETURN_NEGATIVE_ON_FAIL = "crash-on-failing";

    private final boolean stopOnFirstFail;
    private final boolean returnNegativeOnFail;
    private final String classNamePrefix;
    private final PrintStream oldStdOut;
    private final PrintStream oldStdErr;
    private final SecurityManager oldSecurityManager;

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
        private int executedClasses = 0;
        private int failed = 0;
        private int unique = 0;

        public void add(final StatCounter other) {
            executed += other.executed;
            executedClasses += other.executedClasses;
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

        public int getExecutedClasses() {
            return executedClasses;
        }

        public void countUniqueMethod() {
            ++unique;
        }

        public void countExecution() {
            ++executed;
        }

        public void countClassExecution() {
            ++executedClasses;
        }

        public void countFailure() {
            ++failed;
        }
    }

    private static final class NullOutputStream extends OutputStream {
        @Override
        public void write(int b) {
            // nothing
        }
    }

    private static final class ExitGuardSecurityManager extends SecurityManager {
        @Override
        public void checkExit(int status) {
            throw new SecurityException();
        }

        @Override
        public void checkPermission(Permission perm) {
            // everything else is allowed
        }
    }

    private SelfTestMain(final Properties props) {
        this.oldStdOut = System.out;
        this.oldStdErr = System.err;
        this.oldSecurityManager = System.getSecurityManager();

        classNamePrefix = "approximations." + props.getProperty(PROP_CLASS_NAME_PREFIX, "");
        stopOnFirstFail = props.getProperty(PROP_STOP_ON_FIRST_FAIL, "false").equalsIgnoreCase("true");
        returnNegativeOnFail = props.getProperty(PROP_RETURN_NEGATIVE_ON_FAIL, "true").equalsIgnoreCase("true");

        if (SUPPRESS_ILLEGAL_REFLECTION_ACCESS)
            suppressIllegalAccessWarning();
    }

    private static void suppressIllegalAccessWarning() {
        /*
        try {
            final Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            final Unsafe u = (Unsafe) theUnsafe.get(null);

            final Class<?> cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            final Field logger = cls.getDeclaredField("logger");
            u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
        } catch (Exception ignore) {
        }
        */
    }

    private void run() {
        final StatCounter stats = new StatCounter();

        // look for classes
        final Collection<Class<?>> testClasses = findTestClasses(classNamePrefix);
        System.out.printf("[i] Found %d test classes matching prefix '%s':%n",
                testClasses.size(),
                classNamePrefix
        );
        testClasses.forEach(c -> System.out.println("- " + c.getCanonicalName()));
        System.out.println();

        // execute the tests one-by-one
        for (Class<?> clazz : testClasses) {
            stats.add(runTestClass(clazz));

            if (stopOnFirstFail && stats.failed != 0)
                break;
        }

        System.out.println("[i] Done.");
        renderStatistics(stats);

        if (returnNegativeOnFail && stats.failed != 0) {
            System.out.println("[x] There are some failing tests have been detected");
            System.exit(-1);
        }
    }

    private void renderStatistics(final StatCounter stats) {
        System.out.printf("[i] Final stats: %d failed out of %d executed in %d unique methods in %d classes.%n",
                stats.getFailed(),
                stats.getExecuted(),
                stats.getUniqueMethods(),
                stats.getExecutedClasses()
        );
    }

    private static boolean isSuitableTestMethod(final Method method) {
        if (!method.isAnnotationPresent(TEST_METHOD_ANNOTATION))
            return false;

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
        result.countClassExecution();
        for (Method tm : methods)
            if (isSuitableTestMethod(tm)) {
                result.add(runTestMethod(tm));

                if (stopOnFirstFail && result.failed != 0)
                    return result;
            }

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
                final String reason = getTestDisabledReason(metadata);
                System.out.printf("[-] (?\\?) #%s - DISABLED%s%n",
                        testMethodName,
                        reason != null
                                ? ": " + reason
                                : ""
                );
                return stats;
            }
            final int maxExecution = getTestExecutionLimit(metadata) + 1;
            if (maxExecution <= 0)
                throw new TestInfrastructureException("Invalid max execution value: %s", maxExecution - 1);

            // running individual cases
            stats.countUniqueMethod();
            for (int exe = 0; exe < maxExecution; exe++) {
                System.out.printf("[~] (%d\\%d) #%s...", exe + 1, maxExecution, testMethodName);

                final Throwable exception = runTestCase(testMethod, exe);
                stats.countExecution();

                if (exception == null) {
                    System.out.println("ok");
                } else {
                    stats.countFailure();
                    System.out.println("FAILED.");

                    final int newTraceSize = patchException(exception, testMethod);
                    if (newTraceSize > 0)
                        exception.printStackTrace(System.out);
                    else
                        System.out.println("[?] " + exception.getMessage());

                    if (stopOnFirstFail) {
                        System.out.println("[x] A failing test have been detected - aborting");
                        break;
                    }
                }
            }
        } catch (TestInfrastructureException e) {
            System.out.printf("[~] (?\\?) #%s - INVALID%n", testMethodName);
            System.out.printf("[?] %s%n", e.getMessage());
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
                    "Unexpected return type: expecting '%s' but got '%s'",
                    TEST_METHOD_RETURN_TYPE.getCanonicalName(),
                    returnType.getCanonicalName()
            );

        // parameters
        final Parameter[] parameters = testMethod.getParameters();
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
                        i,
                        expected.getCanonicalName(),
                        actual.getCanonicalName()
                );
        }
    }

    private Throwable runTestCase(final Method testMethod, final int execution) {
        beforeTestCase();
        try {
            final int res = (int) testMethod.invoke(null, execution);
            if (res != execution) {
                final String msg = "Expected " + execution + " but got " + res;
                return new AssertionError(msg);
            }
            return null;
        } catch (InvocationTargetException e) {
            return e.getCause();
        } catch (Throwable e) {
            return e;
        } finally {
            afterTestCase();
        }
    }

    private void beforeTestCase() {
        AccessController.doPrivileged((PrivilegedAction<?>) () -> {
            System.setSecurityManager(new ExitGuardSecurityManager());
            System.setOut(new PrintStream(new NullOutputStream()));
            System.setErr(new PrintStream(new NullOutputStream()));

            return null;
        });
    }

    private void afterTestCase() {
        AccessController.doPrivileged((PrivilegedAction<?>) () -> {
            System.setOut(oldStdOut);
            System.setErr(oldStdErr);
            System.setSecurityManager(oldSecurityManager);

            return null;
        });
    }

    private static Test getTestMetadata(final Method testMethod) {
        return testMethod.getAnnotation(Test.class);
    }

    private static int getTestExecutionLimit(final Test metadata) {
        return metadata != null
                ? metadata.executionMax()
                : 0;
    }

    private static boolean getTestDisabled(final Test metadata) {
        return metadata != null && metadata.disabled();
    }

    private static String getTestDisabledReason(final Test metadata) {
        if (metadata == null)
            return null;

        final String reason = metadata.reason();
        return reason.isEmpty()
                ? null
                : reason;
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

    private static Collection<Class<?>> findTestClasses(final String classNamePrefix) {
        final Collection<Class<?>> result = new ArrayList<>();

        // collect everything
        final String packageName = SelfTestMain.class.getPackage().getName();
        try {
            findTestClasses(classNamePrefix, packageName, result);
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

    private static void findTestClasses(final String classNamePrefix,
                                        final String packageName,
                                        final Collection<Class<?>> out) throws IOException, ClassNotFoundException {
        final ClassLoader loader = SelfTestMain.class.getClassLoader();

        final Enumeration<URL> resources = loader.getResources(packageName.replace('.', '/'));
        while (resources.hasMoreElements()) {
            final File dir = new File(resources.nextElement().getFile());

            if (dir.exists())
                findTestClasses(dir, classNamePrefix, packageName, out);
        }
    }

    private static void findTestClasses(final File dir,
                                        final String classNamePrefix,
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

                if (canonicalName.startsWith(classNamePrefix)) {
                    final Class<?> clazz = Class.forName(canonicalName);
                    if (clazz.isAnnotationPresent(TEST_CLASS_ANNOTATION))
                        out.add(clazz);
                }
            } else if (entry.isDirectory()) {
                findTestClasses(entry, classNamePrefix, packageName + "." + entryName, out);
            }
        }
    }

    private static Properties parseArguments(final String[] args) {
        final Properties config = new Properties();
        for (String arg : args) {
            final String[] argPair = arg.split("=", 2);
            config.setProperty(argPair[0], argPair[1]);
        }
        return config;
    }

    public static void main(String[] args) {
        new SelfTestMain(parseArguments(args)).run();
    }
}
