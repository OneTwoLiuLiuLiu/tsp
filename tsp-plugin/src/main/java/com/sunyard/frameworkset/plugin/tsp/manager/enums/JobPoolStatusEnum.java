package com.sunyard.frameworkset.plugin.tsp.manager.enums;

import com.sunyard.frameworkset.util.enums.EnumAware;

/**
 * Created by Misaki on 2017/1/19.
 */
public enum JobPoolStatusEnum implements EnumAware {
	NO_TAKE ("0", "未分配"),
	TAKE ("1", "已分配");

	JobPoolStatusEnum (String code, String name) {
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
