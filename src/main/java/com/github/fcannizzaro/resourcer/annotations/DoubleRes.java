package com.github.fcannizzaro.resourcer.annotations;

import java.lang.annotation.*;


/**
 * @author Francesco Cannizzaro
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DoubleRes {
    String value() default "null";
}
