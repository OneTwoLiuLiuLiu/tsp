package com.sunyard.frameworkset.plugin.tsp.manager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Misaki on 2016/12/30.
 */

@Entity
@Table(name = "tsp_job_monitor")
public class JobMonitor {

	@Column(name = "pname", length = 50)
	private String pName;
	@Column(name = "jname", length = 50)
	private String jName;

	@Column(name = "jobid", length = 255)
	private String jobId;

	@Id
	@Column(name = "id", length = 255)
	private String id;

	@Column(name = "plan_inst_id", length = 40)
	private String planInstId;

	@Column(name = "plan_id", length = 255)
	private String planId;

	@Column(name = "start_time", length = 20)
	private String startTime;

	@Column(name = "end_time", length = 20)
	private String endTime;

	@Column(name = "status", length = 20)
	private String status;

	@Column(name = "run_hostname", length = 100)
	private String runHostname;

	@Column(name = "run_log", length = 100)
	private String runLog;

	public String getpName () {
		return pName;
	}

	public void setpName (String pName) {
		this.pName = pName;
	}

	public String getjName () {
		return jName;
	}

	public void setjName (String jName) {
		this.jName = jName;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getId () {
		return id;
	}

	public void setId (String id) {
		this.id = id;
	}

	public String getPlanInstId () {
		return planInstId;
	}

	public void setPlanInstId (String planInstId) {
		this.planInstId = planInstId;
	}

	public String getPlanId () {
		return planId;
	}

	public void setPlanId (String planId) {
		this.planId = planId;
	}

	public String getStartTime () {
		return startTime;
	}

	public void setStartTime (String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime () {
		return endTime;
	}

	public void setEndTime (String endTime) {
		this.endTime = endTime;
	}

	public String getStatus () {
		return status;
	}

	public void setStatus (String status) {
		this.status = status;
	}

	public String getRunHostname () {
		return runHostname;
	}

	public void setRunHostname (String runHostname) {
		this.runHostname = runHostname;
	}

	public String getRunLog () {
		return runLog;
	}

	public void setRunLog (String runLog) {
		this.runLog = runLog;
	}
}
