package approximations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {METHOD})
public @interface Test {
    int executionMax() default 0;

    Class<?>[] exceptions() default {};

    boolean disabled() default false;
}
