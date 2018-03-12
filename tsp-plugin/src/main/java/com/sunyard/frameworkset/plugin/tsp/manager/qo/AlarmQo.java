package com.sunyard.frameworkset.plugin.tsp.manager.qo;

import com.sunyard.frameworkset.util.pages.PagingOrder;

import java.io.Serializable;

public class AlarmQo extends PagingOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	/**
	 * 警告类型:1-;2-;3;
	 */
	private String type;

	private String createTime;

	private String content;

	private String sendUsers;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendUsers() {
		return sendUsers;
	}

	public void setSendUsers(String sendUsers) {
		this.sendUsers = sendUsers;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
