package com.sunyard.frameworkset.plugin.tsp.manager.vo;

import java.io.Serializable;

public class JobPoolVo implements Serializable {

	/**
	 * 该任务已被分配执行
	 */
	public static final String TAKE="1";
	
	/**
	 * 该任务还没有被分配执行
	 */
	public static final String NOTAKE="0";

	private static final long serialVersionUID = 3794773091376825117L;

	private String id;

	private String jobInstanceId;

	private String jobId;

	private String createTime;

	private String planInstId;
	
	private String status=NOTAKE;

	public JobPoolVo () {
		super();
		// TODO Auto-generated constructor stub
	}

	public JobPoolVo (String id, String jobInstanceId, String jobId,
					  String createTime, String planInstId) {
		super();
		this.id = id;
		this.jobInstanceId = jobInstanceId;
		this.jobId = jobId;
		this.createTime = createTime;
		this.planInstId = planInstId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJobInstanceId() {
		return jobInstanceId;
	}

	public void setJobInstanceId(String jobInstanceId) {
		this.jobInstanceId = jobInstanceId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPlanInstId() {
		return planInstId;
	}

	public void setPlanInstId(String planInstId) {
		this.planInstId = planInstId;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "JobPoolVo [id=" + id + ", jobInstanceId=" + jobInstanceId
				+ ", jobId=" + jobId + ", createTime=" + createTime
				+ ", planInstId=" + planInstId + "]";
	}

}
