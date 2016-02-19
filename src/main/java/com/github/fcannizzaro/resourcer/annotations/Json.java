package com.github.fcannizzaro.resourcer.annotations;

import java.lang.annotation.*;

/**
 * @author Francesco Cannizzaro
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Json {
    String value() default "null";

    String url() default "null";

    String key() default "null";
}
