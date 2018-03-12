package com.sunyard.frameworkset.plugin.tsp.manager.vo;

import java.io.Serializable;

public class ReRunningJobVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5120097824358680139L;
      
	/**
	 * 运行中
	 */
	public static final String RUNNING = "0";
	/**
	 * 完成
	 */
	public static final String COMPLE = "1";

	/**
	 * 超时
	 */
	public static final String OUTOFTIME = "2";

	/**
	 * 运行失败
	 */
	public static final String RUNERRER = "3";

	private String id;

	private String jobId;

	private String jobInstanceId;

	private String runHostname;

	private String status;

	private String startTime;

	private String endTime;

	private String runLog;

	private String lastModifyIUser;

	private String lastModifyTime;
		
	private String createTime;
	
	private String createUser;

	private String planInstId;

	private String callPlanInstId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobInstanceId() {
		return jobInstanceId;
	}

	public void setJobInstanceId(String jobInstanceId) {
		this.jobInstanceId = jobInstanceId;
	}

	public String getRunHostname() {
		return runHostname;
	}

	public void setRunHostname(String runHostname) {
		this.runHostname = runHostname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRunLog() {
		return runLog;
	}

	public void setRunLog(String runLog) {
		this.runLog = runLog;
	}

	public String getLastModifyIUser() {
		return lastModifyIUser;
	}

	public void setLastModifyIUser(String lastModifyIUser) {
		this.lastModifyIUser = lastModifyIUser;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getPlanInstId() {
		return planInstId;
	}

	public void setPlanInstId(String planInstId) {
		this.planInstId = planInstId;
	}

	public String getCallPlanInstId() {
		return callPlanInstId;
	}

	public void setCallPlanInstId(String callPlanInstId) {
		this.callPlanInstId = callPlanInstId;
	}

	@Override
	public String toString() {
		return "ReRunningJobVo [id=" + id + ", jobId=" + jobId
				+ ", jobInstanceId=" + jobInstanceId + ", runHostname="
				+ runHostname + ", status=" + status + ", startTime="
				+ startTime + ", endTime=" + endTime + ", runLog=" + runLog
				+ ", lastModifyIUser=" + lastModifyIUser + ", lastModifyTime="
				+ lastModifyTime + ", createTime=" + createTime
				+ ", createUser=" + createUser + ", planInstId=" + planInstId
				+ ", callPlanInstId=" + callPlanInstId + "]";
	}

}
