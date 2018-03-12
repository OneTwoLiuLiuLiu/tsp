package com.sunyard.frameworkset.plugin.tsp.manager.vo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


public class JobServerConfigVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4330918830875105936L;
	
	/**
	 * 正常状态
	 */
	public static final String NORMAL="1";
	
	/**
	 * 断开状态
	 */
	public static final String DISCONNECT="0";

	/**
	 * 不启用状态
	 */
	public static final String DISABLE="0";
	
	/**
	 * 启用状态
	 */
	public static final String ABLE="1";
	

	private String id;


	private String hostname;


	private Integer maxRunNum=50;


	private Integer minFreeMemory=10;


	private Integer minFreeCpu=20;

	private Integer maxHistoryDay=5;
	
	/**
	 * 作业服务器是否可用
	 * 0：表示不可用
	 * 1：表示可用
	 */
	private String status;

	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

	/**
	 * 作业服务器是否在线

	 */
	private String isOnline;

	private String runJobType;
	
	@Deprecated
	private String runJobTypeCn;

	private List<String> jobTypes;

	public JobServerConfigVo () {

	}

	public List<String> getJobTypes() {
		return jobTypes;
	}

	public void setJobTypes(List<String> jobTypes) {
		this.jobTypes = jobTypes;
	}

	public JobServerConfigVo (String id) {
		super();
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getRunJobTypeCn() {
		return runJobTypeCn;
	}

	public void setRunJobTypeCn(String runJobTypeCn) {
		this.runJobTypeCn = runJobTypeCn;
	}

	public Integer getMaxHistoryDay() {
		return maxHistoryDay;
	}

	public void setMaxHistoryDay(Integer maxHistoryDay) {
		this.maxHistoryDay = maxHistoryDay;
	}

	public String getId() {
		return id;
	}

	public String getHostname() {
		return hostname;
	}

	public Integer getMaxRunNum() {
		return maxRunNum;
	}

	public Integer getMinFreeMemory() {
		return minFreeMemory;
	}

	public Integer getMinFreeCpu() {
		return minFreeCpu;
	}

	public String getStatus() {
		return status;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public void setMaxRunNum(Integer maxRunNum) {
		this.maxRunNum = maxRunNum;
	}

	public void setMinFreeMemory(Integer minFreeMemory) {
		this.minFreeMemory = minFreeMemory;
	}

	public void setMinFreeCpu(Integer minFreeCpu) {
		this.minFreeCpu = minFreeCpu;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRunJobType() {
		return runJobType;
	}

	public void setRunJobType(String runJobType) {
		this.runJobType = runJobType;
	}
}
