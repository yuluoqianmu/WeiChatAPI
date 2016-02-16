package com.laipai.app.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 如果对像的setter是List，此annotation标注的是List里的Object class
 * 
 * @author lts
 * @date 2013-11-28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD })
public @interface ListObjectType {
	Class<?> value();
}
