package com.sunyard.frameworkset.plugin.tsp.manager.exception;

import com.sunyard.frameworkset.core.exception.FapException;
import com.sunyard.frameworkset.util.enums.EnumAware;

/**
 * 任务调度异常类
 * User : zhanghui
 * Date : 2016/11/7 14:42
 * File : TspException.java
 */
public class TspException extends FapException {
	public TspException(EnumAware enumAware, Throwable e) {
		super ( enumAware, e );
	}

	public TspException(EnumAware enumAware) {
		super ( enumAware );
	}

	public TspException(String errorCode, String errorMsg) {
		super ( errorCode, errorMsg );
	}

	public TspException(String errorCode, String errorMsg, Throwable e) {
		super ( errorCode, errorMsg, e );
	}
}
