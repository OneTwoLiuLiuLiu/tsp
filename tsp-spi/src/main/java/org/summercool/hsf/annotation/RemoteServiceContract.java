package org.summercool.hsf.annotation;

import java.lang.annotation.*;

/**
 * @Title: HsfServiceContract.java
 * @Package org.summercool.hsf.annotation
 * @Description: HSF远程服务接口注解
 * @author 简道
 * @date 2011-11-14 下午5:54:18
 * @version V1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RemoteServiceContract {
	String value() default "";
}