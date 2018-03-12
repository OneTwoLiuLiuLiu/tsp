package com.sunyard.frameworkset.plugin.tsp.manager.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tsp_job_pool")
public class JobPool implements Serializable {

	/**
	 * 该任务已被分配执行
	 */
	public static final String TAKE="1";
	
	/**
	 * 该任务还没有被分配执行
	 */
	public static final String NOTAKE="0";

	private static final long serialVersionUID = 3794773091376825117L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@Column(name = "job_instance_id", length = 40)
	private String jobInstanceId;

	@Column(name = "job_id", length = 40)
	private String jobId;

	@Column(name = "create_time", length = 20)
	private String createTime;

	@Column(name = "plan_inst_id", length = 40)
	private String planInstId;
	
	@Column(name="status",length=1)
	private String status=NOTAKE;

	public JobPool() {
		super();
	}

	public JobPool(String id, String jobInstanceId, String jobId,
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
		return "JobPool [id=" + id + ", jobInstanceId=" + jobInstanceId
				+ ", jobId=" + jobId + ", createTime=" + createTime
				+ ", planInstId=" + planInstId + "]";
	}

}
