package com.sunyard.frameworkset.plugin.tsp.manager.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tsp_rerunning_job")
public class ReRunningJob implements Serializable{

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
     
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@Column(name = "job_id", length = 40)
	private String jobId;

	@Column(name = "job_instance_id", length = 40)
	private String jobInstanceId;

	@Column(name = "run_hostname", length = 100)
	private String runHostname;

	@Column(name = "status", length = 20)
	private String status;

	@Column(name = "start_time", length = 20)
	private String startTime;

	@Column(name = "end_time", length = 20)
	private String endTime;

	
	@Column(name = "run_log", length = 100)
	private String runLog;
	
	@Column(name = "last_modify_user",length=20)
	private String lastModifyIUser;

	@Column(name = "last_modify_time",length=20)
	private String lastModifyTime;
		
	@Column(name = "create_time", length = 20)
	private String createTime;
	
	@Column(name = "create_user", length = 20)
	private String createUser;

	@Column(name = "plan_inst_id", length = 40)
	private String planInstId;

	@Column(name = "call_plan_inst_id",length = 40)
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
		return "ReRunningJob [id=" + id + ", jobId=" + jobId
				+ ", jobInstanceId=" + jobInstanceId + ", runHostname="
				+ runHostname + ", status=" + status + ", startTime="
				+ startTime + ", endTime=" + endTime + ", runLog=" + runLog
				+ ", lastModifyIUser=" + lastModifyIUser + ", lastModifyTime="
				+ lastModifyTime + ", createTime=" + createTime
				+ ", createUser=" + createUser + ", planInstId=" + planInstId
				+ ", callPlanInstId=" + callPlanInstId + "]";
	}

}
