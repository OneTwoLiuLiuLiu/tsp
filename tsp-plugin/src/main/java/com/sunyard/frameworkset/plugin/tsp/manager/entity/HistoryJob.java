package com.sunyard.frameworkset.plugin.tsp.manager.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tsp_history_job")
public class HistoryJob implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4694410900355142082L;

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

	@Column(name = "plan_inst_id", length = 40)
	private String planInstId;

	@Column(name = "job_id", length = 40)
	private String jobId;

	@Column(name = "run_hostname", length = 100)
	private String runHostname;

	@Column(name = "status", length = 1)
	private String status;

	@Column(name = "start_time", length = 20)
	private String startTime;

	@Column(name = "end_time", length = 20)
	private String endTime;

	@Column(name = "run_log", length = 100)
	private String runLog;

	@Column(name = "call_plan_inst_id")
	private String callPlanInstId;

	@Column(name = "last_modify_user")
	private String lastModifyIUser;

	@Column(name = "last_modify_time")
	private String lastModifyTime;

	public HistoryJob() {
		super();

	}

	public String getId() {
		return id;
	}

	public String getPlanInstId() {
		return planInstId;
	}

	public String getJobId() {
		return jobId;
	}

	public String getRunHostname() {
		return runHostname;
	}

	public String getStatus() {
		return status;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getRunLog() {
		return runLog;
	}

	public String getCallPlanInstId() {
		return callPlanInstId;
	}

	public String getLastModifyIUser() {
		return lastModifyIUser;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPlanInstId(String planInstId) {
		this.planInstId = planInstId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public void setRunHostname(String runHostname) {
		this.runHostname = runHostname;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setRunLog(String runLog) {
		this.runLog = runLog;
	}

	public void setCallPlanInstId(String callPlanInstId) {
		this.callPlanInstId = callPlanInstId;
	}

	public void setLastModifyIUser(String lastModifyIUser) {
		this.lastModifyIUser = lastModifyIUser;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((callPlanInstId == null) ? 0 : callPlanInstId.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jobId == null) ? 0 : jobId.hashCode());
		result = prime * result
				+ ((lastModifyIUser == null) ? 0 : lastModifyIUser.hashCode());
		result = prime * result
				+ ((lastModifyTime == null) ? 0 : lastModifyTime.hashCode());
		result = prime * result
				+ ((planInstId == null) ? 0 : planInstId.hashCode());
		result = prime * result
				+ ((runHostname == null) ? 0 : runHostname.hashCode());
		result = prime * result + ((runLog == null) ? 0 : runLog.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistoryJob other = (HistoryJob) obj;
		if (callPlanInstId == null) {
			if (other.callPlanInstId != null)
				return false;
		} else if (!callPlanInstId.equals(other.callPlanInstId))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (jobId == null) {
			if (other.jobId != null)
				return false;
		} else if (!jobId.equals(other.jobId))
			return false;
		if (lastModifyIUser == null) {
			if (other.lastModifyIUser != null)
				return false;
		} else if (!lastModifyIUser.equals(other.lastModifyIUser))
			return false;
		if (lastModifyTime == null) {
			if (other.lastModifyTime != null)
				return false;
		} else if (!lastModifyTime.equals(other.lastModifyTime))
			return false;
		if (planInstId == null) {
			if (other.planInstId != null)
				return false;
		} else if (!planInstId.equals(other.planInstId))
			return false;
		if (runHostname == null) {
			if (other.runHostname != null)
				return false;
		} else if (!runHostname.equals(other.runHostname))
			return false;
		if (runLog == null) {
			if (other.runLog != null)
				return false;
		} else if (!runLog.equals(other.runLog))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

}
