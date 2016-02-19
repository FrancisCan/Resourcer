package com.github.fcannizzaro.resourcer.annotations;

import java.lang.annotation.*;

/**
 * @author Francesco Cannizzaro
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ColorRes {
    String value() default "null";
}
