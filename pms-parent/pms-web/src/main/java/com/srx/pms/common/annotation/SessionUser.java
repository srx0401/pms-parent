package com.srx.pms.common.annotation;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented  
public @interface SessionUser {
	String description() default "";
}
