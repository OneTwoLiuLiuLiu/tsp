package com.sunyard.frameworkset.plugin.tsp.manager.qo;

import com.sunyard.frameworkset.util.pages.PagingOrder;

public class WaittingJobQo extends PagingOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2687916510940847854L;

	private String id;

	private String jobId;

	private String createTime;

	private String planInstId;

	private String callPlanInstId;
	
	
	public WaittingJobQo () {
		super();

	}

	public WaittingJobQo (String id, String jobId, String createTime,
						  String planInstId) {
		super();
		this.id = id;
		this.jobId = jobId;
		this.createTime = createTime;
		this.planInstId = planInstId;
	}

	
	
	public String getCallPlanInstId() {
		return callPlanInstId;
	}

	public void setCallPlanInstId(String callPlanInstId) {
		this.callPlanInstId = callPlanInstId;
	}

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

	@Override
	public String toString() {
		return "WaittingJobVo [id=" + id + ", jobId=" + jobId + ", createTime="
				+ createTime + ", planInstId=" + planInstId + "]";
	}

}
