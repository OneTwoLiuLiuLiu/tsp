package com.sunyard.frameworkset.plugin.tsp.manager.enums;

import com.sunyard.frameworkset.util.enums.EnumAware;

/**
 * Created by Misaki on 2017/1/3.
 */
public enum PlanConfigStatusEnum implements EnumAware {
	STOP("0","停止"),
	START("1","运行"),
	;

	PlanConfigStatusEnum (String code, String name){
		this.code = code;
		this.name = name;
	}

	private String code;
	private String name;
	@Override
	public String getCode () {
		return this.code;
	}

	@Override
	public String getName () {
		return this.name;
	}

	@Override
	public String getSimpleName () {
		return this.name;
	}
}
