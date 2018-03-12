package com.sunyard.frameworkset.plugin.tsp.manager.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tsp_waitting_job")
public class WaittingJob implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2687916510940847854L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@Column(name = "job_id", length = 40)
	private String jobId;

	@Column(name = "create_time", length = 20)
	private String createTime;

	@Column(name = "plan_inst_id", length = 40)
	private String planInstId;

	@Column(name="call_plan_inst_id",length = 40)
	private String callPlanInstId;
	
	
	public WaittingJob() {
		super();

	}

	public WaittingJob(String id, String jobId, String createTime,
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
		return "WaittingJob [id=" + id + ", jobId=" + jobId + ", createTime="
				+ createTime + ", planInstId=" + planInstId + "]";
	}

}
