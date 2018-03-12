package com.sunyard.frameworkset.plugin.tsp.manager.enums;


import com.sunyard.frameworkset.util.enums.EnumAware;

/**
 * 作业服务器配置状态： 手动启用与否
 * User : zhanghui
 * Date : 2016/8/25 14:17
 * File : JobServerConfigStatusEnum.java
 */
public enum JobServerConfigStatusEnum implements EnumAware {
	ENABLED ("1", "启用"),
	DISABLED("0", "不启用");

	JobServerConfigStatusEnum(){

	}

	JobServerConfigStatusEnum(String code, String name){
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
