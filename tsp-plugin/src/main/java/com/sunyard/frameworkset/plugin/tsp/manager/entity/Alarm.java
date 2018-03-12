package com.sunyard.frameworkset.plugin.tsp.manager.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tsp_alarm")
public class Alarm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;
	
	/**
	 * 警告类型:1-;2-;3;
	 */
	@Column(length=1)
	private String type;
	
	@Column(name="create_time")
	private String createTime;
	
	@Lob
	@Column(name="content", columnDefinition="TEXT", nullable=true) 
	private String content;
	
	@Column(name="send_users",length=255)
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
