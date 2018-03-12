package com.sunyard.frameworkset.plugin.tsp.spi.user.service;

import java.io.Serializable;

public class OrgVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 机构名称
	 */
	private String name;
	
	/**
	 * 机构唯一标识符
	 */
	private String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
