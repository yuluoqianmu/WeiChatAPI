/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lyz.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *This annotation is used to validate int type
 * @author jiangtch
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidateLong {

    long min() default Long.MIN_VALUE;

    long max() default Long.MAX_VALUE;

    String message() default "Value of the long is not in expected scope.";
}
