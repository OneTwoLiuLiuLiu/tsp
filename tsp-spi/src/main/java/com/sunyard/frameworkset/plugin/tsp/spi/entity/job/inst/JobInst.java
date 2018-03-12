package com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst;

import java.io.Serializable;


public class JobInst implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 500557664688465383L;


	private String jobId;


	private String name;


	private String runParams;


	private String ignoreErr;


	private Integer retryCnt=3;


	private Integer retrySec=60;


	private String  planInstId;
	
	private String  runHostName;
	
	private String  runLog;
	
	
	
	
	public String getRunLog() {
		return runLog;
	}


	public void setRunLog(String runLog) {
		this.runLog = runLog;
	}


	public String getRunHostName() {
		return runHostName;
	}


	public void setRunHostName(String runHostName) {
		this.runHostName = runHostName;
	}


	public String getJobId() {
		return jobId;
	}


	public void setJobId(String jobId) {
		this.jobId = jobId;
	}


	public String getPlanInstId() {
		return planInstId;
	}


	public void setPlanInstId(String planInstId) {
		this.planInstId = planInstId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getRunParams() {
		return runParams;
	}


	public void setRunParams(String runParams) {
		this.runParams = runParams;
	}


	public String getIgnoreErr() {
		return ignoreErr;
	}


	public void setIgnoreErr(String ignoreErr) {
		this.ignoreErr = ignoreErr;
	}


	public Integer getRetryCnt() {
		return retryCnt;
	}


	public void setRetryCnt(Integer retryCnt) {
		this.retryCnt = retryCnt;
	}


	public Integer getRetrySec() {
		return retrySec;
	}


	public void setRetrySec(Integer retrySec) {
		this.retrySec = retrySec;
	}

	public String getType(){
		return "";
	}


	@Override
	public String toString() {
		return "JobInst [jobId=" + jobId + ", name=" + name + ", runParams="
				+ runParams + ", ignoreErr=" + ignoreErr + ", retryCnt="
				+ retryCnt + ", retrySec=" + retrySec + ", planInstId="
				+ planInstId + ", runHostName=" + runHostName + ", runLog="
				+ runLog + "]";
	}
	
	
}
