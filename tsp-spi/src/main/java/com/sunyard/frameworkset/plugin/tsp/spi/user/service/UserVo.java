package com.sunyard.frameworkset.plugin.tsp.spi.user.service;

import java.io.Serializable;

public class UserVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户名称
	 */
	private String name;
	
	/**
	 * 用户mail地址
	 */
	private String mail;
	
	/**
	 * 用户唯一标识符
	 */
	private String code;
	
	/**
	 * 用户手机号码
	 */
	private String phone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
