package com.sunyard.frameworkset.plugin.tsp.manager.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tsp_plan_instance")
public class PlanInstance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7648248960243422559L;

	/**
	 * 完成
	 */
	public static final String COMPLETE = "2";

	/**
	 * 暂停
	 */
	public static final String PAUSE = "0";

	/**
	 * 运行中
	 */
	public static final String RUNNING = "1";

	/**
	 * 结束
	 */
	public static final String END = "3";
	
	/**
	 * 失败
	 */
	public static final String ERROR = "4";
	/*
      等待
	 */
	public static final String WAITING = "5";


	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@Column(name = "success_job_num")
	private Integer successJobNum;

	@Column(name = "fail_job_num")
	private Integer failJobNum;

	@Column(name = "pass_jon_num")
	private Integer passJobNum;

	@Column(name = "running_job_num")
	private Integer runningJobNum;

	@Column(name = "left_job_num")
	private Integer leftJobNum;

	@Column(name = "status", length = 20)
	private String status;

	@Column(name = "end_user", length = 20)
	private String endUser;

	@Column(name = "end_user_code", length = 20)
	private String endUserCode;

	@Column(name = "start_time", length = 20)
	private String startTime;

	@Column(name = "end_time", length = 20)
	private String endTime;
	
	@Column(name = "batch_no", length = 20)
	private String batchNo;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_id")
	private Plan plan;

	@Column(name = "parent_plan_inst_id")
	private String parentPlanInstId;

	@Column(name = "run_plan_id")
	private String runPlanId;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="planInst")
	private List<PlanInstParam> params;
	
	
	public PlanInstance() {

	}

	public PlanInstance(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public Integer getSuccessJobNum() {
		return successJobNum;
	}

	public Integer getFailJobNum() {
		return failJobNum;
	}

	public Integer getPassJobNum() {
		return passJobNum;
	}

	public Integer getRunningJobNum() {
		return runningJobNum;
	}

	public Integer getLeftJobNum() {
		return leftJobNum;
	}

	public String getStatus() {
		return status;
	}

	public String getEndUser() {
		return endUser;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSuccessJobNum(Integer successJobNum) {
		this.successJobNum = successJobNum;
	}

	public void setFailJobNum(Integer failJobNum) {
		this.failJobNum = failJobNum;
	}

	public void setPassJobNum(Integer passJobNum) {
		this.passJobNum = passJobNum;
	}

	public void setRunningJobNum(Integer runningJobNum) {
		this.runningJobNum = runningJobNum;
	}

	public void setLeftJobNum(Integer leftJobNum) {
		this.leftJobNum = leftJobNum;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public String getParentPlanInstId() {
		return parentPlanInstId;
	}

	public void setParentPlanInstId(String parentPlanInstId) {
		this.parentPlanInstId = parentPlanInstId;
	}

	public String getEndUserCode() {
		return endUserCode;
	}

	public void setEndUserCode(String endUserCode) {
		this.endUserCode = endUserCode;
	}

	public String getRunPlanId() {
		return runPlanId;
	}

	public void setRunPlanId(String runPlanId) {
		this.runPlanId = runPlanId;
	}

	
	
	
	
	public List<PlanInstParam> getParams() {
		return params;
	}

	public void setParams(List<PlanInstParam> params) {
		this.params = params;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((endUser == null) ? 0 : endUser.hashCode());
		result = prime * result
				+ ((failJobNum == null) ? 0 : failJobNum.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((leftJobNum == null) ? 0 : leftJobNum.hashCode());
		result = prime * result
				+ ((passJobNum == null) ? 0 : passJobNum.hashCode());
		result = prime * result
				+ ((runningJobNum == null) ? 0 : runningJobNum.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((successJobNum == null) ? 0 : successJobNum.hashCode());
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
		PlanInstance other = (PlanInstance) obj;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (endUser == null) {
			if (other.endUser != null)
				return false;
		} else if (!endUser.equals(other.endUser))
			return false;
		if (failJobNum == null) {
			if (other.failJobNum != null)
				return false;
		} else if (!failJobNum.equals(other.failJobNum))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (leftJobNum == null) {
			if (other.leftJobNum != null)
				return false;
		} else if (!leftJobNum.equals(other.leftJobNum))
			return false;
		if (passJobNum == null) {
			if (other.passJobNum != null)
				return false;
		} else if (!passJobNum.equals(other.passJobNum))
			return false;
		if (runningJobNum == null) {
			if (other.runningJobNum != null)
				return false;
		} else if (!runningJobNum.equals(other.runningJobNum))
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
		if (successJobNum == null) {
			if (other.successJobNum != null)
				return false;
		} else if (!successJobNum.equals(other.successJobNum))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlanInstance [id=" + id + ", successJobNum=" + successJobNum
				+ ", failJobNum=" + failJobNum + ", passJobNum=" + passJobNum
				+ ", runningJobNum=" + runningJobNum + ", leftJobNum="
				+ leftJobNum + ", status=" + status + ", endUser=" + endUser
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
