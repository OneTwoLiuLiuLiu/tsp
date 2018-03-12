package com.sunyard.frameworkset.plugin.tsp.manager.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JobDesc {

	public String desc ();
	
	public boolean isNullAble () default true;
	
}
