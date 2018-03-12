package com.sunyard.frameworkset.plugin.tsp.manager.enums;


import com.sunyard.frameworkset.util.enums.EnumAware;

/**
 * 作业服务器状态：  判断作业服务器是否在线
 * User : zhanghui
 * Date : 2016/8/25 14:17
 * File : JobServerConfigStatusEnum.java
 */
public enum JobServerStatusEnum implements EnumAware {
	ONLINE ("1", "在线"),
	OFFLINE("0", "断开");

	JobServerStatusEnum(){

	}

	JobServerStatusEnum(String code, String name){
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
